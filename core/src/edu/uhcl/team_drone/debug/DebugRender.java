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
    private NumberFormat frmt;
    private PIDGraph graph;
    public boolean isVisible = false;

    public DebugRender(Drone ownerIn) {
        this.owner = ownerIn;
        sb = new SpriteBatch();
        frmt = new DecimalFormat("###.##");
        graph = new PIDGraph(sb, owner);
        graph.setPosition(230, 370);
    }

    public void update() {

        if (isVisible) {
            
            
            
            
            sb.begin();

            String temp = "speedX: " + frmt.format(owner.getSpeedX());
            writeString(temp, 10, 50);
            temp = "speedZ: " + frmt.format(owner.getSpeedZ());
            writeString(temp, 120, 50);
            temp = "            dx: " + frmt.format(owner.getDx());
            writeString(temp, 10, 30);
            temp = "            dz: " + frmt.format(owner.getDz());
            writeString(temp, 120, 30);

            temp = "pitch: " + frmt.format(owner.gyro.getCurrentPitch());
            writeString(temp, 10, 130);
            temp = "roll: " + frmt.format(owner.gyro.getCurrentRoll());
            writeString(temp, 10, 110);
            temp = "yaw: " + frmt.format(owner.gyro.getCurrentYaw());
            writeString(temp, 10, 90);
            
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
