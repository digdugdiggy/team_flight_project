/* * * * * * * * * * * * * * * * * *
* PROGRAMMER: CHARLES FAHSELT
*
* COURSE: CINF 4388 SENIOR PROJECT 2015
*
* PURPOSE: This class is a globally accessible place to store assets.
*          It loads all assets at program launch.
 * * * * * * * * * * * * * * * * * */

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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import edu.uhcl.team_drone.screens.mainmenu.MenuSoundPlayer;

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
    
    public static SliderStyle sliderStyle;
    public static TextButtonStyle textBtnStyle;
    public static TextButtonStyle smallTextBtnStyle;
    public static TextButtonStyle blueTextBtnStyle;
    public static LabelStyle labelStyle;
    public static LabelStyle bottomBarStyle;

    public static void init() {
        atlas = new TextureAtlas(Gdx.files.internal("2d/menu/ui.pack"));
        
        loadMenuAssets();        
        makeFonts();
        manager.finishLoading();
        makeUI();
        
        menuSoundPlayer = new MenuSoundPlayer();
    }

    private static void loadMenuAssets() {       
        manager.load("2d/logo/Logo-Circled-small.png", Texture.class);   
        
        // attitude indicator        
        manager.load("2d/hud/attitudeIndicator/background.png", Texture.class);
        manager.load("2d/hud/attitudeIndicator/inner-lines.png", Texture.class);
        manager.load("2d/hud/attitudeIndicator/outer.png", Texture.class);
        
        // compass 
        manager.load("2d/hud/compassIndicator/CompassInner.png", Texture.class);
        manager.load("2d/hud/compassIndicator/CompassOuter.png", Texture.class);        
                
        // clouds image       
        manager.load("2d/menu/clouds.jpg", Texture.class);     
        
        // Controls image
        manager.load("2d/menu/controls.png", Texture.class);
        
    }

    private static void makeFonts() {       
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font1.ttf"));
        FreeTypeFontParameter params = new FreeTypeFontParameter();
        params.size = 60;
        bigFont = gen.generateFont(params);    
        bigFont.setFixedWidthGlyphs("0123456789");
        params.size = 40;
        smallFont = gen.generateFont(params);
        smallFont.setFixedWidthGlyphs("0123456789");        
        
        gen = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        params = new FreeTypeFontParameter();
        params.size = 28;        
        robotoFont = gen.generateFont(params);
        robotoFont.setFixedWidthGlyphs("0123456789");
    }

    private static void makeUI() {
        
        backgroundPatch = new NinePatchDrawable(
                new NinePatch(
                        manager.get("2d/menu/clouds.jpg", Texture.class)));
                          
        // blue button style
        NinePatchDrawable bluepatch = new NinePatchDrawable(atlas.createPatch("buttonup"));
        NinePatchDrawable bluepatchDown = new NinePatchDrawable(atlas.createPatch("buttondown"));
        blueTextBtnStyle = new TextButtonStyle(
                bluepatch, bluepatchDown, bluepatch, robotoFont);        

        // style for the bottom info bar
        bottomBarStyle = new LabelStyle(robotoFont, Color.BLACK);
        bottomBarStyle.background = new NinePatchDrawable(atlas.createPatch("bottombar"));
        bottomBarStyle.fontColor = Color.BLACK;

        // style for Title label at top of menu's
        labelStyle = new LabelStyle(bigFont, Color.BLACK);
        labelStyle.background = new NinePatchDrawable(
                new NinePatch(atlas.createPatch("buttonup")));
        labelStyle.background.setMinWidth(100);
        labelStyle.fontColor = Color.BLACK;

        sliderStyle = new SliderStyle();
        sliderStyle.background = new NinePatchDrawable(
                new NinePatch(atlas.createPatch("bar")));
        sliderStyle.knob = new NinePatchDrawable(
                new NinePatch(atlas.createPatch("knob")));
    }

    public static void dispose() {
        atlas.dispose();
        manager.dispose();
        bigFont.dispose();
        smallFont.dispose();
        menuSoundPlayer.dispose();
    }

}
