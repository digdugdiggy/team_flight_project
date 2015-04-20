/* * * * * * * * * * * * * * * * * *
* PROGRAMMER: CHARLES FAHSELT
*
* COURSE: CINF 4388 SENIOR PROJECT 2015
*
* PURPOSE: Handles swapping between different levels.
*
 * * * * * * * * * * * * * * * * * */

package edu.uhcl.team_drone.world;

import com.badlogic.gdx.graphics.Camera;
import edu.uhcl.team_drone.drone.Drone;

public class WorldManager { 
    
    public final Level programmaticLevel;
    
    public WorldManager() {       
        programmaticLevel = new Level();
    }

    public void render(Camera camIn, float dt, Drone droneIn) {       
        programmaticLevel.render();
    }
    
    public void dispose(){
        programmaticLevel.dispose();
    }

}
