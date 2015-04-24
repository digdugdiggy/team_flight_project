/* *****************************************************************************
* Programmer: Michael Truncale
* *Code is modified from original code by Charles Fahselt
* Course: CSCI 4388.01 - Senior Project
* Date: April 20, 2015
* Assignment: Senior Project
* Environment: Windows 7 - 64 bit
* IDE: Compiled and tested under NetBeans 8.0.2 / JDK 1.8
/******************************************************************************/

package edu.uhcl.team_drone.screens.flightscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.drone.Drone;
import edu.uhcl.team_drone.input.hardware.HardwareInterface;
import edu.uhcl.team_drone.main.Main;



public class FlyScreen implements Screen {
    public Stage stage;    
    private Main game;
    private Drone drone;
    private HardwareUI ui;
    private HardwareInterface input;
    private EscapeScreen escapeScreen;
    private boolean escapeScreenActive;
    InputMultiplexer iMixer;
    TextButton loadVideoButton;
    TextButton closeVideoButton;

    
    public FlyScreen(Main gameIn) {
        game = gameIn;     
        escapeScreenActive = false;
    }

    
    @Override
    public void show() {
        Gdx.graphics.setDisplayMode(600, 300, false);
        stage = new Stage(new FitViewport(Main.RESOLUTION.x, Main.RESOLUTION.y));        
        
        drone = new Drone(false);
        input = new HardwareInterface(game, this);
        escapeScreen = new EscapeScreen(game, this, input);
        ui = new HardwareUI(this, input,drone,stage.getViewport());
        
        iMixer = new InputMultiplexer();
        iMixer.addProcessor(stage);
        iMixer.addProcessor(input);
        
        loadVideoButton = new TextButton("Load Video", Assets.blueTextBtnStyle);
        loadVideoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                input.loadVideo();
                displayCloseVideoButton();
            }
        });
                
        closeVideoButton = new TextButton("Close Video", Assets.blueTextBtnStyle);
        closeVideoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                input.closeVideo();
                displayLoadVideoButton();
            }
        });
                
        TextButton resetDroneButton = new TextButton("Reset Drone", Assets.blueTextBtnStyle);
        resetDroneButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                input.droneCommand("disableEmergency");
            }
        });
                
        Table resetLayout = new Table();
        resetLayout.setFillParent(true);  
        resetLayout.bottom().padTop(0);
        resetLayout.add(resetDroneButton).size(250, 90).padRight(250);
        stage.addActor(resetLayout);
        
        displayLoadVideoButton();
    }

    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
        ui.render(delta);

        input.update(delta);
        Gdx.input.setInputProcessor(iMixer);
        
        if (escapeScreenActive == true){
            displayEscapeMenu();
        }
    }
    
    public void displayEscapeMenu(){
        int x = Gdx.graphics.getWidth();
        int y = Gdx.graphics.getHeight();

        escapeScreen.resize(x,y);
        escapeScreen.render();
    }
    
    
    public void setHideEscapeMenu(){
        escapeScreenActive = false;
    }
    
    
    public void setShowEscapeMenu(){
        escapeScreenActive = true;
    }
    
    
    public void setToggleEscapeMenu(){
        if (escapeScreenActive == true){
            escapeScreenActive = false;
        }
        else {
            escapeScreenActive = true;
        }
    }
    
    
    public void displayLoadVideoButton(){
        Table videoLayout = new Table();
        videoLayout.setFillParent(true);  
        videoLayout.bottom().padTop(0);
        videoLayout.add(loadVideoButton).size(250, 90).padLeft(250);
        stage.addActor(videoLayout);
    }
    
    
    public void displayCloseVideoButton(){
        Table videoLayout2 = new Table();
        videoLayout2.setFillParent(true);  
        videoLayout2.bottom().padTop(0);
        videoLayout2.add(closeVideoButton).size(250, 90).padLeft(250);
        stage.addActor(videoLayout2);
    }
    
    
    @Override
    public void resize(int width, int height) {
       stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

