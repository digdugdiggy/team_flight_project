/* * * * * * * * * * * * * * * * * *
* PROGRAMMER: CHARLES FAHSELT
*
* COURSE: CINF 4388 SENIOR PROJECT 2015
*
* PURPOSE: UI shown during gameplay.
*
 * * * * * * * * * * * * * * * * * */
package edu.uhcl.team_drone.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.uhcl.team_drone.drone.Drone;
import edu.uhcl.team_drone.main.Main;

public class PlayUI {

    private Drone drone;
    
    private AttitudeIndicator attitudeIndicator;    
    private CompassIndicator compassIndicator;
    public static Timer timeIndicator;

    private Stage stage;   

    public PlayUI(Drone owner, Viewport viewIn) {       
        this.drone = owner;
        
        // make stage, let it acept input for button clicks, etc
        stage = new Stage(new FitViewport(Main.RESOLUTION.x, Main.RESOLUTION.y));           
        
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
