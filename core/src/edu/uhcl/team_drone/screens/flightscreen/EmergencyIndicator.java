/* *****************************************************************************
* Programmer: Michael Truncale
* *Code is modified from original code by Charles Fahselt
* Course: CSCI 4388.01 - Senior Project
* Date: April 20, 2015
* Assignment: Senior Project
* Environment: Windows 7 - 64 bit
* IDE: Compiled and tested under NetBeans 8.0.2 / JDK 1.8
/******************************************************************************/

package edu.uhcl.team_drone.screens.flightscreen;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.input.hardware.HardwareInterface;



public class EmergencyIndicator{
    private HardwareInterface droneData;
    private Label emergencyLabel;  

    
    public EmergencyIndicator(Table tableIn, HardwareInterface input) {  
        droneData = input;
        emergencyLabel = new Label("", Assets.labelStyle);

        tableIn.top().center().padTop(490);
        tableIn.add(emergencyLabel).size(280, 90);
        
        emergencyLabel.setText("Reset Drone");
    }
    
    
    public void update(float dt) {

    }
}

