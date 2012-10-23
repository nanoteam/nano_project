package util;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class DeleteFirstText {
    public static void main(String args[]) {
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        File fileInput, fileOutput;

        try {
            fileInput = new File("D:/class.java");
            fileOutput = new File("D:/classOutput.java");
            bufferedReader = new BufferedReader(new FileReader(fileInput));
            bufferedWriter = new BufferedWriter(new FileWriter(fileOutput));
            HashSet<String> box = new HashSet<String>();
            box.add(new String("0"));
            box.add(new String("1"));
            box.add(new String("2"));
            box.add(new String("3"));
            box.add(new String("4"));
            box.add(new String("5"));
            box.add(new String("6"));
            box.add(new String("7"));
            box.add(new String("8"));
            box.add(new String("9"));
            

            boolean onRun = true;
            while (onRun) {

                if (!bufferedReader.ready()) {
                    onRun = false;
                    continue;
                }
                String line = bufferedReader.readLine();
                boolean onRunLocal = true;
                int currentLenght = 0;

                while(onRunLocal){
                    if (box.contains(String.valueOf(line.charAt(currentLenght)))){
                        currentLenght++;
                    }
                    else{
                        onRunLocal = false;
                        continue;
                    }
                }
                bufferedWriter.write(line.substring(currentLenght,line.length())+"\n");
            }

            bufferedWriter.close();
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
