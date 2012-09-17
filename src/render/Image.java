package render;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Image {

	private Texture texture;
	public void Load(String file) throws IOException {
		texture = TextureLoader.getTexture("JPG",
				ResourceLoader.getResourceAsStream(file));

	}

	public Image() {
	}
	
	public Texture getTexture(){
		return texture;
	}
}