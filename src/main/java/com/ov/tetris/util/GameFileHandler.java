package com.ov.tetris.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class GameFileHandler {

    public static void writeGameScoreFile(String text) {
        try {
            FileWriter scoreWriter = new FileWriter("score.txt");
            scoreWriter.write(text);
            scoreWriter.close();
        } catch (Exception e) {
            System.err.println("Error writing score file: " + e.getMessage());
        }
    }

    public static String readGameScoreFile() {
        try {
            File scoreFile = new File("score.txt");
            String scoreString = "";
            
            if (!scoreFile.exists()) {
                return scoreString;
            }

            Scanner reader = new Scanner(scoreFile);

            while (reader.hasNext()) {
                String line = reader.nextLine();
                scoreString += line;
            }
            reader.close();
            return scoreString;

        } catch (FileNotFoundException e) {
                System.err.println("Score file not found: " + e.getMessage());
                return "";

        } catch (Exception e) {
                System.err.println("Error reading score file: " + e.getMessage());
                return "";
        }
    }
    
}
