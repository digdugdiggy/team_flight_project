package edu.uhcl.team_drone.world;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import edu.uhcl.team_drone.drone.Drone;
import edu.uhcl.team_drone.screens.playscreen.PlayScreen;

public class CollisionWorld {
    
    public static final short WORLD_FLAG = 1<<8;
    public static final short DRONE_FLAG = 1<<9;
    public static final short ALL_FLAG = 1<<8;    

    public static boolean debugOn = false;

    public static btCollisionWorld btWorld;
    
    private btCollisionConfiguration collisionConfig;
    private btDispatcher dispatcher;
    private btBroadphaseInterface broadPhase;

    private DebugDrawer debugDrawer;

    private Drone drone;
    private MyContactListener contactListener;

    public CollisionWorld(Drone droneIn) {
        this.drone = droneIn;

        // debug renderer to see collision shapes 
        debugDrawer = new DebugDrawer();

        // Make bullet world
        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);
        broadPhase = new btDbvtBroadphase();
        btWorld = new btCollisionWorld(dispatcher, broadPhase, collisionConfig);

        // enabling debug drawing
        btWorld.setDebugDrawer(debugDrawer);
        debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_DrawWireframe);

        // make contact listener to detect collisions
        contactListener = new MyContactListener();

        // add the drone's collision object to the world
        btWorld.addCollisionObject(drone.collisionCmpnt.getCollisionObject(), DRONE_FLAG, WORLD_FLAG);

        // add the level's collision objects to the world.
        for (btCollisionObject obj : PlayScreen.worldManager.programmaticLevel.colObjs) {
            btWorld.addCollisionObject(obj, WORLD_FLAG, DRONE_FLAG);
        }
    }

    public void update(PerspectiveCamera camIn) {

        // perform collision detection and summon ContactListener        
        btWorld.performDiscreteCollisionDetection();

        if (debugOn) {
            debugDrawer.begin(camIn);
            btWorld.debugDrawWorld();
            debugDrawer.end();
        }
    }
    
    public void dispose(){        
        collisionConfig.dispose();
        dispatcher.dispose();
        broadPhase.dispose();
        btWorld.dispose();
        debugDrawer.dispose();
        contactListener.dispose();       
        
    }
}

