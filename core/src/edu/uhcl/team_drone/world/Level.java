package edu.uhcl.team_drone.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.utils.Array;
import edu.uhcl.team_drone.main.Main;
import java.util.Random;

public class Level {

    private final int LEVEL_SIZE = 100000;
    private final int GRID_SIZE = 2000;

    private final Vector3 CUBE_OFFSET = new Vector3(1000, 1000, 1000);

    private ModelBuilder modelBuilder;

    Array<ModelInstance> renderInstances = new Array<ModelInstance>();
    Array<ModelInstance> modelInstances = new Array<ModelInstance>();
    Array<btCollisionObject> colObjs = new Array<btCollisionObject>();

    private Environment environment;
    
    Random rand;

    public Level() {
        rand = new Random();
        modelBuilder = new ModelBuilder();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 0f, +100f, 0f));

        makeFloor();

        for (int i = 0; i < 80; i++) {
            int a = randInt(-10, 10);
            int b = randInt(-10, 10);
            while (a == -1 || a == 0 || a == 1) {
                a = randInt(-10, 10);
            }
            while (b == -1 || b == 0 || b == 1) {
                b = randInt(-10, 10);
            }
            makeCube(a, 0, b);
            makeCube(a, 1, b);
            makeCube(a, 2, b);
        }

        for (ModelInstance instance : modelInstances) {
            btCollisionShape colShape = new btBoxShape(CUBE_OFFSET);
            btCollisionObject obj = new btCollisionObject();
            obj.setCollisionShape(colShape);
            obj.setWorldTransform(instance.transform);
            colObjs.add(obj);
        }
    }

    private void makeFloor() {
        Model floorModel = modelBuilder.createBox(
                LEVEL_SIZE, 1, LEVEL_SIZE,
                new Material(ColorAttribute.createDiffuse(Color.LIGHT_GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);        
        renderInstances.add(new ModelInstance(floorModel));

        Model grid = modelBuilder.createLineGrid(
                LEVEL_SIZE / GRID_SIZE, LEVEL_SIZE / GRID_SIZE, GRID_SIZE, GRID_SIZE,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);        
        renderInstances.add(new ModelInstance(grid));
        modelInstances.add(new ModelInstance(grid));
    }

    private void makeCube(float x, float y, float z) {

        Vector3 cubePos = new Vector3(
                CUBE_OFFSET.x + (CUBE_OFFSET.x * x*2),
                CUBE_OFFSET.y + (CUBE_OFFSET.y * y*2),
                CUBE_OFFSET.z + (CUBE_OFFSET.z * z*2)
        );

        Model cube = modelBuilder.createBox(
                GRID_SIZE, GRID_SIZE, GRID_SIZE,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance cubeModel = new ModelInstance(cube, cubePos);
        renderInstances.add(cubeModel);
        modelInstances.add(cubeModel);
        
        Model grid = modelBuilder.createLineGrid(
                1, 1, GRID_SIZE, GRID_SIZE,
                new Material(ColorAttribute.createAmbient(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        ModelInstance grid1Model = new ModelInstance(grid);
        grid1Model.transform.setToRotation(Vector3.X, 90);
        grid1Model.transform.setTranslation(cubePos.x, cubePos.y, 2000 + (CUBE_OFFSET.z * z*2));
        renderInstances.add(grid1Model);

        ModelInstance grid2Model = new ModelInstance(grid);
        grid2Model.transform.setToRotation(Vector3.X, 270);
        grid2Model.transform.setTranslation(cubePos.x, cubePos.y, (CUBE_OFFSET.z * z*2));
        renderInstances.add(grid2Model);

        ModelInstance grid3Model = new ModelInstance(grid);
        grid3Model.transform.setToRotation(Vector3.Z, 90);
        grid3Model.transform.setTranslation(2000+(CUBE_OFFSET.x * x*2), cubePos.y, cubePos.z);
        renderInstances.add(grid3Model);

        ModelInstance grid4Model = new ModelInstance(grid);
        grid4Model.transform.setToRotation(Vector3.Z, 270);
        grid4Model.transform.setTranslation((CUBE_OFFSET.x * x*2), cubePos.y, cubePos.z);
        renderInstances.add(grid4Model);

    }

    public void render() {
        Main.modelBatch.begin(Main.cam);
        Main.modelBatch.render(renderInstances, environment);
        Main.modelBatch.end();
    }

    public void dispose() {
        for (btCollisionObject obj : colObjs) {
            obj.dispose();
        }        
        renderInstances.clear();
    }

    public Array<btCollisionObject> getColObjs() {
        return colObjs;
    }

    private int randInt(int min, int max) {

        rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }


}
