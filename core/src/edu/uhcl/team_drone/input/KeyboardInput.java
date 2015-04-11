package edu.uhcl.team_drone.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.IntIntMap;
import edu.uhcl.team_drone.drone.Drone;
import edu.uhcl.team_drone.screens.playscreen.PlayScreen;

public class KeyboardInput extends InputAdapter {

    // drone that this input object controls
    private Drone owner;
    
    private int keysPressed = 0;

    private boolean pitchAdjusted;
    private boolean rollAdjusted;

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

    public KeyboardInput(Drone ownerIn) {
        this.owner = ownerIn;
    }

    public void update(float deltaTime) {
        // up/down
        if (keys.containsKey(UP)) {
            owner.altitude(2.0f);
        } else if (keys.containsKey(DOWN)) {
            owner.altitude(-2.0f);
        }

        // left/right
        if (keys.containsKey(LEFT)) {
            rollAdjusted = true;
            owner.setControlRollAmt(owner.getControlRollAmt() - 0.13f);
        } else if (keys.containsKey(RIGHT)) {
            rollAdjusted = true;
            owner.setControlRollAmt(owner.getControlRollAmt() + 0.13f);
        } else {
            rollAdjusted = false;
        }

        // forward/backward
        if (keys.containsKey(FORWARD)) {
            pitchAdjusted = true;
            owner.setControlPitchAmt(owner.getControlPitchAmt() - 0.03f);
        } else if (keys.containsKey(BACKWARD)) {
            pitchAdjusted = true;
            owner.setControlPitchAmt(owner.getControlPitchAmt() + 0.03f);
        } else {
            pitchAdjusted = false;
        }

        // rotations
        if (keys.containsKey(ROTATELEFT)) {
            owner.yaw(1.4f);
        } else if (keys.containsKey(ROTATERIGHT)) {
            owner.yaw(-1.4f);
        }

        if (keys.containsKey(DEBUGON)) {
            PlayScreen.debug.isVisible = true;
            PlayScreen.collisionWorld.debugOn = true;
            System.out.println("ENDED FROM KEYBOARD");
            //PlayScreen.setState(PlayScreen.GAME_STATES.ENDED);
            //keys.remove(DEBUGON, 0);
        }
        if (keys.containsKey(DEBUGOFF)) {
            PlayScreen.debug.isVisible = false;
            PlayScreen.collisionWorld.debugOn = false;
        }
    }

    @Override
    public boolean keyDown(int keycode) { // called by libgdx on every key press down        
        if (keycode != Input.Keys.ESCAPE) {
            keysPressed++;
            keys.put(keycode, keycode);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {// called by libgdx on every key release   
        if (keycode == Input.Keys.ESCAPE) {
            PlayScreen.setState(PlayScreen.GAME_STATES.PAUSED); 
        } else {
            keysPressed--;
            keys.remove(keycode, 0);
        }
        return true;
    }

    public boolean isKeyPressed() {
        return keysPressed == 0;
    }

    public boolean isPitchAdjusted() {
        return pitchAdjusted;
    }

    public boolean isRollAdjusted() {
        return rollAdjusted;
    }

}
