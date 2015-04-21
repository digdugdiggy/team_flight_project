/* *****************************************************************************
* Programmer: Michael Truncale
* Course: CSCI 4388.01 - Senior Project
* Date: April 20, 2015
* Assignment: Senior Project
* Environment: Windows 7 - 64 bit
* IDE: Compiled and tested under NetBeans 8.0.2 / JDK 1.8
/******************************************************************************/

package edu.uhcl.team_drone.input.hardware;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.IntIntMap;
import edu.uhcl.team_drone.screens.playscreen.PlayScreen;



//Used to handle drone hardware inputs on Java side and also to request drone
//hardware data for later display in UI
public class HardwareInputComponent extends InputAdapter {
    DroneDriver droneHardware;

    //True = real drone flight mode, drone commands will be transmitted
    //False = drone commands will be ignored by droneCommand()
    private boolean realFlightEnabled = true;
    
    //True = drone only flies w/ key held down
    //False = drone continues to fly in given direction until stopped
    private boolean precisionMode = false;

    //Sentinal value for rebooting server
    private boolean serverRunning = false;
    
    float MAX_PITCH = 0.6f;

    private final IntIntMap keys = new IntIntMap();
    private int LEFT = Input.Keys.A;
    private int RIGHT = Input.Keys.D;
    private int FORWARD = Input.Keys.W;
    private int BACKWARD = Input.Keys.S;
    private int UP = Input.Keys.R;
    private int DOWN = Input.Keys.F;
    private int ROTATELEFT = Input.Keys.Q;
    private int ROTATERIGHT = Input.Keys.E;
    private int TAKEOFF = Input.Keys.P;
    private int LAND = Input.Keys.L;
    private int DRONESTOP = Input.Keys.SPACE;
    private int ENDCONNECTION = Input.Keys.F12;
    private int RESTARTCONNECTION = Input.Keys.F11;
    private int DEBUGON = Input.Keys.F1;
    private int DEBUGOFF = Input.Keys.F2;
    private int TOGGLEMODE = Input.Keys.F8;

    //51 forward, 47 backward, 29 left, 32 right
    //46 up, 34 down, 33 right rotate, 45 left rotate 
    //Must add new keycodes if you want precisionMode enabled for it
    private int[] precisionKeyCodes = new int[] {29,32,33,34,45,46,47,51};
    private int keysPressed = 0;
    private int maxKeysPressed = 3;
 
    public float rollAmt = 0;
    public float pitchAmt = 0;
    private String batteryCharge = "--";
    private String altitude = "--";
    private String currentCommand = "none";
    
    

    public HardwareInputComponent() {

        startServer(); // starts the Node.js server to connect to the drone
    }

    
    //Update cycle runs continually - used to poll drone hardware data
    public void update(float deltaTime) {
        try{
            batteryCharge = droneHardware.readBatteryData();
            altitude = droneHardware.readAltitudeData();
        }
        catch (Exception e){
            //Error handling in readData()   
        }
    }
    
    
    //Returns string value of the current battery charge [0 - 100]
    public String getBatteryCharge(){
        return batteryCharge;
    }
    
    
    //Returns shortened string of drone's altitude
    public String getAltitude(){
        String parsedAltitude;
        
        parsedAltitude = altitude;
        
        try {
            parsedAltitude = parsedAltitude.substring(0, 4);
        }
        catch (Exception e){
        }
        
        return parsedAltitude;
    }
    
    
    //Returns last command issued to drone for logging
    public String getCurrentCommand(){
        return currentCommand;
    }
    
    
    //Returns if drone is in precision flight mode or not
    public boolean getMode(){
        return precisionMode;
    }
    

    //Sends string command to DroneDriver for transmit to drone
    public void droneCommand(String command) {
        if (realFlightEnabled == true) {
            try {
                droneHardware.sendCommand(command);
                currentCommand = command;
            } catch (Exception e) {
                //Error handling in DroneDriver()
            }
        }
    }

    
    //Starts communication between software and drone 
    public void startServer() {
        if (realFlightEnabled == true) {
            try {
                this.droneHardware = new DroneDriver();
                serverRunning = true;
            } 
            catch (Exception e) {
                System.out.println("Error starting DroneDriver():");
                System.out.println("     " + e);
            }
        }
    }

    
    //User inter-actionable keycommands
    @Override
    public boolean keyDown(int keycode) {
        keysPressed++;
        
        if (keysPressed < maxKeysPressed){
            keys.put(keycode, keycode);

            if (keys.containsKey(UP)) {
                droneCommand("up");
            }

            if (keys.containsKey(DOWN)) {
                droneCommand("down");
            }
            
            if (keys.containsKey(LEFT)) {
                droneCommand("left");
            } 
            
            if (keys.containsKey(RIGHT)) {
                droneCommand("right");
            } 
            
            if (keys.containsKey(FORWARD)) {
                if (pitchAmt > -MAX_PITCH) {
                    droneCommand("forward");
                }
            } 
            
            if (keys.containsKey(BACKWARD)) {
                if (pitchAmt < MAX_PITCH) {
                    droneCommand("backward");
                }
            }
            
            if (keys.containsKey(ROTATELEFT)) {
                droneCommand("rotateLeft");
            }  
            
            if (keys.containsKey(ROTATERIGHT)) {
                droneCommand("rotateRight");
            }

            if (keys.containsKey(DEBUGON)) {
                PlayScreen.debug.isVisible = true;
            }
            
            if (keys.containsKey(DEBUGOFF)) {
                PlayScreen.debug.isVisible = false;
            }
            
            if (keys.containsKey(TOGGLEMODE)) {
                if (precisionMode == true){
                    precisionMode = false;
                    //System.out.println("Precision mode turned off...");
                }
                else{
                    precisionMode = true;    
                    //System.out.println("Precision mode turned on...");
                }
            }
            
            if (keys.containsKey(TAKEOFF)) {
                droneCommand("droneTakeOff");
            }
            
            if (keys.containsKey(LAND)) {
                droneCommand("droneLand");
            }
            
            if (keys.containsKey(DRONESTOP)) {
                droneCommand("droneStop");
            }
            
            if (keys.containsKey(ENDCONNECTION)) {
                droneCommand("closeConnection");
                serverRunning = false;
                
                droneHardware.closeVideo();
            }
            
            if (keys.containsKey(RESTARTCONNECTION)) {
                if (serverRunning == false) {
                    startServer();
                }
            }
        }
        return true;
    }

    
    //Detects when a key is released, mainly used for precision mode to know
    //when to tell the drone to stop
    @Override
    public boolean keyUp(int keycode) {
        keysPressed--;
        keys.remove(keycode, 0);

        precisionMoveCheck(keycode);
        
        return true;
    }
    
    
    //Precision mode only moves drone when key is pressed down, this function
    //is called when key is released (keyUp()) to stop the drone - works by
    //searching through precisionKeyCodes[] to see if the key released is a 
    //precision move (forward, backward, etc...) and if precisionMode == true
    //then drone is told to stop, otherwise it does not
    public void precisionMoveCheck(int keycode){
        boolean isPrecisionMove = false;
        
        if (precisionMode == true){
            for (int i = 0; i < precisionKeyCodes.length; i++){
                if (keycode == precisionKeyCodes[i]){
                    isPrecisionMove = true;
                    break;
                }
            }
            
            if (isPrecisionMove == true){
                droneCommand("droneStop");
            }
        }
    }

    
    public boolean isKeyPressed() {
        return keysPressed == 0;
    }
}
