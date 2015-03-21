package edu.uhcl.team_drone.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import edu.uhcl.team_drone.screens.main_menu.MenuSoundPlayer;

public class Assets {
    
    // This class serves as a general asset directory. All assets used are loaded here.

    public static MenuSoundPlayer menuSoundPlayer;
    
    public static AssetManager manager = new AssetManager();
    
    private static TextureAtlas atlas;

    public static BitmapFont bigFont;
    public static BitmapFont smallFont;
    public static BitmapFont robotoFont;

    public static NinePatchDrawable backgroundPatch;
    public static NinePatchDrawable greyButtonPatchUp;
    public static NinePatchDrawable greyButtonPatchDown;
    
    public static TextButtonStyle textBtnStyle;
    public static TextButtonStyle smallTextBtnStyle;
    public static TextButtonStyle blueTextBtnStyle;
    public static LabelStyle labelStyle;
    public static LabelStyle bottomBarStyle;

    public static void init() {
        atlas = new TextureAtlas(Gdx.files.internal("2d/menu/ui.pack"));
        
        loadMenuAssets();
        load3dModels();
        makeFonts();
        manager.finishLoading();
        makeUI();
        
        menuSoundPlayer = new MenuSoundPlayer();
    }

    private static void loadMenuAssets() {       
        manager.load("2d/logo/Logo-Circled-small.png", Texture.class);

        manager.load("blue_panel.png", Texture.class);
        manager.load("blue_button13.png", Texture.class);       
        
        // attitude indicator
        manager.load("2d/hud/Attitude-Background.png", Texture.class);
        manager.load("2d/hud/Attitude-Inner-Lines.png", Texture.class);
        manager.load("2d/hud/Attitude-Inner-Small.png", Texture.class);
        manager.load("2d/hud/Attitude-Outer-Small.png", Texture.class);
        
        // compass 
        manager.load("2d/hud/CompassInner.png", Texture.class);
        manager.load("2d/hud/CompassOuter.png", Texture.class);
        
        // HUD frame
        manager.load("2d/hud/uiFrames.png", Texture.class);
        
        
        manager.load("2d/menu/clouds.jpg", Texture.class);
    }

    private static void load3dModels() {
        
        manager.load("3d/skybox/SKYBOX.g3db", Model.class);        
        manager.load("3d/levels/newMaze.g3db", Model.class);
        manager.load("3d/levels/BestMaze.g3db", Model.class);
        
    }

    private static void makeFonts() {       
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font1.ttf"));
        FreeTypeFontParameter params = new FreeTypeFontParameter();
        params.size = 60;
        bigFont = gen.generateFont(params);        
        params.size = 40;
        smallFont = gen.generateFont(params);
        
        gen = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        params = new FreeTypeFontParameter();
        params.size = 28;
        robotoFont = gen.generateFont(params);
    }

    private static void makeUI() {
        
        backgroundPatch = new NinePatchDrawable(
                new NinePatch(
                        manager.get("2d/menu/clouds.jpg", Texture.class)));
        
        greyButtonPatchUp = new NinePatchDrawable(atlas.createPatch("grey_button_up"));
        greyButtonPatchDown = new NinePatchDrawable(atlas.createPatch("grey_button_down"));     
        
        // grey button style
        textBtnStyle = new TextButtonStyle(
                greyButtonPatchUp, greyButtonPatchDown, greyButtonPatchUp, bigFont);
        textBtnStyle.downFontColor = Color.DARK_GRAY;
        textBtnStyle.fontColor = Color.BLACK;
        
        // alternate small font button style
        smallTextBtnStyle = new TextButtonStyle(
                greyButtonPatchUp, greyButtonPatchDown, greyButtonPatchUp, robotoFont);
        textBtnStyle.downFontColor = Color.DARK_GRAY;
        textBtnStyle.fontColor = Color.BLACK;
        
        // blue button style
        NinePatchDrawable bluepatch = new NinePatchDrawable(atlas.createPatch("blue_button"));
        NinePatchDrawable bluepatchDown = new NinePatchDrawable(atlas.createPatch("blue_button_down"));
        blueTextBtnStyle = new TextButtonStyle(
                bluepatch, bluepatchDown, bluepatch, robotoFont);
        textBtnStyle.downFontColor = Color.DARK_GRAY;
        textBtnStyle.fontColor = Color.BLACK;

        // style for the bottom info bar
        bottomBarStyle = new LabelStyle(robotoFont, Color.BLACK);
        bottomBarStyle.background = new NinePatchDrawable(atlas.createPatch("bottombar"));
        bottomBarStyle.fontColor = Color.BLACK;

        // style for Title label at top of menu's
        labelStyle = new LabelStyle(bigFont, Color.BLACK);
        labelStyle.background = new NinePatchDrawable(
                new NinePatch(atlas.createPatch("blue_button")));
        labelStyle.background.setMinWidth(100);
        labelStyle.fontColor = Color.BLACK;
    }

    public static void dispose() {
        atlas.dispose();
        manager.dispose();
        bigFont.dispose();
        smallFont.dispose();
        menuSoundPlayer.dispose();
    }

}
