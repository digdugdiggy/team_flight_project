package edu.uhcl.team_drone.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import edu.uhcl.team_drone.drone.Drone;

public class ControllerInput {
    
    private final float DEAD_ZONE = 0.3f;
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
            if (controller.getAxis(XBox360Pad.AXIS_RIGHT_X) > DEAD_ZONE
                    || controller.getAxis(XBox360Pad.AXIS_RIGHT_X) < -DEAD_ZONE) {
                owner.yaw(-2 * controller.getAxis(XBox360Pad.AXIS_RIGHT_X));
            }

            // height
            if (controller.getAxis(XBox360Pad.AXIS_RIGHT_Y) > DEAD_ZONE
                    || controller.getAxis(XBox360Pad.AXIS_RIGHT_Y) < -DEAD_ZONE) {
                owner.altitude(-2 * controller.getAxis(XBox360Pad.AXIS_RIGHT_Y));
            }

            // roll
            if (controller.getAxis(XBox360Pad.AXIS_LEFT_X) > DEAD_ZONE
                    || controller.getAxis(XBox360Pad.AXIS_LEFT_X) < -DEAD_ZONE) {

                owner.setControlRollAmt(
                        controller.getAxis(XBox360Pad.AXIS_LEFT_X) > 0
                        ? controller.getAxis(XBox360Pad.AXIS_LEFT_X) - DEAD_ZONE
                        : controller.getAxis(XBox360Pad.AXIS_LEFT_X) + DEAD_ZONE);

            } else {
                owner.setControlRollAmt(0);
            }

            // pitch
            if (controller.getAxis(XBox360Pad.AXIS_LEFT_Y) > DEAD_ZONE
                    || controller.getAxis(XBox360Pad.AXIS_LEFT_Y) < -DEAD_ZONE) {

                // if axis bigger than zero, subtract deadZone, then apply pitch
                owner.setControlPitchAmt(
                        controller.getAxis(XBox360Pad.AXIS_LEFT_Y) > 0
                        ? controller.getAxis(XBox360Pad.AXIS_LEFT_Y) - DEAD_ZONE
                        : controller.getAxis(XBox360Pad.AXIS_LEFT_Y) + DEAD_ZONE);

            } else {
                owner.setControlPitchAmt(0);
            }
        }
    }

}
