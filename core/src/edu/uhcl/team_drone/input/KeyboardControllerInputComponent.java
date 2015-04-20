/* * * * * * * * * * * * * * * * * *
* PROGRAMMER: CHARLES FAHSELT
*
* COURSE: CINF 4388 SENIOR PROJECT 2015
*
* PURPOSE: This class handles mixing the two input types 
*          (Keyboard and controller) into one global input.
*
 * * * * * * * * * * * * * * * * * */

package edu.uhcl.team_drone.input;

import edu.uhcl.team_drone.input.controller.ControllerInput;
import com.badlogic.gdx.InputProcessor;
import edu.uhcl.team_drone.drone.Drone;

public class KeyboardControllerInputComponent {
    
    private Drone owner;
    
    private ControllerInput controller;
    private KeyboardInput keyboard;

    public KeyboardControllerInputComponent(Drone ownerIn) {
        this.owner = ownerIn;
        controller = new ControllerInput(owner);
        keyboard = new KeyboardInput(owner);
    }

    public void update(float dt) {
        // update two input devices
        keyboard.update(dt);
        controller.update();
        
        // if both the keyboard and controller pitches are not adjusted, set to zero.
        if (!keyboard.isPitchAdjusted() && !controller.isPitchAdjusted()) {
            owner.setControlPitchAmt(0);
        }
        // if both the keyboard and controller rolls are not adjusted, set to zero.
        if (!keyboard.isRollAdjusted() && !controller.isRollAdjusted()) {
            owner.setControlRollAmt(0);
        }
    }

    public InputProcessor getInputProcessor() {
        return keyboard;
    }

}
