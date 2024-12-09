package org.therapist.bot.fileutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileUtils {

    public static Map<String, List<String>> loadResponses(String filePath) {
        Map<String, List<String>> responses = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentEmotion = null;
            List<String> currentResponses = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.endsWith(":")) { // Emotion header
                    if (currentEmotion != null && !currentResponses.isEmpty()) {
                        responses.put(currentEmotion.toUpperCase(), new ArrayList<>(currentResponses));
                    }
                    currentEmotion = line.substring(0, line.length() - 1).toUpperCase();
                    currentResponses.clear();
                } else if (line.startsWith("-")) { // Response line
                    if (currentEmotion != null) {
                        currentResponses.add(line.substring(1).trim());
                    }
                }
            }

            // Add the last emotion and its responses
            if (currentEmotion != null && !currentResponses.isEmpty()) {
                responses.put(currentEmotion.toUpperCase(), new ArrayList<>(currentResponses));
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading file: " + filePath);
        }
        return responses;
    }
}
