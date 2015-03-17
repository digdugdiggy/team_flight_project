package edu.uhcl.team_drone.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class GraphData {

    private Vector2 dataPoints[];
    private int graphWidth;

    private int counter;
    private Color color;

    public GraphData(int graphWidthIn) {
        this.graphWidth = graphWidthIn;
        dataPoints = new Vector2[graphWidth];
        this.counter = 0;
        this.color = Color.WHITE;
    }

    public void setData(int index, float xIn, float yIn) {
        dataPoints[index] = new Vector2(xIn, yIn);       
    }

    public Vector2 getData() {
        return dataPoints[counter];
    }

    public void resetArray() {
        for (int i = 0; i < graphWidth; i++) {
            dataPoints[i] = null;
        }
    }

    public void draw(ShapeRenderer sr, int index) {
        
        sr.setColor(color);
        if (dataPoints[index] != null) {
            sr.point(
                    dataPoints[index].x,
                    dataPoints[index].y,
                    0);
        }
        
    }
    
    public void setColor(Color colorIn){
        this.color = colorIn;
    }

}
