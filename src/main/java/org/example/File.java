package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class File {
    private static final String FILE_PATH = "game_result.txt";

    public static void writeGameResult(String winner, int roundsPlayed) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("Winner: " + winner + "\n");
            writer.write("Rounds played: " + roundsPlayed + "\n");
        } catch (IOException e) {
            System.out.println("Error writing game result to file: " + e.getMessage());
        }
    }
}
