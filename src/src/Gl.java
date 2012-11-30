import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.OpenALException;
import org.lwjgl.opengl.*;
import org.lwjgl.openal.AL;
import static org.lwjgl.openal.AL10.*;
import org.lwjgl.util.WaveData;

public class Gl {

	public static void main(String[] args) throws LWJGLException {

		Display.setDisplayMode(new DisplayMode(640, 480));
		Display.setTitle("Open Audio Library Test");
		Display.create();
		AL.create();

		WaveData data = null;
		try {
			data = WaveData.create(new BufferedInputStream(new FileInputStream(
					"libs/sp.wav")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		int buffer = alGenBuffers();
		System.out.println(buffer);
		alBufferData(buffer, data.format, data.data, data.samplerate);
		data.dispose();
		int source = alGenSources();
		alSourcei(source, AL_BUFFER, buffer);

		while (!Display.isCloseRequested()) {

			while (Keyboard.next()) {
				if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
					alSourcePlay(source);
				}
			}
			Display.update();
			Display.sync(60);

		}

		Display.destroy();
		AL.destroy();
		alDeleteBuffers(buffer);
		System.exit(0);

	}
}
