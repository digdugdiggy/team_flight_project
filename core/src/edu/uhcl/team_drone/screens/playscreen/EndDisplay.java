/*
// This class displays the menu shown after the player has completed the maze
*/
package edu.uhcl.team_drone.screens.playscreen;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.uhcl.team_drone.main.Main;

public class EndDisplay {
    
    private final Main game;

    private final Stage stage;
    private Table table;
    

    public EndDisplay(Main gameIn) {
        this.game = gameIn;
        this.stage = new Stage(new FitViewport(Main.RESOLUTION.x, Main.RESOLUTION.y));

        table = new Table();
        table.defaults().center().pad(10).height(80).align(Align.center);
        table.setFillParent(true);
        table.setVisible(false);

        createMenu();
    }

    private void createMenu() {

    }

    public void render() {

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
