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
import edu.uhcl.team_drone.world.mapgen.MapGenerator;
import java.util.Random;

public class Level {

    private final int LEVEL_SIZE = 100000;
    private final int GRID_SIZE = 3000;
    private final int MAZE_DIMENSION = 11;
    private final Vector3 CUBE_OFFSET = new Vector3(GRID_SIZE / 2, GRID_SIZE / 2, GRID_SIZE / 2);

    private final ModelBuilder modelBuilder;

    private Array<ModelInstance> renderInstances = new Array<ModelInstance>();
    private Array<ModelInstance> modelInstances = new Array<ModelInstance>();
    protected Array<btCollisionObject> colObjs = new Array<btCollisionObject>();

    private Environment environment;

    private Random rand;

    private MapGenerator mapgen;

    private ModelInstance finalCube;

    public Level() {
        rand = new Random();
        modelBuilder = new ModelBuilder();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 0f, +100f, 0f));

        generateNewMap();

        // make collision shapes for all of the instances created
        Vector3 wallBlockSize = new Vector3(CUBE_OFFSET.x, CUBE_OFFSET.y * 3, CUBE_OFFSET.z);
        for (ModelInstance instance : modelInstances) {
            btCollisionShape colShape = new btBoxShape(wallBlockSize);
            btCollisionObject obj = new btCollisionObject();
            obj.setCollisionShape(colShape);
            obj.setWorldTransform(instance.transform);
            colObjs.add(obj);
        }

        // add ending shape
        btCollisionShape colShape = new btBoxShape(CUBE_OFFSET);
        btCollisionObject obj = new btCollisionObject();
        obj.setCollisionShape(colShape);
        obj.setWorldTransform(finalCube.transform);
        obj.setUserIndex(100);
        colObjs.add(obj);
    }

    private void generateNewMap() {
        mapgen = new MapGenerator(MAZE_DIMENSION, MAZE_DIMENSION);
        char[][] charMap = mapgen.getMap();

        for (int x = 0; x < MAZE_DIMENSION; x++) {
            for (int y = 0; y < MAZE_DIMENSION; y++) {
                if (charMap[y][x] == 'X') {
                    makeCubeStack(y - MAZE_DIMENSION / 2, x - MAZE_DIMENSION / 2, Color.GRAY);
                } else if (charMap[y][x] == '.') {
                    makeFloorTile(y - MAZE_DIMENSION / 2, x - MAZE_DIMENSION / 2);
                } else if (charMap[y][x] == '#') {
                    makeSingleCube(y - MAZE_DIMENSION / 2, 0.5f, x - MAZE_DIMENSION / 2, Color.GREEN);
                    makeFloorTile(y - MAZE_DIMENSION / 2, x - MAZE_DIMENSION / 2);
                } else if (charMap[y][x] == 'E') {
                    makeFinalCube(y - MAZE_DIMENSION / 2, 0.5f, x - MAZE_DIMENSION / 2, Color.BLUE);
                    makeFloorTile(y - MAZE_DIMENSION / 2, x - MAZE_DIMENSION / 2);
                }
            }
        }
    }

    private void makeFloorTile(float x, float z) {
        Vector3 tilePos = new Vector3(
                CUBE_OFFSET.x + (CUBE_OFFSET.x * x * 2),
                1,
                CUBE_OFFSET.z + (CUBE_OFFSET.z * z * 2)
        );

        // makes a small rectangle for one tile of the 3d floor
        Model floorModel = modelBuilder.createBox(
                GRID_SIZE - 2, 1, GRID_SIZE - 2,
                new Material(ColorAttribute.createDiffuse(Color.LIGHT_GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        renderInstances.add(new ModelInstance(floorModel, tilePos));

        // adds a grid over the floor
        Model grid = modelBuilder.createLineGrid(
                1, 1, GRID_SIZE - 1, GRID_SIZE - 1,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance grid1Model = new ModelInstance(grid);
        grid1Model.transform.setTranslation(tilePos.x, tilePos.y + 2, tilePos.z);
        renderInstances.add(grid1Model);
    }

    private void makeSingleCube(float x, float y, float z, Color colorIn) {

        Vector3 cubePos = new Vector3(
                CUBE_OFFSET.x + (CUBE_OFFSET.x * x * 2),
                CUBE_OFFSET.y + (CUBE_OFFSET.y * y * 2),
                CUBE_OFFSET.z + (CUBE_OFFSET.z * z * 2)
        );

        Model cube = modelBuilder.createBox(
                GRID_SIZE - 2, GRID_SIZE - 2, GRID_SIZE - 2,
                new Material(ColorAttribute.createDiffuse(colorIn)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance cubeModel = new ModelInstance(cube, cubePos);
        renderInstances.add(cubeModel);
        modelInstances.add(cubeModel);

        Model grid = modelBuilder.createLineGrid(
                1, 1, GRID_SIZE - 1, GRID_SIZE - 1,
                new Material(ColorAttribute.createAmbient(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        ModelInstance grid1Model = new ModelInstance(grid);
        grid1Model.transform.setToRotation(Vector3.X, 90);
        grid1Model.transform.setTranslation(cubePos.x, cubePos.y, GRID_SIZE + (CUBE_OFFSET.z * z * 2));
        renderInstances.add(grid1Model);

        ModelInstance grid2Model = new ModelInstance(grid);
        grid2Model.transform.setToRotation(Vector3.X, 270);
        grid2Model.transform.setTranslation(cubePos.x, cubePos.y, (CUBE_OFFSET.z * z * 2));
        renderInstances.add(grid2Model);

        ModelInstance grid3Model = new ModelInstance(grid);
        grid3Model.transform.setToRotation(Vector3.Z, 90);
        grid3Model.transform.setTranslation(GRID_SIZE + (CUBE_OFFSET.x * x * 2), cubePos.y, cubePos.z);
        renderInstances.add(grid3Model);

        ModelInstance grid4Model = new ModelInstance(grid);
        grid4Model.transform.setToRotation(Vector3.Z, 270);
        grid4Model.transform.setTranslation((CUBE_OFFSET.x * x * 2), cubePos.y, cubePos.z);
        renderInstances.add(grid4Model);
    }

    private void makeCubeStack(float x, float z, Color colorIn) {
        Vector3 cubePos = new Vector3(
                CUBE_OFFSET.x + (CUBE_OFFSET.x * x * 2),
                CUBE_OFFSET.y * 2 + (CUBE_OFFSET.y),
                CUBE_OFFSET.z + (CUBE_OFFSET.z * z * 2)
        );
        Model cube = modelBuilder.createBox(
                GRID_SIZE - 2, GRID_SIZE * 3 - 2, GRID_SIZE - 2,
                new Material(ColorAttribute.createDiffuse(colorIn)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance cubeModel = new ModelInstance(cube, cubePos);
        renderInstances.add(cubeModel);
        modelInstances.add(cubeModel);
        makeStackGrid(x, z, cubePos);
    }

    private void makeStackGrid(float x, float z, Vector3 cubePos) {
        Model grid = modelBuilder.createLineGrid(
                1, 3, GRID_SIZE - 1, GRID_SIZE - 1,
                new Material(ColorAttribute.createAmbient(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        ModelInstance grid1Model = new ModelInstance(grid);
        grid1Model.transform.setToRotation(Vector3.X, 90);
        grid1Model.transform.setTranslation(cubePos.x, cubePos.y, GRID_SIZE + (CUBE_OFFSET.z * z * 2));
        renderInstances.add(grid1Model);

        ModelInstance grid2Model = new ModelInstance(grid);
        grid2Model.transform.setToRotation(Vector3.X, 270);
        grid2Model.transform.setTranslation(cubePos.x, cubePos.y, (CUBE_OFFSET.z * z * 2));
        renderInstances.add(grid2Model);

        grid = modelBuilder.createLineGrid(
                3, 1, GRID_SIZE - 1, GRID_SIZE - 1,
                new Material(ColorAttribute.createAmbient(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        ModelInstance grid3Model = new ModelInstance(grid);
        grid3Model.transform.setToRotation(Vector3.Z, 90);
        grid3Model.transform.setTranslation(GRID_SIZE + (CUBE_OFFSET.x * x * 2), cubePos.y, cubePos.z);
        renderInstances.add(grid3Model);

        ModelInstance grid4Model = new ModelInstance(grid);
        grid4Model.transform.setToRotation(Vector3.Z, 270);
        grid4Model.transform.setTranslation((CUBE_OFFSET.x * x * 2), cubePos.y, cubePos.z);
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

    private void makeFinalCube(float x, float y, float z, Color colorIn) {

        Vector3 cubePos = new Vector3(
                CUBE_OFFSET.x + (CUBE_OFFSET.x * x * 2),
                CUBE_OFFSET.y + (CUBE_OFFSET.y * y * 2),
                CUBE_OFFSET.z + (CUBE_OFFSET.z * z * 2)
        );

        Model cube = modelBuilder.createBox(
                GRID_SIZE - 2, GRID_SIZE - 2, GRID_SIZE - 2,
                new Material(ColorAttribute.createDiffuse(colorIn)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        finalCube = new ModelInstance(cube, cubePos);
        renderInstances.add(finalCube);

        Model grid = modelBuilder.createLineGrid(
                1, 1, GRID_SIZE - 1, GRID_SIZE - 1,
                new Material(ColorAttribute.createAmbient(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        ModelInstance grid1Model = new ModelInstance(grid);
        grid1Model.transform.setToRotation(Vector3.X, 90);
        grid1Model.transform.setTranslation(cubePos.x, cubePos.y, GRID_SIZE + (CUBE_OFFSET.z * z * 2));
        renderInstances.add(grid1Model);

        ModelInstance grid2Model = new ModelInstance(grid);
        grid2Model.transform.setToRotation(Vector3.X, 270);
        grid2Model.transform.setTranslation(cubePos.x, cubePos.y, (CUBE_OFFSET.z * z * 2));
        renderInstances.add(grid2Model);

        ModelInstance grid3Model = new ModelInstance(grid);
        grid3Model.transform.setToRotation(Vector3.Z, 90);
        grid3Model.transform.setTranslation(GRID_SIZE + (CUBE_OFFSET.x * x * 2), cubePos.y, cubePos.z);
        renderInstances.add(grid3Model);

        ModelInstance grid4Model = new ModelInstance(grid);
        grid4Model.transform.setToRotation(Vector3.Z, 270);
        grid4Model.transform.setTranslation((CUBE_OFFSET.x * x * 2), cubePos.y, cubePos.z);
        renderInstances.add(grid4Model);
    }

}
