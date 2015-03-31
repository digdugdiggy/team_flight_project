package edu.uhcl.team_drone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.uhcl.team_drone.debug.DebugRender;
import edu.uhcl.team_drone.drone.Drone;
import edu.uhcl.team_drone.main.Main;
import static edu.uhcl.team_drone.main.Main.cam;
import static edu.uhcl.team_drone.main.Main.modelBatch;
import edu.uhcl.team_drone.ui.PlayUI;
import edu.uhcl.team_drone.world.CollisionWorld;
import edu.uhcl.team_drone.world.WorldManager;

public class PlayScreen implements Screen {

    private Drone drone;
    public static WorldManager worldManager;

    //
    public static CollisionWorld collisionWorld;
    //
    private Viewport view;

    public static boolean isPaused = false;
    public static DebugRender debug;
    private Main game;
    private PlayUI ui;
    private PauseMenu pause;

    public PlayScreen(Main gameIn) {
        this.game = gameIn;
        view = new FitViewport(800, 600);
        view.setCamera(cam);
        view.apply();
    }

    @Override
    public void show() {
        Bullet.init();
        drone = new Drone(true);

        worldManager = new WorldManager();
        collisionWorld = new CollisionWorld(drone);

        //drone.collisionCmpnt.registerWithWorld(collisionWorld.btWorld);
        ui = new PlayUI(drone, view);
        debug = new DebugRender(drone);

        Gdx.input.setInputProcessor(drone.input.getInputProcessor());
        updateCameraFromDrone();
        pause = new PauseMenu(game, this);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
//        Gdx.gl.glEnable(GL20.GL_POLYGON_OFFSET_FILL);
        //Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        //Gdx.gl.glDepthRangef(0.5f, 40000);
        Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);
        Gdx.gl.glPolygonOffset(1.0f, 1.0f);


        renderRunning(delta);

    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
        ui.resize(width, height);
        pause.resize(width, height);
    }

    private void renderRunning(float delta) {
        if (!isPaused) {
            drone.update(delta);
            
            collisionWorld.render(cam);
        }

        worldManager.render(cam, delta, drone);
        ui.render(delta);
        debug.update();

        pause.render(isPaused);

        updateCameraFromDrone();
    }

    @Override
    public void dispose() {
        // throw away heavy objects
        modelBatch.dispose();
        ui.dispose();
        debug.dispose();
    }

    private void updateCameraFromDrone() {
        cam.position.set(drone.getPosition());
        cam.direction.set(drone.getDirection());
        cam.up.set(drone.getUp());
        cam.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    public CollisionWorld getWorld() {
        return collisionWorld;
    }

    public Drone getDrone() {
        return drone;
    }

}
