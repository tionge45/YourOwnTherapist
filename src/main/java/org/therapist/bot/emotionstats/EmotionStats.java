package org.therapist.bot.emotionstats;

import org.therapist.bot.emotions_database.DatabaseUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class EmotionStats {
    private static final Logger logger = Logger.getLogger(EmotionStats.class.getName());

    // Recording user emotions
    public void recordEmotionForUser(Long chatId, String emotion, String language) {
        String query = "INSERT INTO emotions (chat_id, emotion, date, language) VALUES (?, ?, CURDATE(), ?)";
        if (emotion == null || emotion.trim().isEmpty()) {
            logger.warning("Emotion is null or empty for chatId: " + chatId);
        } else {

            try (Connection conn = DatabaseUtility.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setLong(1, chatId);
                ps.setString(2, emotion);
                ps.setString(3, language);
                ps.executeUpdate();
            } catch (SQLException e) {
                logger.severe("Error recording emotion: " + e.getMessage());
            }
        }
    }

    // Getting user emotions
    public String getUserEmotionStatsForToday(Long chatId, String language) {
        String query = "SELECT emotion, COUNT(*) as count FROM emotions WHERE chat_id = ? AND date = CURDATE() GROUP BY emotion";
        StringBuilder stats = new StringBuilder();
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, chatId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String emotion = rs.getString("emotion");
                    int count = rs.getInt("count");
                    stats.append(String.format("%s - %d %s\n",
                            emotion,
                            count,
                            language.equalsIgnoreCase("RU") ? "раз" : "times"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats.length() > 0 ? stats.toString() : language.equalsIgnoreCase("RU")
                ? "Эмоции за сегодня не найдены."
                : "No emotions recorded for today.";
    }

    // Getting all user emotion stats
    public String getGeneralEmotionStats(Long chatId, String language) {
        String query = "SELECT date, emotion, COUNT(*) as count FROM emotions WHERE chat_id = ? GROUP BY date, emotion ORDER BY date";
        StringBuilder stats = new StringBuilder();
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, chatId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String date = rs.getString("date");
                    String emotion = rs.getString("emotion");
                    int count = rs.getInt("count");
                    stats.append(String.format("%s: %s - %d %s\n",
                            date,
                            emotion,
                            count,
                            language.equalsIgnoreCase("RU") ? "раз" : "times"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats.length() > 0 ? stats.toString() : language.equalsIgnoreCase("RU")
                ? "Вы еще не добавили никаких эмоций."
                : "You haven't logged any emotions yet.";
    }

    // Deleting saved emotions
    public String deleteAllEmotionsForUser(Long chatId, String language) {
        String query = "DELETE FROM emotions WHERE chat_id = ?";
        try (Connection conn = DatabaseUtility.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, chatId);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0
                    ? (language.equalsIgnoreCase("RU")
                    ? "Все эмоции успешно удалены."
                    : "All emotions have been successfully deleted.")
                    : (language.equalsIgnoreCase("RU")
                    ? "У вас еще нет сохраненных эмоций для удаления."
                    : "You don't have any saved emotions to delete.");
        } catch (SQLException e) {
            e.printStackTrace();
            return language.equalsIgnoreCase("RU")
                    ? "Произошла ошибка при удалении эмоций."
                    : "An error occurred while deleting emotions.";
        }
    }
}
