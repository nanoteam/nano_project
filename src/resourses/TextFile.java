package resourses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loading and storage setting from file.
 */

public class TextFile {
	private File file;
	private BufferedReader inBufferedReader;
	private BufferedWriter inBufferedWriter;
	private boolean reading = false;
	private String pathToActualFile;
	private ArrayList<String> textFromFile = new ArrayList<String>();
	private final static String NAME_FILE_SETTING = System
			.getProperty("user.dir")
			+ "\\"
			+ "resources"
			+ "\\"
			+ "HighScores.txt";

	public void beginReadSession(String path) {
		file = new File(path);

		if (null == inBufferedReader) {
			try {
				inBufferedReader = new BufferedReader(new FileReader(file));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			textFromFile.add(new String(inBufferedReader.readLine()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void endReadSession() {

		try {
			inBufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		file = null;
		pathToActualFile = null;
		reading = false;
	}

	public List<String> loadResult() {
		return textFromFile;
	}

	/*
	 * public String readStringFromFile(File file) { String box = ""; try { box
	 * = inBufferedReader.readLine(); } catch (Exception e) { return box; }
	 * return box; }
	 * 
	 * public void writeStringToFile() { if (null == inBufferedWriter) { try {
	 * 
	 * inBufferedWriter = new BufferedWriter(new FileWriter(file));
	 * 
	 * } catch (Exception e) {
	 * 
	 * } } String word = "";
	 * 
	 * try { inBufferedWriter.write(word); inBufferedWriter.write('\n'); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * }
	 * 
	 * }
	 */

	public boolean isReading() {
		return reading;
	}

	public String getPathToActualFile() {
		return pathToActualFile;
	}

}
