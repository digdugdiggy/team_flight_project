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

public class Assets {
    
    // This class serves as a general asset directory. All assets used are loaded here.

    public static AssetManager manager = new AssetManager();
    
    private static TextureAtlas atlas;

    public static BitmapFont bigFont;
    public static BitmapFont smallFont;

    public static NinePatchDrawable backgroundPatch;
    public static NinePatchDrawable greyButtonPatchUp;
    public static NinePatchDrawable greyButtonPatchDown;
    
    public static TextButtonStyle textBtnStyle;
    public static TextButtonStyle smallTextBtnStyle;
    public static TextButtonStyle blueTextBtnStyle;
    public static LabelStyle labelStyle;

    public static void init() {
        atlas = new TextureAtlas(Gdx.files.internal("2d/menu/ui.pack"));
        
        loadMenuAssets();
        load3dModels();
        makeFonts();
        manager.finishLoading();
        makeUI();
    }

    private static void loadMenuAssets() {       
        manager.load("2d/logo/Logo-Circled-small.png", Texture.class);

        manager.load("blue_panel.png", Texture.class);
        manager.load("blue_button13.png", Texture.class);
        manager.load("yellow_button01.png", Texture.class);
        
        manager.load("2d/hud/Attitude-Background.png", Texture.class);
        manager.load("2d/hud/Attitude-Inner-Lines.png", Texture.class);
        manager.load("2d/hud/Attitude-Inner-Small.png", Texture.class);
        manager.load("2d/hud/Attitude-Outer-Small.png", Texture.class);
        
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
        params.size = 24;
        smallFont = gen.generateFont(params);
    }

    private static void makeUI() {
        
        backgroundPatch = new NinePatchDrawable(
                new NinePatch(
                        manager.get("2d/menu/clouds.jpg", Texture.class)));
        
        greyButtonPatchUp = new NinePatchDrawable(atlas.createPatch("grey_button_up"));
        greyButtonPatchDown = new NinePatchDrawable(atlas.createPatch("grey_button_down"));
     

        textBtnStyle = new TextButtonStyle(
                greyButtonPatchUp, greyButtonPatchDown, greyButtonPatchUp, bigFont);
        textBtnStyle.downFontColor = Color.DARK_GRAY;
        textBtnStyle.fontColor = Color.BLACK;
        
        smallTextBtnStyle = new TextButtonStyle(
                greyButtonPatchUp, greyButtonPatchDown, greyButtonPatchUp, smallFont);
        textBtnStyle.downFontColor = Color.DARK_GRAY;
        textBtnStyle.fontColor = Color.BLACK;
        
        NinePatchDrawable bluepatch = new NinePatchDrawable(atlas.createPatch("blue_button"));
        NinePatchDrawable bluepatchDown = new NinePatchDrawable(atlas.createPatch("blue_button_down"));
        blueTextBtnStyle = new TextButtonStyle(
                bluepatch, bluepatchDown, bluepatch, bigFont);
        textBtnStyle.downFontColor = Color.DARK_GRAY;
        textBtnStyle.fontColor = Color.BLACK;

        labelStyle = new LabelStyle(bigFont, Color.BLACK);
        labelStyle.background = new NinePatchDrawable(
                new NinePatch(atlas.createPatch("blue_button")));
                       // manager.get("yellow_button01.png", Texture.class)));
        labelStyle.background.setMinWidth(100);
        labelStyle.fontColor = Color.BLACK;
    }
    
    public static void dispose(){
        atlas.dispose();
        manager.dispose();
        bigFont.dispose();
        smallFont.dispose();
    }

}
