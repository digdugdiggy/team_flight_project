package edu.uhcl.team_drone.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.drone.Drone;

public class AttitudeIndicator {

    private static final Vector2 INDICATOR_SIZE = new Vector2(150, 150);

    private Image indicatorMoving;

    private float indicatorHomePos;

    public AttitudeIndicator(Table table) {

        Image indicatorStatic = new Image(
                Assets.manager.get("2d/hud/Attitude-Outer-Small.png", Texture.class));
        indicatorStatic.setOrigin(indicatorStatic.getWidth() / 2, indicatorStatic.getHeight() / 2);
        Image indicatorStaticBlueBG = new Image(
                Assets.manager.get("2d/hud/Attitude-Inner-Small.png", Texture.class));
        indicatorStatic.setOrigin(indicatorStatic.getWidth() / 2, indicatorStatic.getHeight() / 2);

        indicatorMoving = new Image(
                Assets.manager.get("2d/hud/Attitude-Inner-Lines.png", Texture.class));

        Stack stack = new Stack();
        
        
        stack.add(indicatorStaticBlueBG); 
        stack.add(indicatorMoving);
         stack.add(indicatorStatic);
        //stack.debugAll();        

        table.add(stack).size(INDICATOR_SIZE.x, INDICATOR_SIZE.y);
        indicatorMoving.setOrigin(INDICATOR_SIZE.x / 2, INDICATOR_SIZE.y / 2);
        table.right().bottom();
        indicatorHomePos = indicatorMoving.getY();
    }

    protected void update(Drone droneIn) {
        float rotation = droneIn.gyro.getCurrentRoll() * 45;
        indicatorMoving.setRotation(rotation);

        float pitch = droneIn.gyro.getCurrentPitch();

        indicatorMoving.setPosition(
                indicatorMoving.getX(),
                indicatorHomePos + (pitch * 45));
    }
}
