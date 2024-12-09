package org.therapist.bot.fileutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileReaderUtil {

    private static final Random RANDOM = new Random();

    // Read responses from the file and return a random one for the given emotion
    public static String readResponseFromFiles(String emotion, String language) {
        String fileName = language.equalsIgnoreCase("RU") ? "emotion_responses_russian.txt" : "emotion_responses_english.txt";
        List<String> responses = new ArrayList<>();
        boolean emotionFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\JAVA\\MyOwnTherapist\\" + fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equalsIgnoreCase(emotion + ":")) {
                    emotionFound = true;
                } else if (emotionFound && line.startsWith("-")) {
                    responses.add(line.substring(1).trim());
                } else if (emotionFound && line.isEmpty()) {
                    break; // End of emotion block
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!responses.isEmpty()) {
            int randomIndex = RANDOM.nextInt(responses.size());
            return responses.get(randomIndex);
        } else {
            return "Sorry, I don't have a response for that emotion."; // Default if not found
        }
    }
}
