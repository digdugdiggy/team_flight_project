package edu.uhcl.team_drone.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import edu.uhcl.team_drone.drone.Drone;

public class Xbox360Input implements ControllerListener {

    Controller controller;
    
    private Drone owner;

    public float rollAmt = 0, pitchAmt = 0;

    public Xbox360Input(Drone droneIn) {
        this.owner = droneIn;
        Controllers.addListener(this);
    }

    @Override
    public boolean buttonDown(Controller cntrlr, int buttonCode) {
        if (buttonCode == XBox360Pad.BUTTON_Y) {

        }

        return false;
    }

    @Override
    public boolean buttonUp(Controller cntrlr, int i) {

        return false;
    }

    @Override
    public boolean axisMoved(Controller cntrlr, int axisCode, float value) {
       // This is your analog stick
        // Value will be from -1 to 1 depending how far left/right, up/down the stick is
        // For the Y translation, I use a negative because I like inverted analog stick
        // Like all normal people do! ;)

        // Left Stick
        if (axisCode == XBox360Pad.AXIS_LEFT_X) {
            //owner.yaw(value);
        }

        if (axisCode == XBox360Pad.AXIS_LEFT_Y) {
            owner.altitude(-cntrlr.getAxis(XBox360Pad.AXIS_LEFT_X));
        }

        // Right stick
        if (axisCode == XBox360Pad.AXIS_RIGHT_X) {
            rollAmt = value;
        }
        if (axisCode == XBox360Pad.AXIS_RIGHT_Y) {
            pitchAmt = value;
        }

        // sprite.rotate(10f * value);
        return false;
    }

    @Override
    public boolean povMoved(Controller cntrlr, int i, PovDirection pd) {

        return false;
    }

    @Override
    public boolean xSliderMoved(Controller cntrlr, int i, boolean bln) {

        return false;
    }

    @Override
    public boolean ySliderMoved(Controller cntrlr, int i, boolean bln) {

        return false;
    }

    @Override
    public void connected(Controller cntrlr) {
    }

    @Override
    public void disconnected(Controller cntrlr) {
    }

    @Override
    public boolean accelerometerMoved(Controller cntrlr, int i, Vector3 vctr) {

        return false;
    }
}
