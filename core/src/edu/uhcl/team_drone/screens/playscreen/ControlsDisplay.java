package edu.uhcl.team_drone.screens.playscreen;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.uhcl.team_drone.main.Main;

public class ControlsDisplay {

    private final Stage stage;

    public ControlsDisplay() {
        this.stage = new Stage(new FitViewport(Main.RESOLUTION.x, Main.RESOLUTION.y));
    }

}
