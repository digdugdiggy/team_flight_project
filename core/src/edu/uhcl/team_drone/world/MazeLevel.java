package edu.uhcl.team_drone.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.utils.Array;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.drone.Drone;
import edu.uhcl.team_drone.main.Main;

public class MazeLevel {

    private Model terrainModel;
    
    Array<ModelInstance> instances = new Array<ModelInstance>();

    private Environment environment;

    public MazeLevel() {
        // make lighting environment
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));
        //environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 0f, +100f, 0f));

        // load maze terrain
        terrainModel = Assets.manager.get("3d/levels/BestMaze.g3db", Model.class);
        terrainModel.materials.get(0).set(new IntAttribute(IntAttribute.CullFace, 0));

        instances.add(new ModelInstance(terrainModel));
    }

    public void render(Camera camIn, float dt, Drone droneIn) {
        Main.modelBatch.begin(camIn);
        Main.modelBatch.render(instances, environment);
        Main.modelBatch.end();
    }

    public void dispose() {
        terrainModel.dispose();
    }
}
