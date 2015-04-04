package edu.uhcl.team_drone.world;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import edu.uhcl.team_drone.screens.playscreen.PlayScreen;

public class MyContactListener extends ContactListener {

    @Override
    public void onContactStarted(
            btCollisionObject obj1,
            btCollisionObject obj2) {
        System.out.println("COLLIDE");

        if (obj1.getUserIndex() == 100 || obj2.getUserIndex() == 100) {
            System.out.println("END ");
            PlayScreen.setState(PlayScreen.GAME_STATES.ENDED);            
        } else {
            PlayScreen.getDrone().moveToPosition(1000, 1000, 1000);
        }
    }
}
