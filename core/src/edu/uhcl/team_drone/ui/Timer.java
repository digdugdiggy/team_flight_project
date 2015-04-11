package edu.uhcl.team_drone.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.uhcl.team_drone.assets.Assets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Timer {
    
    private float timeCount;
    private boolean isActive;
    
    private Label timerLabel;  
    
    private DecimalFormat formatter;

    public Timer(Table tableIn) {       
        this.timeCount = 00.00f;
        this.isActive = false;

        timerLabel = new Label("", Assets.labelStyle);
            

        tableIn.add(timerLabel).size(140, 90);
        tableIn.center().top().left();
        
        // format to replace '.' with ':' to convert from float to TIME
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setDecimalSeparator(':');
        formatter = new DecimalFormat("#00.##", formatSymbols);
        
        timerLabel.setText(formatter.format(timeCount));  

    }

    public void update(float dt) {
        if (isActive) {
            timeCount += dt;
            timerLabel.setText(formatter.format(timeCount));
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
