package edu.uhcl.team_drone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.uhcl.team_drone.drone.Drone;
import edu.uhcl.team_drone.input.HardwareInputComponent;
import edu.uhcl.team_drone.main.Main;
import edu.uhcl.team_drone.ui.PlayUI;

//
// This class is a screen that shows the video from a physical drone, and 
// allows the user to control said drone.
//

public class FlyScreen implements Screen {

    private Stage stage;    

    private Main game;
    private PlayUI ui;
    private Drone drone; // unneccessary, need to refactor for this bit.
    
    private HardwareInputComponent input;
    
    public FlyScreen(Main gameIn) {
        this.game = gameIn;                
    }

    @Override
    public void show() {
        
        stage = new Stage(new FitViewport(800, 600));
        //Gdx.input.setInputProcessor(stage);
        
        
        drone = new Drone(false);
        input = new HardwareInputComponent();
        
        // TODO
        // Drone video input stuff here
        //
        
        ui = new PlayUI(drone,stage.getViewport());
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
