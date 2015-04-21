/* *****************************************************************************
* Programmer: Michael Truncale
* *Code is modified from original code by Charles Fahselt
* Course: CSCI 4388.01 - Senior Project
* Date: April 20, 2015
* Assignment: Senior Project
* Environment: Windows 7 - 64 bit
* IDE: Compiled and tested under NetBeans 8.0.2 / JDK 1.8
/******************************************************************************/

package edu.uhcl.team_drone.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.input.hardware.HardwareInputComponent;



public class HardwareAltitudeIndicator{
    private HardwareInputComponent droneData;
    private Label altitudeLabel;  
    private String altitudeData = "--";
    

    public HardwareAltitudeIndicator(Table tableIn, HardwareInputComponent input) {  
        droneData = input;
        altitudeLabel = new Label("", Assets.labelStyle);

        tableIn.top().right().padTop(90);
        tableIn.add(altitudeLabel).size(350, 90);
        
        altitudeLabel.setText("Altitude: " + altitudeData + "%");  
    }
    
    
    public void update(float dt) {
        altitudeData = droneData.getAltitude();

        if (altitudeData != null){
            altitudeLabel.setText("Altitude: " + altitudeData + " m");  
        }
        else{
            altitudeLabel.setText("Altitude: -- m");  
        }
    }
}

