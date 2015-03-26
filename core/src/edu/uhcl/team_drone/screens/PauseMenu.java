package edu.uhcl.team_drone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.main.Main;

public class PauseMenu {

    Stage stage;
    Table pauseTableLayout;
    Main game;
    PlayScreen playScreen;
    
    public PauseMenu(Main gameIn, PlayScreen screenIn){
        this.playScreen = screenIn;
        this.game = gameIn;
        stage = new Stage(new FitViewport(800, 600));
        //Gdx.input.setInputProcessor(stage);
        
        // create table
        pauseTableLayout = new Table();
        pauseTableLayout.defaults().center().pad(10).height(80).align(Align.center);
        pauseTableLayout.setFillParent(true);
        
        createPauseMenu(stage);
    }

    private void createPauseMenu(Stage stageIn) {
        // make buttons to go in table
        TextButton resumeButton = new TextButton("Resume", Assets.blueTextBtnStyle);
        TextButton restartButton = new TextButton("Restart", Assets.blueTextBtnStyle);
        TextButton optionsButton = new TextButton("Options", Assets.blueTextBtnStyle);
        TextButton exitToMenuButton = new TextButton("Exit to Menu", Assets.blueTextBtnStyle);
        TextButton exitToDesktopButton = new TextButton("Exit to Desktop", Assets.blueTextBtnStyle);
        
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayScreen.isPaused = false;
                Gdx.input.setInputProcessor(playScreen.getDrone().inputCmpnt); 
            }
        });
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {}
        });
        exitToMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayScreen.isPaused = false;                                 
                game.setScreen(new MainMenuScreen(game));
            }
        });
        exitToDesktopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // add buttons to table
        pauseTableLayout.add(resumeButton).align(Align.center);
        pauseTableLayout.row();
        pauseTableLayout.add(restartButton).align(Align.center);
        pauseTableLayout.row();
        pauseTableLayout.add(optionsButton).align(Align.center);
        pauseTableLayout.row();
        pauseTableLayout.add(exitToMenuButton).align(Align.center);
        pauseTableLayout.row();
        pauseTableLayout.add(exitToDesktopButton).align(Align.center);

        stage.addActor(pauseTableLayout);
    }

    public void render(boolean isPaused) {
        //pauseTableLayout.setVisible(isPaused);
        if (isPaused) {
            Gdx.input.setInputProcessor(stage);
            stage.act();
            stage.draw();
        }
    }   
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);        
    }
}
