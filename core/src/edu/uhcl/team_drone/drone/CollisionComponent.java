/* * * * * * * * * * * * * * * * * *
* PROGRAMMER: CHARLES FAHSELT
*
* COURSE: CINF 4388 SENIOR PROJECT 2015
*
* PURPOSE: This class handles all collision about the drone(player)
*
 * * * * * * * * * * * * * * * * * */

package edu.uhcl.team_drone.drone;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;

public class CollisionComponent {
    
    private final static float DRONE_COLLISION_SIZE = 400;

    btCollisionShape collisionShape;
    btCollisionObject collisionObject;

    private Matrix4 transform;

    private Drone owner;

    public CollisionComponent(Drone ownerIn) {
        this.owner = ownerIn;
        this.collisionShape = new btSphereShape(DRONE_COLLISION_SIZE);
        
        collisionObject = new btCollisionObject();
        collisionObject.setCollisionShape(collisionShape);
        transform = new Matrix4(
                owner.getPosition(),
                new Quaternion(),
                new Vector3(0,0,0));
        collisionObject.setWorldTransform(transform);
    }
  

    public void update() {
        updateTransform();
        collisionObject.setWorldTransform(transform);
    }

    private void updateTransform() {
        transform.set(owner.getPosition(), new Quaternion());
    }

    public btCollisionObject getCollisionObject() {
        return collisionObject;
    }

}
