package edu.uhcl.team_drone.screens;

import edu.uhcl.team_drone.screens.mainmenu.MainMenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.main.Main;
import edu.uhcl.team_drone.screens.mainmenu.BottomInfoBar;

public class OptionsScreen implements Screen {

    private Stage stage;

    private Main game;

    private BottomInfoBar infoBar;
    
    private Label sliderLabel;
    private Slider slider;

    public OptionsScreen(Main gameIn) {
        this.game = gameIn;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Main.RESOLUTION.x, Main.RESOLUTION.y));
        Gdx.input.setInputProcessor(stage);

        TextButton backToMenuButton = new TextButton("Back to Menu", Assets.blueTextBtnStyle);

        Label optionsTitle = new Label("OPTIONS", Assets.labelStyle);
        optionsTitle.setAlignment(Align.center);

        Table rootTable = new Table();
        rootTable.setBackground(Assets.backgroundPatch);
        rootTable.setFillParent(true);
        rootTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        rootTable.add(optionsTitle).align(Align.center).padBottom(100).size(400, 100).colspan(2);
        rootTable.row();
        
        slider = new Slider(0, 100, 10, false, Assets.sliderStyle);
        sliderLabel = new Label(Integer.toString((int)slider.getValue()), Assets.labelStyle);
        sliderLabel.setAlignment(Align.center);
        rootTable.add(sliderLabel).size(110,80).align(Align.center).padBottom(40);
        rootTable.row();
        rootTable.add(slider).size(200, 5);
        
        
        

        rootTable.row();
        rootTable.add(backToMenuButton).size(300, 80).bottom().align(Align.center).space(40).padTop(10).padBottom(180).colspan(2);
        rootTable.row();        

        infoBar = new BottomInfoBar("", Assets.bottomBarStyle);
        infoBar.addToTable(rootTable);

        stage.addActor(rootTable);

        backToMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Assets.menuSoundPlayer.playClickSound();
                game.setScreen(game.mainMenuScreen);
            }
        });
    }

    @Override
    public void render(float delta) {
        sliderLabel.setText(Integer.toString((int)slider.getValue()));
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
