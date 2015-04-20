/* * * * * * * * * * * * * * * * * *
* PROGRAMMER: CHARLES FAHSELT
*
* COURSE: CINF 4388 SENIOR PROJECT 2015
*
* PURPOSE: Displays and updates compass direction
*
 * * * * * * * * * * * * * * * * * */
package edu.uhcl.team_drone.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.drone.Drone;

// This class is responsible for updating and displaying the HUD's 
// adirection indicator, which displays Compass directions
public class CompassIndicator {

    private static final Vector2 INDICATOR_SIZE = new Vector2(190, 190);

    // portion of the indicator that rotates
    private Image indicatorMoving;

    // keeps track of the initial position for the moving indicator
    private float indicatorHomePos;

    public CompassIndicator(Table tableIn) {
        // container that holds two objects on top of each other
        Stack stack = new Stack();

        // make bg for indicator and add it to stack
        Image indicatorStaticBG = new Image(
                Assets.manager.get("2d/hud/compassIndicator/CompassOuter.png", Texture.class));
        stack.add(indicatorStaticBG);

        // moving portion of the attitude indicator, add to stack
        indicatorMoving = new Image(
                Assets.manager.get("2d/hud/compassIndicator/CompassInner.png", Texture.class));
        indicatorMoving.setOrigin(INDICATOR_SIZE.x / 2, INDICATOR_SIZE.y / 2);
        stack.add(indicatorMoving);

        // position the stack into the table
        tableIn.add(stack).size(INDICATOR_SIZE.x, INDICATOR_SIZE.y);
        tableIn.left().bottom();
        indicatorHomePos = indicatorMoving.getRotation();
    }

    protected void update(Drone droneIn) {
        float droneAngle = droneIn.gyroCmpnt.getCurrentYaw();
        indicatorMoving.setRotation(droneAngle);
    }
}
