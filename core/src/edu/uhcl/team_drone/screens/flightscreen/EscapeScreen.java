/* *****************************************************************************
* Programmer: Michael Truncale
* *Code is modified from original code by Charles Fahselt
* Course: CSCI 4388.01 - Senior Project
* Date: April 22, 2015
* Assignment: Senior Project
* Environment: Windows 7 - 64 bit
* IDE: Compiled and tested under NetBeans 8.0.2 / JDK 1.8
/******************************************************************************/

package edu.uhcl.team_drone.screens.flightscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.input.hardware.HardwareInterface;
import edu.uhcl.team_drone.main.Main;



public class EscapeScreen {
    private final Stage stage;
    private final Table tableLayout;
    private final Main game;
    private final InputMultiplexer inputMixer;
    private final FlyScreen flyScreen;
    private HardwareInterface input;

    
    public EscapeScreen(Main gameIn, FlyScreen flyIn, HardwareInterface gameInput) {
        game = gameIn;
        flyScreen = flyIn;
        input = gameInput;
        stage = new Stage(new FitViewport(Main.RESOLUTION.x, Main.RESOLUTION.y));

        inputMixer = new InputMultiplexer();
        inputMixer.addProcessor(stage);
        inputMixer.addProcessor(input);

        tableLayout = new Table();
        tableLayout.defaults().center().pad(10).height(80).align(Align.center);
        tableLayout.setFillParent(true);

        createEscapeMenu();
    }

    
    private void createEscapeMenu() {
        TextButton resumeButton = new TextButton("Back to Flight", Assets.blueTextBtnStyle);
        TextButton resetDrone = new TextButton("Reset Drone", Assets.blueTextBtnStyle);
        TextButton exitToMenuButton = new TextButton("Exit to Menu", Assets.blueTextBtnStyle);
        TextButton exitToDesktopButton = new TextButton("Exit to Desktop", Assets.blueTextBtnStyle);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                flyScreen.setHideEscapeMenu();
                
            }
        });
        
        resetDrone.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                input.droneCommand("disableEmergency");
                flyScreen.setHideEscapeMenu();
            }
        });
        
        exitToMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                flyScreen.setHideEscapeMenu();
                game.setScreen(Main.mainMenuScreen);
            }
        });
        
        exitToDesktopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


        tableLayout.add(resumeButton).align(Align.center);
        tableLayout.row();
        tableLayout.add(resetDrone).align(Align.center);
        tableLayout.row();
        tableLayout.add(exitToMenuButton).align(Align.center);
        tableLayout.row();
        tableLayout.add(exitToDesktopButton).align(Align.center);

        stage.addActor(tableLayout);
    }

    
    public void render() {
        Gdx.input.setInputProcessor(inputMixer);

        stage.act();
        stage.draw();
    }
    
    
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
