/* * * * * * * * * * * * * * * * * *
* PROGRAMMER: CHARLES FAHSELT
*
* COURSE: CINF 4388 SENIOR PROJECT 2015
*
* PURPOSE: This class is responsible for rendering debug information.
*            It is not usually visible during gameplay.
*
 * * * * * * * * * * * * * * * * * */

package edu.uhcl.team_drone.debug;

import com.badlogic.gdx.Gdx;
import edu.uhcl.team_drone.assets.Assets;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.uhcl.team_drone.drone.Drone;

public class DebugRender {

    private Drone owner;
    private SpriteBatch sb;  
   
    public boolean isVisible = false;

    public DebugRender(Drone ownerIn) {
        this.owner = ownerIn;
        sb = new SpriteBatch();  
    }

    public void update() {
        if (isVisible) {
            sb.begin();
            String temp;
            temp = "FPS: " + Gdx.graphics.getFramesPerSecond();
            writeString(temp, 10, 400);
            sb.end();
        }
    }

    private void writeString(String string, float x, float y) {
        Assets.smallFont.draw(sb, string, x, y);
    }

    public void dispose() {
        sb.dispose();       
    }

}
