package edu.uhcl.team_drone.world;

import com.badlogic.gdx.graphics.Camera;
import edu.uhcl.team_drone.drone.Drone;

public class WorldManager {

    private final MazeLevel mazeLevel;

    public WorldManager() {
        mazeLevel = new MazeLevel();
    }

    public void render(Camera camIn, float dt, Drone droneIn) {
        mazeLevel.render(camIn, dt, droneIn);
    }
    
    public void dispose(){
        mazeLevel.dispose();
    }

}
