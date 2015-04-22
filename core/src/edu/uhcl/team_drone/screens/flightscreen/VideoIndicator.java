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



public class VideoIndicator{
    private HardwareInterface droneData;
    private Label videoLabel;  

    
    public VideoIndicator(Table tableIn, HardwareInterface input) {  
        droneData = input;
        videoLabel = new Label("", Assets.labelStyle);

        tableIn.top().right().padTop(0);
        tableIn.add(videoLabel).size(250, 90);
        
        videoLabel.setText("Load Video");
        
        //Loads video - attach to an actionListener
        //droneData.startVideo();
    }
    
    
    public void update(float dt) {
    }
}

