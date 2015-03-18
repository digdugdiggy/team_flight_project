package edu.uhcl.team_drone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import edu.uhcl.team_drone.assets.Assets;
import edu.uhcl.team_drone.main.Main;

public class MainMenuScreen implements Screen {

    // This class is for displaying the main menu. It has multiple buttons to
    // launch other game modes,and is the central navigation point of the 
    // program.
//    private static final String[] menuTooltips = {
//
//    "Practice Drone flight in the software simulator",
//       "Connect to and fly a real AR drone",
//       "Set Game Options",
//       "Exit"
//   }
    private Stage stage;    // Scene2d stage
    private Main game;      // Reference to the base game class for setScreen()

    

    public MainMenuScreen(Main gameIn) {
        this.game = gameIn;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(800, 600));
        Gdx.input.setInputProcessor(stage);

        // make menu          
        // Buttons        
        TextButton playButton = new TextButton("Play", Assets.blueTextBtnStyle);
        TextButton flyButton = new TextButton("Fly", Assets.blueTextBtnStyle);
        TextButton quitButton = new TextButton("Quit", Assets.blueTextBtnStyle);
        TextButton optionsButton = new TextButton("Options", Assets.blueTextBtnStyle);

        // Label for the bottom info bar
        Label bottomInfoBar = new Label("text"], Assets.bottomBarStyle);
        bottomInfoBar.setAlignment(Align.center);
        
        // Label for game title at top of menu
        Label label = new Label("Drone Control", Assets.labelStyle);
        label.setAlignment(Align.center);
        label.setFontScale(2f);

        // 
        // Setup table layout
        //
        Table rootTable = new Table();
        rootTable.setBackground(Assets.backgroundPatch);
        rootTable.setFillParent(true);
        rootTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        rootTable.add(label).align(Align.center).padBottom(100).padTop(40).size(640, 120);
        rootTable.row();

        Table childTable = new Table();
        childTable.add(playButton)
                .size(170, 120)
                .align(Align.bottomLeft)
                .spaceRight(100);

        childTable.add(flyButton)
                .size(170, 120)
                .align(Align.bottomLeft)
                .spaceRight(100);
        childTable.row();

        childTable.add(optionsButton)
                .size(180, 65).colspan(2)
                .align(Align.center)
                .space(10);

        rootTable.add(childTable);
        rootTable.row();
        rootTable.add(quitButton).size(180, 65).align(Align.center).space(10);
        rootTable.row();
        rootTable.add(bottomInfoBar).padTop(22).fill().width(802).expand();
        // add the root table to the stage
        stage.addActor(rootTable);

        //
        // Setup actions
        //
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game));
            }
        });

        flyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new FlyScreen(game));
            }
        });
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionsScreen(game));
            }
        });

        // actions   
        // adding delay fadein action to all buttons        
        label.addAction(makeDelayedFadeIn(0f));
        playButton.addAction(makeDelayedFadeIn(0.25f));
        flyButton.addAction(makeDelayedFadeIn(0.25f));
        optionsButton.addAction(makeDelayedFadeIn(0.5f));
        quitButton.addAction(makeDelayedFadeIn(0.75f));
    }

    private SequenceAction makeDelayedFadeIn(float delay) {
        // this function returns a Sequence of actions
        // 1. Make button invisible, 2. delay parameter amount, 3. fadeIn 0.25s
        SequenceAction sequence = new SequenceAction(
                Actions.fadeOut(0),
                Actions.delay(delay),
                Actions.fadeIn(0.25f)
        );
        return sequence;
    }

    @Override
    public void render(float delta) {
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
