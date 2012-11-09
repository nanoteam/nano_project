package resourses;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;


import java.io.*;
import java.util.Scanner;


public class ParserText {
	private final File fFile;
	private HashMap<String,String> pairString = new HashMap<String, String>();
    public static    void main(String... aArgs) throws FileNotFoundException {
    	ParserText parser = new ParserText("D:\\setting.txt");
        parser.startParser();
    }

    public ParserText(String aFileName) {
        fFile = new File(aFileName);
    }

    public Map<String, String> startParser() throws FileNotFoundException {
        //Note that FileReader is used, not File, since File is not Closeable
        Scanner scanner = new Scanner(new FileReader(fFile));
        try {
            //first use a Scanner to get each line
            while (scanner.hasNextLine()) {
                parsingLine(scanner.nextLine());
            }
        } finally {
            //ensure the underlying stream is always closed
            //this only has any effect if the item passed to the Scanner
            //constructor implements Closeable (which it does in this case).
            scanner.close();
        }
        return pairString;
    }
    
    public Map<String,String> getPairString(){
        return pairString;

    }

    private void parsingLine(String aLine) {
        //use a second Scanner to parse the content of each line
        aLine = aLine.trim();
        Scanner scanner = new Scanner(aLine);
        scanner.useDelimiter("=");
        if (scanner.hasNext()) {
            String name = scanner.next();
            String value = scanner.next();
            pairString.put(name.trim(),value.trim());
        } else {
            System.out.println("ParserText.parsingLine - Empty or invalid line. Unable to process.");
        }
    }
}
