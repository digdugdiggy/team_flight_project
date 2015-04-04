package edu.uhcl.team_drone.screens.playscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class MenuInput extends InputAdapter {
        // Input class to allow keyUp input.
    // This prevents pause menu flashing from holding down escape

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            PlayScreen.setState(PlayScreen.GAME_STATES.PLAYING); 
            Gdx.input.setInputProcessor(PlayScreen.getDrone().input.getInputProcessor());
        }
        return true;
    }
}
