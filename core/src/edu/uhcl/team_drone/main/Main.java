/* * * * * * * * * * * * * * * * * *
* PROGRAMMER: CHARLES FAHSELT
*
* COURSE: CINF 4388 SENIOR PROJECT 2015
*
* PURPOSE: Main starter class of the software. It stores references to all 
*          Screen objects, the one camera, and the one 3D rendering batch.
*
 * * * * * * * * * * * * * * * * * */
package edu.uhcl.team_drone.main;

import edu.uhcl.team_drone.assets.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import edu.uhcl.team_drone.screens.DebugScreen;
import edu.uhcl.team_drone.screens.flightscreen.FlyScreen;
import edu.uhcl.team_drone.screens.OptionsScreen;
import edu.uhcl.team_drone.screens.SplashScreen;
import edu.uhcl.team_drone.screens.mainmenu.MainMenuScreen;
import edu.uhcl.team_drone.screens.playscreen.PlayScreen;

public class Main extends Game {
    
    public static Vector2 RESOLUTION = new Vector2(800,600);

    public static PerspectiveCamera cam; // 3D camera
    public static ModelBatch modelBatch; // used to render the instance    
    
    public static SplashScreen splashScreen;
    public static MainMenuScreen mainMenuScreen;
    public static OptionsScreen optionsScreen;
    public static PlayScreen playScreen;
    public static FlyScreen flyScreen;
    public static DebugScreen debugScreen;

    @Override
    public void create() {
        Assets.init();       

        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.near = 400f;
        cam.far = 27000f;
        cam.update(true);

        // Create Screens
        splashScreen = new SplashScreen(this);
        mainMenuScreen = new MainMenuScreen(this);
        optionsScreen = new OptionsScreen(this);
        playScreen = new PlayScreen(this);
        flyScreen = new FlyScreen(this);
        debugScreen = new DebugScreen(this);

        // Set starting Screen
        setScreen(splashScreen);
    }

    @Override
    public void render() {
        super.render();
    } 

}
