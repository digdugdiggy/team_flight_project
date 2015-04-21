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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.input.hardware.HardwareInputComponent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;



public class CommandIndicator {
    private HardwareInputComponent droneData;
    private Label commandLabel;  
    public String currentCommand = "none";
  

    public CommandIndicator(Table tableIn, HardwareInputComponent input) {       
        droneData = input;
        commandLabel = new Label("", Assets.labelStyle);
        
        tableIn.top().left().padTop(360);
        tableIn.add(commandLabel).size(800, 90);
        
        commandLabel.setText("Current Command: " + currentCommand);
    }

    
    public void update(float dt) {
        currentCommand = droneData.getCurrentCommand();
        
        //Cleans up text from HardwareInputComponent for display in label
        try{
            if (currentCommand != null){
                commandLabel.setText("Current Command: " + currentCommand);
                
                if (currentCommand == "droneStop"){
                    commandLabel.setText("Current Command: none");
                }
                if (currentCommand == "rotateRight"){
                    commandLabel.setText("Current Command: Rotate Right");
                }
                if (currentCommand == "rotateLeft"){
                    commandLabel.setText("Current Command: Rotate Left");
                }
                if (currentCommand == "droneTakeOff"){
                    commandLabel.setText("Current Command: Taking Off");
                }
                if (currentCommand == "droneLand"){
                    commandLabel.setText("Current Command: Landing");
                }
                if (currentCommand == "disableEmergency"){
                    commandLabel.setText("Current Command: Drone Reset");
                }
                if (currentCommand == "droneCalibrate"){
                    commandLabel.setText("Current Command: Calibrating");
                }
                if (currentCommand == "closeConnection"){
                    commandLabel.setText("Current Command: Server Stopped");
                }
            }
            else{
                commandLabel.setText("Current Command: " + currentCommand);
            }
        }
        catch (Exception e){
        }
    }
    
}

