package edu.uhcl.team_drone.debug;

import com.badlogic.gdx.Gdx;
import edu.uhcl.team_drone.assets.Assets;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.uhcl.team_drone.drone.Drone;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DebugRender {

    private Drone owner;
    private SpriteBatch sb;    
    private PIDGraph graph;
    public boolean isVisible = false;

    public DebugRender(Drone ownerIn) {
        this.owner = ownerIn;
        sb = new SpriteBatch();        
        graph = new PIDGraph(sb, owner);
        graph.setPosition(230, 370);
    }

    public void update() {
        if (isVisible) {
            sb.begin();
            String temp;
            temp = "FPS: " + Gdx.graphics.getFramesPerSecond();
            writeString(temp, 10, 400);
            sb.end();

            graph.render();
        }

    }

    private void writeString(String string, float x, float y) {
        Assets.smallFont.draw(sb, string, x, y);
    }

    public void dispose() {
        sb.dispose();
        graph.dispose();
    }

}
