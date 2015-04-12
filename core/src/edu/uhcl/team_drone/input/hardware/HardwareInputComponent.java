package edu.uhcl.team_drone.input.hardware;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.IntIntMap;
import edu.uhcl.team_drone.screens.playscreen.PlayScreen;



// Class for Hardware drone input
public class HardwareInputComponent extends InputAdapter {

    //Object which starts communication between this software and drone
    DroneDriver droneHardware;

    //True = real drone flight mode, drone commands will be transmitted
    //False = drone commands will be ignored by droneCommand()
    private boolean realFlightEnabled = true;
    private boolean serverRunning = false;
    
    //True = drone only flies w/ key held down
    //False = drone continues to fly in given direction until stopped
    private boolean precisionMode = false;
    
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
    private int batteryCharge = 0;
    
    //Controls speed of update cycle
    private int controlTime = 0;
    
    
    
    public HardwareInputComponent() {

        startServer(); // starts the node.js server to connect to the physical drone
    }

    
    public void update(float deltaTime) {
        controlTime++;
        
        if (controlTime >= 250){
            try {
                droneHardware.readDroneData();
            }
            catch (Exception e){
                //Error handling in readData()    
            }
            
            controlTime = 0;
        }
    }

    
    public void droneCommand(String command) {
        if (realFlightEnabled == true) {
            try {
                droneHardware.sendCommand(command);
            } catch (Exception e) {
                //Error handling in DroneDriver()
            }
        }
    }

    
    public void startServer() {
        //Starts communication between software and drone
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
                    System.out.println("Precision mode turned off...");
                }
                else{
                    precisionMode = true;    
                    System.out.println("Precision mode turned on...");
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
            }
            
            if (keys.containsKey(RESTARTCONNECTION)) {
                if (serverRunning == false) {
                    startServer();
                }
            }
        }
        return true;
    }

    
    @Override
    public boolean keyUp(int keycode) {
        keysPressed--;
        keys.remove(keycode, 0);
        
        precisionMoveCheck(keycode);
        
        return true;
    }
    
    
    //Precision mode only moves drone when key is pressed down, this function
    //is called when key is released (keyUp()) to stop the drone
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
