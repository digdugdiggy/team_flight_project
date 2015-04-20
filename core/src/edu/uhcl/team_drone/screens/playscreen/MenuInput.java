/* * * * * * * * * * * * * * * * * *
* PROGRAMMER: CHARLES FAHSELT
*
* COURSE: CINF 4388 SENIOR PROJECT 2015
*
* PURPOSE: This class allows the menu to open and close with the same ESC button
*
 * * * * * * * * * * * * * * * * * */
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
