package edu.uhcl.team_drone.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.drone.Drone;
import edu.uhcl.team_drone.screens.PlayScreen;

public class PlayUI {

    private Drone drone;
    private AttitudeIndicator attitudeIndicator;

    private Stage stage;

    private Table table;

    public PlayUI(Drone owner, Viewport viewIn) {
       
        this.drone = owner;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);

        //table.debugAll();    
        
//        Table innerTable = new Table();
//        table.setFillParent(true);
//        
//        TextButton propUp = new TextButton("P up", Assets.smallTextBtnStyle);
//        propUp.scaleBy(0.25f);
//        TextButton propDown = new TextButton("P down", Assets.smallTextBtnStyle);
//        
//        innerTable.add(propUp).size(70,50);
//        innerTable.row();
//        innerTable.add(propDown).size(70,50);
//        table.add(innerTable).bottom().size(100,100);
 
        
        attitudeIndicator = new AttitudeIndicator(table);

        
        
       
        
        
        stage.addActor(table);
        
        
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
