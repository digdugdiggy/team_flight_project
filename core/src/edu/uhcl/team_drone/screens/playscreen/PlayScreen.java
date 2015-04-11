/*
 // This class is responsible for the main gameplay portion of the simulator.
 // 
 // It handles all objects related to the playing of the software maze game.
 */
package edu.uhcl.team_drone.screens.playscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.Disposable;
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

public class PlayScreen implements Screen, Disposable {

    public enum GAME_STATES {

        START, PLAYING, PAUSED, ENDED;
    };

    private static GAME_STATES currentState;

    private final Main game;

    private static Drone drone;

    private final Viewport view;
    
    private ControlsDisplay controlDisplay;
    private PauseDisplay pauseDisplay;
    private EndDisplay endDisplay;

    public static WorldManager worldManager;
    public static CollisionWorld collisionWorld;
    public static DebugRender debug;

    private static PlayUI ui;

    public PlayScreen(Main gameIn) {
        this.game = gameIn;
        view = new FitViewport(Main.RESOLUTION.x, Main.RESOLUTION.y);
        view.setCamera(cam);
        view.apply();
        currentState = GAME_STATES.START;

        // initialize Bullet 3D Physics
        Bullet.init();
        
        // Create drone(player) object
        drone = new Drone(true);

        //create the world and its collisionbits
        worldManager = new WorldManager();
        collisionWorld = new CollisionWorld(drone);

        // set up the UI, debug renderer and pause menu
        ui = new PlayUI(drone, view);
        PlayUI.timeIndicator.stop();        
        debug = new DebugRender(drone);
        pauseDisplay = new PauseDisplay(game);
        endDisplay = new EndDisplay(game);
        controlDisplay = new ControlsDisplay();
    }

    @Override
    public void show() {
        // Set input to accept player controls
        Gdx.input.setInputProcessor(drone.input.getInputProcessor());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);
        Gdx.gl.glPolygonOffset(2.0f, 2.0f);

        // always render the world, ui, debugrenderer and pausescreen
        worldManager.render(cam, delta, drone);
        ui.render(delta);

        switch (currentState) {
            case START:
                controlDisplay.render();
                break;
            case PLAYING:
                drone.update(delta);
                collisionWorld.update(cam);
                break;
            case PAUSED:
                pauseDisplay.render();
                break;
            case ENDED:
                endDisplay.render();
                break;
        }

        debug.update();
        updateCameraFromDrone();
    }

    private void updateCameraFromDrone() {
        cam.position.set(drone.getPosition());
        cam.direction.set(drone.getDirection());
        cam.up.set(drone.getUp());
        cam.update();
    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
        ui.resize(width, height);
        pauseDisplay.resize(width, height);
        endDisplay.resize(width, height);
        controlDisplay.resize(width, height);
    }

    @Override
    public void dispose() {
        // throw away heavy objects
        modelBatch.dispose();
        ui.dispose();
        debug.dispose();
        worldManager.dispose();
        collisionWorld.dispose();
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

    public static Drone getDrone() {
        return drone;
    }

    public static void setState(GAME_STATES stateIn) {
        entry(stateIn);
        currentState = stateIn;
        System.out.println("State changing to : " +stateIn.toString());
    }

    private static void entry(GAME_STATES stateIn) {
        switch (stateIn) {
            case START:    
                drone.moveToPosition(1000, 1000, 1000);
                PlayUI.timeIndicator.reset();
                break;
            case PLAYING:                
                PlayUI.timeIndicator.start();
                break;
            case PAUSED:
                PlayUI.timeIndicator.stop();
                break;
            case ENDED:
                PlayUI.timeIndicator.stop();
                break;
        }
    }
}
