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



public class FlightModeIndicator{
    private HardwareInputComponent droneData;
    private Label modeLabel;  
    private boolean inPrecisionMode = false;
    

    public FlightModeIndicator(Table tableIn, HardwareInputComponent input) {  
        droneData = input;
        modeLabel = new Label("", Assets.labelStyle);

        tableIn.top().left().padTop(360);
        tableIn.add(modeLabel).size(420, 90);
        
        modeLabel.setText("Precision Mode: Off");  
    }
    
    
    public void update(float dt) {
        inPrecisionMode = droneData.getMode();
        
        if (inPrecisionMode == true){
            modeLabel.setText("Precision Mode: On");  
        }
        else{
            modeLabel.setText("Precision Mode: Off");  
        }
    }
}

