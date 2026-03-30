package com.ov.tetris.util;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class GameFileHandler {

    public static void writeGameScoreFile(String text) {
        try {
            FileWriter scoreWriter = new FileWriter("score.txt");
            scoreWriter.write(text);
            scoreWriter.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static String readGameScoreFile() {
        try {
            File scoreFile = new File("score.txt");
            Scanner reader = new Scanner(scoreFile);
            String scoreString = "";
            
            if (!scoreFile.exists()) {
                // text.setText("File is not found!");
                reader.close();
                return "";
            }

            while (reader.hasNext()) {
                String line = reader.nextLine();
                scoreString += line;
            }
            reader.close();
            System.out.println(scoreString);
            return scoreString;
            


        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }
    }
    
}
