package edu.uhcl.team_drone.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import edu.uhcl.team_drone.drone.Drone;

public class PIDGraph {

    private static final int GRAPH_WIDTH = 400;
    private static final int GRAPH_HEIGHT = 100;
    private static final int HALF_HEIGHT = GRAPH_HEIGHT / 2;

    private ShapeRenderer sr;

    public int positionX, positionY;

    private Drone drone;

    int counter = 0;

    GraphData rollData;
    GraphData pData;
    GraphData iData;

    public PIDGraph(SpriteBatch sbIn, Drone droneIn) {
        this.drone = droneIn;

        sr = new ShapeRenderer();
        sr.setProjectionMatrix(sbIn.getProjectionMatrix());
        sr.setAutoShapeType(true);
        rollData = new GraphData(GRAPH_WIDTH);
        rollData.setColor(Color.WHITE);
        pData = new GraphData(GRAPH_WIDTH);
        pData.setColor(Color.GREEN);
        iData = new GraphData(GRAPH_WIDTH);
        iData.setColor(Color.RED);
        
    }

    public void render() {
        sr.begin();

        sr.setColor(Color.WHITE);
        // draw outline graph box
        sr.rect(positionX, positionY, GRAPH_WIDTH, GRAPH_HEIGHT);
        
        // drawing current roll       

        rollData.setData(
                counter,
                positionX + counter,
                positionY + HALF_HEIGHT + (drone.gyroCmpnt.getCurrentRoll() * GRAPH_HEIGHT));
        pData.setData(
                counter,
                positionX + counter,
                positionY + HALF_HEIGHT
                + ((float) drone.stabilityCmpnt.getRollPID().getProportional() * 10));
        iData.setData(
                counter,
                positionX + counter,
                positionY + HALF_HEIGHT
                + ((float) drone.stabilityCmpnt.getRollPID().getIntegral() * GRAPH_HEIGHT));

        counter++;
        if (counter >= GRAPH_WIDTH) {
            counter = 0;
            rollData.resetArray();
            pData.resetArray();
            iData.resetArray();
        }

        for (int i = 0; i < GRAPH_WIDTH; i++) {
            rollData.draw(sr, i);
            pData.draw(sr, i);
            iData.draw(sr,i);
        }
        

        // line in the middle of graph      
        drawDottedLine(sr,
                6,
                positionX,
                positionY + GRAPH_HEIGHT / 2,
                positionX + GRAPH_WIDTH,
                positionY + GRAPH_HEIGHT / 2);
        sr.end();
    }

    public void setPosition(int xIn, int yIn) {
        this.positionX = xIn;
        this.positionY = yIn;
    }

    private void drawDottedLine(ShapeRenderer shapeRenderer, int dotDist, float x1, float y1, float x2, float y2) {
        // credit to donfu**
        // http://stackoverflow.com/questions/21892972/easy-way-of-drawing-a-straight-dotted-line-in-libgdx
        sr.setColor(Color.WHITE);
        //shapeRenderer.begin(ShapeType.Point);

        Vector2 vec2 = new Vector2(x2, y2).sub(new Vector2(x1, y1));
        float length = vec2.len();
        for (int i = 0; i < length; i += dotDist) {
            vec2.clamp(length - i, length - i);
            shapeRenderer.point(x1 + vec2.x, y1 + vec2.y, 0);
        }

        //shapeRenderer.end();

    }
    
    public void dispose(){
        sr.dispose();
    }
}
