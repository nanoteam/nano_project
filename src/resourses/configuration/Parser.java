package resourses.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

//static class like procedure
class Parser {
    private Parser() {

    }

    public static SheetParse startParser(String pathToFile) {
        StringTokenizer tokenizer;
        // init all for reading file line by line
        BufferedReader bufferedReader;
        File file;
        try {
            file = new File(pathToFile);
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        boolean onRun = true;
        String line = null;
        int stateParserDepth = 0;
        SheetParse currentSheetParse = null;
        ArrayDeque<SheetParse> stackSheets = new ArrayDeque<SheetParse>();
        //help add last line to parserSheet
        boolean lastLine = false;
        while (onRun) {
            if (lastLine) {
                onRun = false;
                System.out.println("Parser: reading file" +
                        file.getAbsolutePath().toString() + " complite");
                continue;
            }
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // if this line last
            try {
                if (!bufferedReader.ready()) {
                    lastLine = true;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            // if epmty
            if (line == null) {
                continue;
            }
            // if epmty, not bad code!
            if (0 == line.trim().length()) {
                continue;
            }

            // comment "//"
            if (line.trim().substring(0, 2).equals("//")) {
                continue;
            }

            // TODO add cheking line on spec symbols; (Set<String>)

            // error. there are one or zero sign "="
            if (line.indexOf("=") != line.lastIndexOf("=")) {

                continue;
            }

            // clever condition start here!
            int depthLine = getNumberFirstTabs(line);
            // if there is header.
            if (!line.contains("=")) {
                // ------>
                if (depthLine > stateParserDepth) {
                    new Exception(
                            "Parser:startParser - error structure of document"
                                    + file.getAbsolutePath());
                    continue;
                }
                // <------
                // closed open session SheetParse
                // depthLine=>0!
                if (depthLine < stateParserDepth) {
                    // here can be stateParserDepth -1 - depthLine)
                    for (int i = 0; i < stateParserDepth - depthLine; i++) {
                        currentSheetParse = stackSheets.pop();
                    }
                    stateParserDepth = depthLine;

                }


                if (depthLine == stateParserDepth) {
                    tokenizer = new StringTokenizer(line.trim());
                    String name = tokenizer.nextToken();
                    if (currentSheetParse == null) {
                        currentSheetParse = new SheetParse(name, null,
                                new ArrayList<SheetParse>(), null,
                                stateParserDepth);
                        stackSheets.push(currentSheetParse);
                    } else {
                        stackSheets.push(currentSheetParse);
                        currentSheetParse = currentSheetParse
                                .addNewChildHeader(name);
                    }
                    stateParserDepth++;
                    continue;
                }
                continue;
            }

            // if there is one pair name:value
            // continue filling SheetParse
            if (line.contains("=")) {
                // <------
                // closed open session SheetParse
                // depthLine=>0!
                if (depthLine < stateParserDepth) {
                    for (int i = 0; i < stateParserDepth - depthLine; i++) {
                        currentSheetParse = stackSheets.pop();
                    }
                    stateParserDepth = depthLine;
                }
                if (depthLine == stateParserDepth) {
                    tokenizer = new StringTokenizer(line.trim());
                    String name = tokenizer.nextToken();
                    // nextToken() x2 becouse ("name" "=" "value")
                    String value = tokenizer.nextToken();
                    value = tokenizer.nextToken();

                    if (currentSheetParse == null) {
                        new Exception(
                                "Parser:startParser - error structure of document"
                                        + file.getAbsolutePath());
                    } else {
                        currentSheetParse.addNewChildPair(name, value);
                    }
                    continue;
                }
                // ------>
                if (depthLine > stateParserDepth) {
                    new Exception(
                            "Parser:startParser - error structure of document"
                                    + file.getAbsolutePath());
                    continue;
                }
            }
        }
        //currentSheetParse.getHead();  - output, but this is link on obj
        return new SheetParse(currentSheetParse.getHead());

    }

    private static int getNumberFirstTabs(String line) {
        int allDepth = line.indexOf(line.trim().charAt(0));
        int realDepth = 0;
        for (int i = 0; i < allDepth; i++) {
            if ("\t".equals(String.valueOf(line.charAt(i)))) {
                realDepth++;
            }
        }
        return realDepth;
    }
}
