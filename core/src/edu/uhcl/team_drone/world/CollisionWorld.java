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

    public static btCollisionWorld btWorld;

    private btCollisionObject world;
    private btCollisionShape shape;

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

        // make the world object from g3db file               
        btCollisionShape mazeShape = Bullet.obtainStaticNodeShape(Assets.manager.get(
                "3d/levels/BestMaze.g3db",
                Model.class).nodes);
        world = new btCollisionObject();
        world.setCollisionShape(mazeShape);
        //world.forceActivationState(Collision.DISABLE_DEACTIVATION);        

        // add the drone collisionCmpnt object to this world
        //drone.collisionCmpnt.getCollisionObject().forceActivationState(Collision.DISABLE_DEACTIVATION);
        
        btWorld.addCollisionObject(world);

    }

    public void render(PerspectiveCamera camIn) {

        // perform collision detection and summon ContactListener
        
        btWorld.performDiscreteCollisionDetection();

        if (debugOn) {
            debugDrawer.begin(camIn);
            btWorld.debugDrawWorld();
            debugDrawer.end();
        }
    }
}
