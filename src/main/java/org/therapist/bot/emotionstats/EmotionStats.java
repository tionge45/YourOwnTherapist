package org.therapist.bot.emotionstats;

import java.util.*;

public class EmotionStats {
    // In-memory map to store emotion stats for users
    private final Map<Long, Map<String, List<String>>> userEmotionStats = new HashMap<>();

    // Method to record an emotion for a user
    public void recordEmotionForUser(Long chatId, String emotion) {
        // Fetch existing stats for the user or create new ones if none exist
        Map<String, List<String>> userStats = userEmotionStats.getOrDefault(chatId, new HashMap<>());

        // Get today's list of emotions, or create an empty list if none exist
        String today = getCurrentDate();
        List<String> emotions = userStats.getOrDefault(today, new ArrayList<>());

        // Add the current emotion to today's list
        emotions.add(emotion);

        // Put the updated list of emotions back in the map
        userStats.put(today, emotions);

        // Update the user's stats map
        userEmotionStats.put(chatId, userStats);
    }

    // Method to fetch today's emotion stats for a user
    public String getUserEmotionStatsForToday(Long chatId) {
        Map<String, List<String>> userStats = userEmotionStats.get(chatId);
        if (userStats == null || userStats.isEmpty()) {
            return "No emotions recorded for today.";
        }

        String today = getCurrentDate();
        List<String> emotions = userStats.get(today);
        if (emotions == null || emotions.isEmpty()) {
            return "No emotions recorded for today.";
        }

        // Create a simple summary of emotions for the day
        StringBuilder stats = new StringBuilder("Today's emotions: ");
        for (String emotion : emotions) {
            stats.append(emotion).append(" ");
        }
        return stats.toString();
    }

    // Method to generate a summary of all emotion stats for the user
    public String getGeneralEmotionStats(Long chatId) {
        Map<String, List<String>> userStats = userEmotionStats.get(chatId);
        if (userStats == null || userStats.isEmpty()) {
            return "You haven't logged any emotions yet.";
        }

        StringBuilder stats = new StringBuilder("Your emotions:\n");
        for (Map.Entry<String, List<String>> entry : userStats.entrySet()) {
            String date = entry.getKey();
            List<String> emotions = entry.getValue();

            stats.append(String.format("%s: %d emotions\n", date, emotions.size()));
        }
        return stats.toString();
    }

    // New method: Generate a summary message with language support
    public String generateStatsMessage(Long chatId, String language) {
        Map<String, List<String>> userStats = userEmotionStats.get(chatId);
        if (userStats == null || userStats.isEmpty()) {
            return language.equalsIgnoreCase("RU")
                    ? "Вы еще не добавили никаких эмоций."
                    : "You haven't logged any emotions yet.";
        }

        StringBuilder stats = new StringBuilder();
        stats.append(language.equalsIgnoreCase("RU") ? "Ваши эмоции:\n" : "Your emotions:\n");

        for (Map.Entry<String, List<String>> entry : userStats.entrySet()) {
            String date = entry.getKey();
            List<String> emotions = entry.getValue();

            stats.append(language.equalsIgnoreCase("RU")
                    ? String.format("%s: %d эмоций\n", date, emotions.size())
                    : String.format("%s: %d emotions\n", date, emotions.size()));
        }

        return stats.toString();
    }

    // Helper method to get the current date as a string (e.g., "2024-12-12")
    private String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }
}
