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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;



public class FlightTime {
    private HardwareInputComponent droneData;
    private float timeCount;
    private boolean isActive;
    private Label timerLabel;  
    private DecimalFormat formatter;
    private float altitude;

    
    public FlightTime(Table tableIn, HardwareInputComponent input) {       
        droneData = input;
        altitude = 0;
        this.timeCount = 00.00f;
        this.isActive = false;

        timerLabel = new Label("", Assets.labelStyle);
            
        tableIn.add(timerLabel).size(410, 90);
        tableIn.center().top().left();
        
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setDecimalSeparator(':');
        formatter = new DecimalFormat("#00.##", formatSymbols);
        
        timerLabel.setText("Flight Time: " + formatter.format(timeCount));  
    }

    public void update(float dt) {
        flyingCheck();
        
        if (isActive) {
            timeCount += dt;
            timerLabel.setText("Flight Time: " + formatter.format(timeCount));
        }
    }

    
    //Checks to see if drone is flying to record flight time
    public void flyingCheck(){
        try{
            altitude = Float.valueOf(droneData.getAltitude());
        }
        catch (Exception e){
        }
        
        if (altitude > 0){
            isActive = true;
        }
        else if (altitude == 0){
            isActive = false;
        }
    }
    
    
    public void start() {        
        isActive = true;
    }

    public void stop() {
        isActive = false;
    }

    public void reset() {               
        timeCount = 0;
    }

    public String getTime() {
        return formatter.format(timeCount);
    }
}
