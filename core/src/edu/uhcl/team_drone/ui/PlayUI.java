package edu.uhcl.team_drone.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.drone.Drone;

// This class is responsible for displaying the in-game HUD.
// It displays data like Altitude, attitude, orientation
// liftoff button

public class PlayUI {

    private Drone drone;
    
    private AttitudeIndicator attitudeIndicator;    
    private CompassIndicator compassIndicator;

    private Stage stage;   

    public PlayUI(Drone owner, Viewport viewIn) {       
        this.drone = owner;
        
        // make stage, let it acept input for button clicks, etc
        stage = new Stage();           

         // create and add the background hud image
        Image hudBackground = new Image(Assets.manager.get("2d/hud/uiFrames.png", Texture.class));
        hudBackground.setWidth(stage.getWidth());
        hudBackground.setHeight(stage.getHeight());
        //hudBackground.setZIndex(0);
        stage.addActor(hudBackground);
        
        //table to organize hud
        Table table = new Table();        
        table.setFillParent(true);
        
        // create and add the attitude indicator to HUD        
        attitudeIndicator = new AttitudeIndicator(table);
        stage.addActor(table);
        
//        // create and add compass
        Table compassTable = new Table();
        compassIndicator = new CompassIndicator(compassTable);
        
        stage.addActor(compassTable);
    }

    public void render(float dt) {        
        attitudeIndicator.update(drone);
        compassIndicator.update(drone);
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
