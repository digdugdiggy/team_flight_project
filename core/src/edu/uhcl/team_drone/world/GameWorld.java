package edu.uhcl.team_drone.world;

import com.badlogic.gdx.Gdx;
import edu.uhcl.team_drone.assets.Assets;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
import edu.uhcl.team_drone.drone.Drone;
import edu.uhcl.team_drone.main.Main;

public class GameWorld {

    private Model model; // Model used to store the flat plane floor 
    private ModelInstance skyBox;
    
    Array<ModelInstance> instances = new Array<ModelInstance>();

    private Environment environment;

    public GameWorld() {
        ModelBuilder modelBuilder = new ModelBuilder();

        // make floor
        // white plane
        model = modelBuilder.createBox(
                40000, 1, 40000,
                new Material(
                        ColorAttribute.createDiffuse(Color.GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        
        // grass plane
        //model = Assets.manager.get("GrassPlane_Better.g3db", Model.class);
        //instances.add(new ModelInstance(model, 0,-1,0));
//
//        // GRID LINES
//        model = modelBuilder.createBox(
//                20, 20, 40000,
//                new Material(
//                        ColorAttribute.createDiffuse(Color.BLACK)),
//                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
//        for (int i = 0; i < 24; i++) {
//            instances.add(new ModelInstance(model, -12000 + (i * 1000), 0, 0));
//        }
//        model = modelBuilder.createBox(
//                40000, 20, 20,
//                new Material(
//                        ColorAttribute.createDiffuse(Color.BLACK)),
//                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
//        for (int i = 0; i < 24; i++) {
//            instances.add(new ModelInstance(model, 0, 0, -12000 + (i * 1000)));
//        }


        // make boxes
//        model = modelBuilder.createBox(
//                500, 1000, 500,
//                new Material(
//                        ColorAttribute.createDiffuse(Color.WHITE)),
//                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
//
//        for (int i = 0; i < 50; i++) {
//            for (int j = 0; j < 50; j++) {
//                instances.add(new ModelInstance(model, -18000 + (j * 1800), 500, -18000 + (i * 1800)));
//            }
//        }


        // make lighting environment
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));
        //environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 0f, +100f, 0f));
        

        // make skybox
        model = Assets.manager.get("SKYBOX.g3db", Model.class);
        model.materials.get(0).set(new IntAttribute(IntAttribute.CullFace, 0));
        skyBox = new ModelInstance(model);     
        
        model = Assets.manager.get("newMaze.g3db", Model.class);
        model.materials.get(0).set(new IntAttribute(IntAttribute.CullFace, 0));
        //model.materials.add(new Material(ColorAttribute.createDiffuse(Color.WHITE)));
        
        instances.add(new ModelInstance(model));
    }

    public void render(Camera camIn, float dt, Drone droneIn) {
        
        
        Main.modelBatch.begin(camIn);
        skyBox.transform.setToTranslation(
                droneIn.getPosition().x, 0, droneIn.getPosition().z);
        Main.modelBatch.render(skyBox);
        Main.modelBatch.render(instances, environment);
        Main.modelBatch.end();
    }

    public void dispose() {
        model.dispose();
    }

}
