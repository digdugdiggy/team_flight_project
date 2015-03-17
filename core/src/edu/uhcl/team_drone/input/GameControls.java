package edu.uhcl.team_drone.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.IntIntMap;

public class GameControls extends InputAdapter{

    private final IntIntMap keys = new IntIntMap();
    
    private int LEFT = Input.Keys.A;
    private int RIGHT = Input.Keys.D;
    private int FORWARD = Input.Keys.W;
    private int BACKWARD = Input.Keys.S;
    private int UP = Input.Keys.UP;
    private int DOWN = Input.Keys.DOWN;
    private int ROTATELEFT = Input.Keys.LEFT;
    private int ROTATERIGHT = Input.Keys.RIGHT;

    public void setControl(int newInputID) {
        
    }

}
