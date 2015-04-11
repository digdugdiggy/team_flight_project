/*
// This class displays the menu shown after the player has completed the maze
*/
package edu.uhcl.team_drone.screens.playscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.main.Main;
import edu.uhcl.team_drone.ui.PlayUI;

public class EndDisplay {
    
    private final Main game;

    private final Stage stage;
    private Table table;
    
    private Label timeLabel;
    

    public EndDisplay(Main gameIn) {
        this.game = gameIn;
        this.stage = new Stage(new FitViewport(Main.RESOLUTION.x, Main.RESOLUTION.y));

        table = new Table();        
        table.setFillParent(true);
        table.setVisible(true);      
        table.defaults().center().pad(10).height(80).align(Align.center);

        createMenu();
        stage.addActor(table);
    }

    private void createMenu() {                           
        
        timeLabel = new Label("Your Time: " + PlayUI.timeIndicator.getTime(), Assets.labelStyle);
        table.add(timeLabel);
        table.row();

        TextButton restartButton = new TextButton("Restart", Assets.blueTextBtnStyle);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) { 
                Gdx.input.setInputProcessor(PlayScreen.getDrone().input.getInputProcessor());
                PlayScreen.setState(PlayScreen.GAME_STATES.START);                
            }
        });
        table.add(restartButton).center();
    }

    public void render() {
        timeLabel.setText("Your Time: " + PlayUI.timeIndicator.getTime());
        Gdx.input.setInputProcessor(stage);
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
