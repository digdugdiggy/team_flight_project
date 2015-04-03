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
        this.timeCount = 0;
        this.isActive = false;

        timerLabel = new Label(Float.toString(timeCount), Assets.labelStyle);
        //timerLabel.setAlignment(Align.right);        

        tableIn.add(timerLabel).size(140, 90);
        tableIn.center().top().left();
        
        // format to replace '.' with ':' to convert from float to TIME
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setDecimalSeparator(':');
        formatter = new DecimalFormat("#00.##", formatSymbols);

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
        isActive = false;        
        timeCount = 0;
    }

    public String getTime() {
        return formatter.format(timeCount);
    }
}
