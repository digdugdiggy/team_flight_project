package edu.uhcl.team_drone.drone;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;

public class CollisionComponent {
    
    btCollisionShape collisionShape;
    btCollisionObject collisionObject;
    
    private Matrix4 transform;

    private Drone owner;

    private boolean active;

    public CollisionComponent(Drone ownerIn) {
        this.owner = ownerIn;
        this.collisionShape = new btSphereShape(200f);
        this.active = false;
        collisionObject = new btCollisionObject();
        collisionObject.setCollisionShape(collisionShape);
        transform = new Matrix4(
                owner.getPosition(),
                new Quaternion(),
                new Vector3(1, 1, 1));
        collisionObject.setWorldTransform(transform);
    }

    public void registerWithWorld(btCollisionWorld worldIn) {
        worldIn.addCollisionObject(collisionObject);
        this.active = true;
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
