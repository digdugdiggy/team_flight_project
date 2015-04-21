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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.uhcl.team_drone.drone.Drone;
import edu.uhcl.team_drone.input.hardware.HardwareInputComponent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.uhcl.team_drone.main.Main;
import edu.uhcl.team_drone.screens.*;



public class HardwareUI {
    private Drone drone;
    private Stage stage;   
    public static FlightTime timeIndicator;
    public static BatteryIndicator batteryIndicator;
    public static CommandIndicator commandIndicator;
    public static HardwareAltitudeIndicator hardwareAltitudeIndicator;
    public static FlightModeIndicator flightModeIndicator;
    public static EmergencyIndicator emergencyIndicator;
    public static VideoIndicator videoIndicator;

    
    public HardwareUI(HardwareInputComponent input, Drone owner, Viewport viewIn) {       
        this.drone = owner;
        stage = new Stage(new FitViewport(Main.RESOLUTION.x, Main.RESOLUTION.y));      
        
        Table timerTable = new Table();
        timerTable.setFillParent(true);
        timeIndicator = new FlightTime(timerTable, input);
        timeIndicator.start();
        stage.addActor(timerTable);  

        Table batteryTable = new Table();
        batteryTable.setFillParent(true);
        batteryIndicator = new BatteryIndicator(batteryTable, input);
        stage.addActor(batteryTable);
        
        Table commandTable = new Table();
        commandTable.setFillParent(true);
        commandIndicator = new CommandIndicator(commandTable, input);
        stage.addActor(commandTable);
        
        Table altitudeTable = new Table();
        altitudeTable.setFillParent(true);
        hardwareAltitudeIndicator = new HardwareAltitudeIndicator(altitudeTable, input);
        stage.addActor(altitudeTable);
        
        Table modeTable = new Table();
        modeTable.setFillParent(true);
        flightModeIndicator = new FlightModeIndicator(modeTable, input);
        stage.addActor(modeTable);
        
        Table emergencyTable = new Table();
        emergencyTable.setFillParent(true);
        emergencyIndicator = new EmergencyIndicator(emergencyTable, input);
        stage.addActor(emergencyTable);
        
        Table videoTable = new Table();
        videoTable.setFillParent(true);
        videoIndicator = new VideoIndicator(videoTable, input);
        stage.addActor(videoTable);
    }


    //Draws elements to stage / screen and also handles update cycle
    public void render(float dt) {        
        timeIndicator.update(dt);
        batteryIndicator.update(dt);
        hardwareAltitudeIndicator.update(dt);
        commandIndicator.update(dt);
        flightModeIndicator.update(dt);

        stage.act();
        stage.draw();
    }

    
    public void dispose() {
        stage.dispose();
    }
    
    
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);        
    }
}
