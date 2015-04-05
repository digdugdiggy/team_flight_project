/*
 // This class displays the pause screen shown after pressing ESC 
 // during the main game.
 */
package edu.uhcl.team_drone.screens.playscreen;

import edu.uhcl.team_drone.screens.mainmenu.MainMenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import edu.uhcl.team_drone.ui.PlayUI;

public class PauseDisplay {

    private final Stage stage;
    private final Table pauseTableLayout;
    private final Main game;

    private final MenuInput menuKeyInput;
    private final InputMultiplexer inputMixer;

    public PauseDisplay(Main gameIn) {
        this.game = gameIn;
        this.stage = new Stage(new FitViewport(Main.RESOLUTION.x, Main.RESOLUTION.y));

        // Create input and add to multiplexer
        inputMixer = new InputMultiplexer();
        inputMixer.addProcessor(stage);
        menuKeyInput = new MenuInput();
        inputMixer.addProcessor(menuKeyInput);

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
        TextButton exitToMenuButton = new TextButton("Exit to Menu", Assets.blueTextBtnStyle);
        TextButton exitToDesktopButton = new TextButton("Exit to Desktop", Assets.blueTextBtnStyle);

        // add action listeners to buttons
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayScreen.setState(PlayScreen.GAME_STATES.PLAYING);
                Gdx.input.setInputProcessor(PlayScreen.getDrone().input.getInputProcessor());
            }
        });
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayScreen.getDrone().moveToPosition(1000, 1000, 1000);
                PlayUI.timeIndicator.reset();
                PlayScreen.setState(PlayScreen.GAME_STATES.PLAYING);
                Gdx.input.setInputProcessor(PlayScreen.getDrone().input.getInputProcessor());
            }
        });
        exitToMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayScreen.setState(PlayScreen.GAME_STATES.PLAYING);
                game.setScreen(Main.mainMenuScreen);
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
        pauseTableLayout.add(exitToMenuButton).align(Align.center);
        pauseTableLayout.row();
        pauseTableLayout.add(exitToDesktopButton).align(Align.center);

        // add table to stage
        stage.addActor(pauseTableLayout);
    }

    public void render() {
        // if the game is paused, allow input on this pause menu, and display it
        Gdx.input.setInputProcessor(inputMixer);
        stage.act();
        stage.draw();

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
