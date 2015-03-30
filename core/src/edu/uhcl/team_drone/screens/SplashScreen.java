package edu.uhcl.team_drone.screens;

import edu.uhcl.team_drone.screens.main_menu.MainMenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.main.Main;

public class SplashScreen implements Screen {

    private Stage stage = new Stage();
    private Table table = new Table();
    private Main game;

    private final Texture splashTexture;
    private final Image splashImage;

    private boolean animationDone = false;
    
    public SplashScreen(Main gameIn) {
        this.game = gameIn;
        splashTexture = Assets.manager.get("2d/logo/Logo-Circled-small.png", Texture.class);
        splashImage = new Image(splashTexture);        
        splashImage.setOrigin(stage.getWidth() / 4, stage.getHeight() / 4);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        splashImage.addAction(
                Actions.sequence(
                        Actions.alpha(0),
                        Actions.moveBy(1000, 0, 0),
                        Actions.alpha(1),
                        Actions.rotateBy(20),
                        Actions.parallel(
                                Actions.moveBy(-1000, 0, 1, Interpolation.exp10),
                                Actions.rotateBy(-20, 1.3f, Interpolation.exp10)
                        ),
                        Actions.delay(1.5f),
                        Actions.parallel(
                                Actions.moveBy(-1000, 0, 1, Interpolation.exp10In),
                                Actions.rotateBy(20, 1f, Interpolation.exp10)
                        ),
                        Actions.delay(0.5f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                animationDone = true;
                            }
                        })));

        table.setFillParent(true);
        table.add(splashImage).size(stage.getWidth() / 2, stage.getHeight() / 2);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); //sets clear color to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the batch
        stage.act(); //update all actors
        stage.draw(); //draw all actors on the Stage.getBatch()

        if (animationDone) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        //game.resize(width, height);
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
        splashTexture.dispose();
        stage.dispose();
        
    }

}
