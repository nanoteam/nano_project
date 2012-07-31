/**
 * @author Andreyuk Artyom happydroidx@gmail.com
 * @version 1.0
 */
package logic;

import java.util.List;
import org.lwjgl.input.Keyboard;
import logic.entity.GameObject;
import main.Client;
import main.Engine;

public class Logic implements Engine {
	private Level level;
	private Client client;
	public Logic(Client client) {
		this.client = client;
	}
	public Level getLevel(){
		return level;
	}
	public void setLevel(Level level){
		this.level = level; 
	}
	@Override
	public void tick() {
		/**
		 * Do all calculations, handle input, etc.
		 */
		inputTick();
		List<GameObject> game_objects = level.getGameObjects();

		for (GameObject game_object : game_objects) {
			game_object.update();
		}

		/*
		 * while (Keyboard.next())
		 * 
		 * switch (Keyboard.getEventKey()) {
		 * 
		 * case Keyboard.KEY_ESCAPE: // finished = true; //
		 * game.setFinished(true); break; case Keyboard.KEY_W:
		 * level.activeShip.move(Ship.UP_SIDE); break; case Keyboard.KEY_S:
		 * level.activeShip.move(Ship.DOWN_SIDE); break; case Keyboard.KEY_A:
		 * level.activeShip.move(Ship.LEFT_SIDE); break; case Keyboard.KEY_D:
		 * level.activeShip.move(Ship.RIGHT_SIDE); break; case Keyboard.KEY_P:
		 * level.activeShip.rotate(1); break; case Keyboard.KEY_O:
		 * level.activeShip.rotate(2); break; default: break; }
		 * 
		 * while (Keyboard.next()) {
		 * 
		 * if (Keyboard.getEventKeyState()) {
		 * 
		 * if (Keyboard.getEventKey() == Keyboard.KEY_A) {
		 * 
		 * System.out.println("A Key Pressed");
		 * 
		 * }
		 * 
		 * if (Keyboard.getEventKey() == Keyboard.KEY_S) {
		 * System.out.println("S Key Pressed"); } if (Keyboard.getEventKey() ==
		 * Keyboard.KEY_D) { System.out.println("D Key Pressed"); } } else {
		 * 
		 * if (Keyboard.getEventKey() == Keyboard.KEY_A) {
		 * 
		 * System.out.println("A Key Released");
		 * 
		 * }
		 * 
		 * if (Keyboard.getEventKey() == Keyboard.KEY_S) {
		 * 
		 * System.out.println("S Key Released");
		 * 
		 * }
		 * 
		 * if (Keyboard.getEventKey() == Keyboard.KEY_D) {
		 * 
		 * System.out.println("D Key Released");
		 * 
		 * }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * // angle += 2.0f % 360; level.tick();
		 */

	}
	private void inputTick(){
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			client.exit();
		}
		
		
		
		
		
		
		
		
	}

	@Override
	public void cleanUp() {
	}
}