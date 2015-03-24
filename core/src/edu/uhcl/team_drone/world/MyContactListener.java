package edu.uhcl.team_drone.world;

import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;

public class MyContactListener extends ContactListener {

    @Override
    public void onContactStarted(
            btCollisionObject obj1,
            btCollisionObject obj2) {
        System.out.println("COLLIDE");

    }


    

        
    
}
