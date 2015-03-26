package edu.uhcl.team_drone.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.IntIntMap;
import edu.uhcl.team_drone.drone.Drone;
import edu.uhcl.team_drone.screens.PlayScreen;

// Class for software drone simulator input

public class InputComponent extends InputAdapter {      
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
    private int PAUSE = Input.Keys.ESCAPE;
    
    // Number of keys current pressed
    private int keysPressed = 0;
    
    // roll and pitch amounts for PID to read from
    public float rollAmt = 0, pitchAmt = 0;
   
    // drone that this input object controls
    private Drone owner;


    public InputComponent(Drone ownerIn) {
        this.owner = ownerIn;       
    }
    
    public void update(float deltaTime) {
        
        // up/down
        if (keys.containsKey(UP)) {            
            owner.altitude(2.0f);
        } 
        else if (keys.containsKey(DOWN)) {
            owner.altitude(-2.0f);
        }

        // left/right
        if (keys.containsKey(LEFT)) {
            owner.setControlRollAmt(owner.getControlRollAmt() - 0.13f);
        } 
        else if (keys.containsKey(RIGHT)) {
            owner.setControlRollAmt(owner.getControlRollAmt() + 0.13f);
        } 
        else {
            //owner.setControlRollAmt(0);
        }

        // forward/backward
        if (keys.containsKey(FORWARD)) {            
            if(pitchAmt > - MAX_PITCH){
                owner.setControlPitchAmt(owner.getControlPitchAmt() - 0.03f);
            }
        } 
        else if (keys.containsKey(BACKWARD)) {           
            if(pitchAmt < MAX_PITCH){
                owner.setControlPitchAmt(owner.getControlPitchAmt() + 0.03f);              
            }            
        }
        else{
            //owner.setControlPitchAmt(0);
        }

        // rotations
        if (keys.containsKey(ROTATELEFT)) {
            owner.yaw(1.4f);
        } 
        else if (keys.containsKey(ROTATERIGHT)) {
            owner.yaw(-1.4f);
        }
        
        if (keys.containsKey(DEBUGON)) {
            PlayScreen.debug.isVisible = true;
            PlayScreen.collisionWorld.debugOn = true;
        }
        if (keys.containsKey(DEBUGOFF)) {
            PlayScreen.debug.isVisible = false;
            PlayScreen.collisionWorld.debugOn = false;
        }
        if (keys.containsKey(PAUSE)) {
            PlayScreen.isPaused = !PlayScreen.isPaused;
            keys.remove(PAUSE, 0);
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

    