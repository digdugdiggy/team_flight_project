package edu.uhcl.team_drone.world;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import edu.uhcl.team_drone.screens.PlayScreen;

public class MyContactListener extends ContactListener {

    @Override
    public void onContactStarted(
            btCollisionObject obj1,
            btCollisionObject obj2) {
        System.out.println("COLLIDE");
        System.out.println(obj1.getWorldTransform().getTranslation(Vector3.Zero));
        System.out.println();
        System.out.println(obj2.getWorldTransform().getTranslation(Vector3.Zero));
        PlayScreen.getDrone().moveToPosition(0, 1000, 0);
    }
}
