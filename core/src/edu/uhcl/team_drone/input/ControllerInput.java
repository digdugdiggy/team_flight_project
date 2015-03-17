package edu.uhcl.team_drone.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import edu.uhcl.team_drone.drone.Drone;

public class ControllerInput {
    
    
    Drone owner;
    
    private Controller controller;  
    
    private boolean hasController = true;
    
    public ControllerInput(Drone ownerIn){
        this.owner = ownerIn;
        if(Controllers.getControllers().size == 0){
            hasController = false;
        }else{
            controller = Controllers.getControllers().first();
        }
        
    }

    public void update() {
       

        if (hasController) {

            // rotation
            if (controller.getAxis(XBox360Pad.AXIS_LEFT_X) > 0.3f
                    || controller.getAxis(XBox360Pad.AXIS_LEFT_X) < -0.3f) {
                owner.yaw(-2 * controller.getAxis(XBox360Pad.AXIS_LEFT_X));
            }

            // height
            if (controller.getAxis(XBox360Pad.AXIS_LEFT_Y) > 0.3f
                    || controller.getAxis(XBox360Pad.AXIS_LEFT_Y) < -0.3f) {
                owner.altitude(-2 * controller.getAxis(XBox360Pad.AXIS_LEFT_Y));
            }

            // tilt
            if (controller.getAxis(XBox360Pad.AXIS_RIGHT_X) > 0.1f
                    || controller.getAxis(XBox360Pad.AXIS_RIGHT_X) < -0.1f) {
                owner.setControlRollAmt(XBox360Pad.AXIS_RIGHT_X);
                //rollAmt = controller.getAxis(XBox360Pad.AXIS_RIGHT_X);
            } else {
                owner.setControlRollAmt(0);
            }
            if (controller.getAxis(XBox360Pad.AXIS_RIGHT_Y) > 0.1f
                    || controller.getAxis(XBox360Pad.AXIS_RIGHT_Y) < -0.1f) {
                owner.setControlPitchAmt(XBox360Pad.AXIS_RIGHT_Y);
                //pitchAmt = controller.getAxis(XBox360Pad.AXIS_RIGHT_Y);
            } else {
                owner.setControlPitchAmt(0);
            }
        }
    }

}
