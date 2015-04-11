// This class displays the controls image when PLAY mode is started.

package edu.uhcl.team_drone.screens.playscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.main.Main;

public class ControlsDisplay {

    private final Stage stage;
    
    private Table table;

    public ControlsDisplay() {
        this.stage = new Stage(new FitViewport(Main.RESOLUTION.x, Main.RESOLUTION.y));
        Gdx.input.setInputProcessor(stage);
        createDisplay();
    }

    private void createDisplay() {
        table = new Table();
        table.setFillParent(true);
        table.defaults().center().pad(10).height(80).align(Align.center);

        Image image = new Image(Assets.manager.get("2d/menu/controls.png", Texture.class));

        table.add(image).size(500,400);
        table.row();

        TextButton startButton = new TextButton("Start", Assets.blueTextBtnStyle);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PlayScreen.setState(PlayScreen.GAME_STATES.PLAYING);
                Gdx.input.setInputProcessor(PlayScreen.getDrone().input.getInputProcessor());
            }
        });

        table.add(startButton);

        stage.addActor(table);
        
    }

    public void render() {
        Gdx.input.setInputProcessor(stage);
        stage.act();
        stage.draw();
    }
    
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

}
