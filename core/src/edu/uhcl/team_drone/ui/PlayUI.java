package edu.uhcl.team_drone.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.uhcl.team_drone.drone.Drone;

// This class is responsible for displaying the in-game HUD.
// It displays data like Altitude, attitude, orientation
// liftoff button

public class PlayUI {

    private Drone drone;
    
    private AttitudeIndicator attitudeIndicator;    
    private CompassIndicator compassIndicator;
    public static Timer timeIndicator;

    private Stage stage;   

    public PlayUI(Drone owner, Viewport viewIn) {       
        this.drone = owner;
        
        // make stage, let it acept input for button clicks, etc
        stage = new Stage(new FitViewport(800, 600));           
        
        // create and add timer
        Table timerTable = new Table();
        timerTable.setFillParent(true);
        timeIndicator = new Timer(timerTable);
        timeIndicator.start();
        stage.addActor(timerTable);  

        // create Attitude display
        Table attitudeTable = new Table();
        attitudeTable.setFillParent(true);
        // create and add the attitude indicator to HUD        
        attitudeIndicator = new AttitudeIndicator(attitudeTable);
        stage.addActor(attitudeTable);

        // create and add compass
        Table compassTable = new Table();
        compassIndicator = new CompassIndicator(compassTable);
        stage.addActor(compassTable);        
        
    }


    public void render(float dt) {        
        attitudeIndicator.update(drone);
        compassIndicator.update(drone);
        timeIndicator.update(dt);
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);        
    }
}
