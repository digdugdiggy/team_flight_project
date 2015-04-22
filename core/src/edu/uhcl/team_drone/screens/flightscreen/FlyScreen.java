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
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.uhcl.team_drone.drone.Drone;
import edu.uhcl.team_drone.input.hardware.HardwareInterface;
import edu.uhcl.team_drone.main.Main;
import edu.uhcl.team_drone.ui.PlayUI;



public class FlyScreen implements Screen {
    private Stage stage;    
    private Main game;
    private Drone drone;
    private HardwareUI ui;
    private HardwareInterface input;
    private EscapeScreen escapeScreen;
    private boolean escapeScreenActive;

    
    public FlyScreen(Main gameIn) {
        game = gameIn;     
        escapeScreenActive = false;
    }

    
    @Override
    public void show() {
        stage = new Stage(new FitViewport(Main.RESOLUTION.x, Main.RESOLUTION.y));
        drone = new Drone(false);
        input = new HardwareInterface(game, this);
        escapeScreen = new EscapeScreen(game, this, input);

        ui = new HardwareUI(input,drone,stage.getViewport());
        Gdx.input.setInputProcessor(input);
    }

    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        input.update(delta);

        stage.act();
        stage.draw();
        ui.render(delta);
        
        if (escapeScreenActive == true){
            displayEscapeMenu();
        }
    }
    
    public void displayEscapeMenu(){
        escapeScreen.render();
    }
    
    public void setHideEscapeMenu(){
        escapeScreenActive = false;
        //escapeScreen.dispose();
    }
    
    public void setShowEscapeMenu(){
        escapeScreenActive = true;
        //escapeScreen.dispose();
    }
    
    public void setToggleEscapeMenu(){
        if (escapeScreenActive == true){
            escapeScreenActive = false;
        }
        else {
            escapeScreenActive = true;
        }
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

