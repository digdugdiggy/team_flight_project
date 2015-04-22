package drone2.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.uhcl.team_drone.main.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                config.title = "Team Flight";
                //config.fullscreen = true;
                config.width = 800;
                config.height = 600;
                
                new LwjglApplication(new Main(), config);
                
	}
}
