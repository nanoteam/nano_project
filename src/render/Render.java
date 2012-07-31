/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package render;

import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import logic.Level;
import logic.entity.GameObject;
import main.Engine;

public class Render implements Engine {
	private Level level;

	public Level getLevel(){
		return level;
	}
	public void setLevel(Level level){
		this.level = level; 
	}
	
	@Override
	public void tick() {
		//clear buffer and add image to buffer, display been do good
		glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glClear(GL_COLOR_BUFFER_BIT);
		
		List<GameObject> game_objects = level.getGameObjects();
		for (GameObject game_object : game_objects) {
			game_object.draw();
		}
	}
	//TODO add avto detecting optimal working set display, 1600x900x32 or 640x480x16 or ...
	public Render() {		
		try {
			Display.setFullscreen(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		// Enable vsync if we can
		Display.setVSyncEnabled(true);

		try {
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		

		// Put the window into orthographic projection mode with 1:1 pixel
		// ratio.
		// We haven't used GLU here to do this to avoid an unnecessary
		// dependency.
		
		//without this part of code render not working kerrektly!
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, Display.getDisplayMode().getWidth(), 0.0, Display
				.getDisplayMode().getHeight(), -1.0, 1.0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(0, 0, Display.getDisplayMode().getWidth(), Display
				.getDisplayMode().getHeight());

	}

	@Override
	public void cleanUp() {
		// Close the window
		Display.destroy();
	}

	public void update() {
		Display.update();
	}

	// TODO move this method in other classs
	public void syncFps(int frame_rate) {
		Display.sync(frame_rate);
	}
}