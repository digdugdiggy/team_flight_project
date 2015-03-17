package edu.uhcl.team_drone.world;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.drone.Drone;

public class CollisionWorld {

    public static boolean debugOn = false;
    btCollisionObject world;
    btCollisionShape shape;
    
    btCollisionConfiguration collisionConfig;
    btDispatcher dispatcher;
    btBroadphaseInterface broadPhase;
    btCollisionWorld collisionWorld;
    
    DebugDrawer debugDrawer;

    private Drone drone;
    MyContactListener contactListener;

    public CollisionWorld(Drone droneIn) {
        this.drone = droneIn;        
        debugDrawer = new DebugDrawer();        
        
        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);
        broadPhase = new btDbvtBroadphase();
        collisionWorld = new btCollisionWorld(dispatcher, broadPhase, collisionConfig);
        contactListener = new MyContactListener();
        
               
        world = new btCollisionObject();
        
        btCollisionShape mazeShape = Bullet.obtainStaticNodeShape(Assets.manager.get(
                "3d/levels/BestMaze.g3db",
                Model.class).nodes);
        
        world.setCollisionShape(mazeShape); 
        world.forceActivationState(Collision.DISABLE_DEACTIVATION);
        
        // enabling debug drawing
        collisionWorld.setDebugDrawer(debugDrawer);        
        debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_DrawWireframe);

        // add the drone collision object to this world
        drone.collision.getCollisionObject().forceActivationState(Collision.DISABLE_DEACTIVATION);
        collisionWorld.addCollisionObject(drone.collision.getCollisionObject(), (short) 1, (short) 1);
        collisionWorld.addCollisionObject(world, (short) 1, (short) 1);

    }


    public void render(PerspectiveCamera camIn) {

        collisionWorld.performDiscreteCollisionDetection();

        if (debugOn) {
            debugDrawer.begin(camIn);
            collisionWorld.debugDrawWorld();
            debugDrawer.end();
        }
    }
}

