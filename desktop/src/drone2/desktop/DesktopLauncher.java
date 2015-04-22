package drone2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.uhcl.team_drone.main.Main;
import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                config.title = "Team Flight - Drone Control";
                
                Toolkit toolkit=Toolkit.getDefaultToolkit();
                int screenHeight = toolkit.getScreenSize().height;
                int screenWidth = toolkit.getScreenSize().width;

                //config.fullscreen = true;
                //config.width = (screenHeight / 2);
                //config.height = (screenWidth / 4);
               
                //Adjusts starting screen position (0,0 is upper left)
                //Currently centers screen
                //config.x = (screenWidth - (screenWidth / 2) - (config.width / 2));
                //config.y = (screenHeight - (screenHeight / 2) - (config.height / 2));
                
                new LwjglApplication(new Main(), config);
	}
}
