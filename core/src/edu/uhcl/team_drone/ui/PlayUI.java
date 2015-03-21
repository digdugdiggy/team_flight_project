package edu.uhcl.team_drone.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.drone.Drone;

public class PlayUI {

    private Drone drone;
    private AttitudeIndicator attitudeIndicator;
    private Image hudBackground;

    private Stage stage;

    private Table table;

    public PlayUI(Drone owner, Viewport viewIn) {
       
        this.drone = owner;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        

        table = new Table();
        table.setFillParent(true);
        hudBackground = new Image(Assets.manager.get("2d/hud/uiFrames.png", Texture.class));
        hudBackground.setWidth(stage.getWidth());
        hudBackground.setHeight(stage.getHeight());
        
        
        attitudeIndicator = new AttitudeIndicator(table);

        
        
       
        
        
        stage.addActor(table);
        stage.addActor(hudBackground);
        
        
    }

    public void render(float dt) {
        attitudeIndicator.update(drone);
        stage.act();
        stage.draw();
    }
    public void dispose(){
        stage.dispose();
    }
}
