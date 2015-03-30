package edu.uhcl.team_drone.screens;

import edu.uhcl.team_drone.screens.main_menu.MainMenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.main.Main;
import static edu.uhcl.team_drone.main.Main.cam;

public class DebugScreen implements Screen {

    Main game;

    Viewport view;

    Stage stage;

    public DebugScreen(Main gameIn) {
        this.game = gameIn;
        view = new FitViewport(800, 600);
        view.setCamera(cam);
        view.apply();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(800, 600));
        Gdx.input.setInputProcessor(stage);

        TextButton simButton = new TextButton("Sim", Assets.blueTextBtnStyle);
        TextButton normalButton = new TextButton("Normal Run", Assets.blueTextBtnStyle);
        TextButton menuButton = new TextButton("Main Menu", Assets.blueTextBtnStyle);

        simButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game));
            }
        });
        normalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SplashScreen(game));
            }
        });
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().padBottom(20);
        table.add(simButton).size(300, 100).align(Align.center);
        table.row();
        table.add(normalButton).size(300, 100).align(Align.center);
        table.row();
        table.add(menuButton).size(300, 100).align(Align.center);

        stage.addActor(table);
    }

    @Override
    public void render(float f) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();

    }

}
