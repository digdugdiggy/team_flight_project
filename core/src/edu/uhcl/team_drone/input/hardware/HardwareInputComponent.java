package edu.uhcl.team_drone.input.hardware;

import edu.uhcl.team_drone.input.hardware.DroneDriver;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.IntIntMap;
import edu.uhcl.team_drone.drone.Drone;
import edu.uhcl.team_drone.screens.PlayScreen;

// Class for Hardware drone input

public class HardwareInputComponent extends InputAdapter {

    //Object which starts communication between this software and drone
    DroneDriver droneHardware;

    //True = real drone flight mode, drone commands will be transmitted
    //False = drone commands will be ignored by droneCommand()
    private boolean realFlightEnabled = true;

    float MAX_PITCH = 0.6f;

    //Keypress Associations
    private final IntIntMap keys = new IntIntMap();
    private int LEFT = Input.Keys.A;
    private int RIGHT = Input.Keys.D;
    private int FORWARD = Input.Keys.W;
    private int BACKWARD = Input.Keys.S;
    private int UP = Input.Keys.UP;
    private int DOWN = Input.Keys.DOWN;
    private int ROTATELEFT = Input.Keys.LEFT;
    private int ROTATERIGHT = Input.Keys.RIGHT;
    private int TAKEOFF = Input.Keys.P;
    private int LAND = Input.Keys.L;
    private int DRONESTOP = Input.Keys.SPACE;
    private int ENDCONNECTION = Input.Keys.F12;
    private int TOGGLEMODE = Input.Keys.F11;
    private int DEBUGON = Input.Keys.F1;
    private int DEBUGOFF = Input.Keys.F2;

    // Number of keys current pressed
    private int keysPressed = 0;

    // roll and pitch amounts for PID to read from
    public float rollAmt = 0, pitchAmt = 0;

    public HardwareInputComponent() {

        startServer(); // starts the JS server to connect to the physical drone
    }

    public void update(float deltaTime) {

        // up/down
        if (keys.containsKey(UP)) {
            droneCommand("up");
        } else if (keys.containsKey(DOWN)) {
            droneCommand("down");
            System.out.println("eh");
        }

        // left/right
        if (keys.containsKey(LEFT)) {
            droneCommand("left");
        } else if (keys.containsKey(RIGHT)) {
            droneCommand("right");
        } else {
        }

        // forward/backward
        if (keys.containsKey(FORWARD)) {
            if (pitchAmt > -MAX_PITCH) {
                droneCommand("forward");
            }
        } else if (keys.containsKey(BACKWARD)) {
            if (pitchAmt < MAX_PITCH) {
                droneCommand("backward");
            }
        } else {

        }

        // rotations
        if (keys.containsKey(ROTATELEFT)) {
            droneCommand("rotateLeft");
        } else if (keys.containsKey(ROTATERIGHT)) {
            droneCommand("rotateRight");
        }

        if (keys.containsKey(DEBUGON)) {
            PlayScreen.debug.isVisible = true;
        }
        if (keys.containsKey(DEBUGOFF)) {
            PlayScreen.debug.isVisible = false;
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
            realFlightEnabled = false;
        }
        if (keys.containsKey(TOGGLEMODE)) {
            if (realFlightEnabled == true) {
                droneCommand("closeConnection");
                realFlightEnabled = false;
            } else if (realFlightEnabled == false) {
                realFlightEnabled = true;
                startServer();
            }
        }
    }

    public void droneCommand(String command) {
        if (realFlightEnabled == true) {
            try {
                droneHardware.sendCommand(command);
            } catch (Exception e) {
                //Error handling in DroneDriver
            }
        }
    }

    public void startServer() {
        //Starts communication between software and drone
        if (realFlightEnabled == true) {
            try {
                this.droneHardware = new DroneDriver();
            } catch (Exception e) {
                System.out.println("Error with drone driver:");
                System.out.println("     " + e);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) { // called by libgdx on every key press down
        keysPressed++;
        keys.put(keycode, keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {// called by libgdx on every key release
        keysPressed--;
        keys.remove(keycode, 0);
        return true;
    }

    public boolean isKeyPressed() {
        return keysPressed == 0;
    }
}
