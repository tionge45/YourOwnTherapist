package org.therapist.bot.logic;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.therapist.bot.utilities.Emotion;

import java.util.ArrayList;
import java.util.List;

public class ButtonFactory {

    // Create language selection buttons
    public static InlineKeyboardMarkup getLanguageButtons() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        // Create English language button
        InlineKeyboardButton englishButton = new InlineKeyboardButton("🇬🇧 English");
        englishButton.setCallbackData("LANGUAGE_EN");

        // Create Russian language button
        InlineKeyboardButton russianButton = new InlineKeyboardButton("🇷🇺 Русский");
        russianButton.setCallbackData("LANGUAGE_RU");

        // Add buttons to a row
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(englishButton);
        row.add(russianButton);

        // Set the keyboard layout
        markup.setKeyboard(List.of(row));
        return markup;
    }

    // Create emotion selection buttons based on the language
    public static InlineKeyboardMarkup getEmotionButtons(String language) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        // Using the Emotion enum to generate emotion buttons dynamically
        for (Emotion emotion : Emotion.values()) {
            InlineKeyboardButton button = new InlineKeyboardButton(emotion.getTranslation(language));
            button.setCallbackData("EMOTION_" + emotion.name());

            // Each emotion button in its own row
            rows.add(List.of(button));
        }

        // Set the keyboard layout
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup getMainButtons() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        // Create Emotion Stats button
        InlineKeyboardButton statsButton = new InlineKeyboardButton("📊 View Emotion Stats");
        statsButton.setCallbackData("EMOTION_STATS");

        // Add button to a row
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(statsButton);

        // Set the keyboard layout
        markup.setKeyboard(List.of(row));
        return markup;
    }

}
