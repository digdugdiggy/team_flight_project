package edu.uhcl.team_drone.world;

import com.badlogic.gdx.graphics.Camera;
import edu.uhcl.team_drone.drone.Drone;

public class WorldManager {

    private final MazeLevel mazeLevel;
    public final Level programmaticLevel;
    
    

    public WorldManager() {
        mazeLevel = new MazeLevel();
        programmaticLevel = new Level();
    }

    public void render(Camera camIn, float dt, Drone droneIn) {
        //mazeLevel.render(camIn, dt, droneIn);
        programmaticLevel.render();
    }
    
    public void dispose(){
        mazeLevel.dispose();
    }

}
