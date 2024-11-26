package org.therapist.bot.commands;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.therapist.bot.utilities.Emotion;

import java.util.ArrayList;
import java.util.List;

public class EmotionCommand {
    // Create emotion buttons for a keyboard
    public InlineKeyboardMarkup createEmotionKeyboard(String language) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        // Create a row for each emotion
        for (Emotion emotion : Emotion.values()) {
            InlineKeyboardButton button =
                    new InlineKeyboardButton(emotion.getTranslation(language));
            button.setCallbackData(emotion.name());

            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(button);
            keyboard.add(row);
        }

        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    // Generate response based on selected emotion
    public String generateEmotionResponse(String emotionName, String language) {
        // Example: Customize response based on emotion
        Emotion emotion = Emotion.valueOf(emotionName);
        return "You selected " + emotion.getTranslation(language) + " in " + (language.equals("RU") ? "Russian" : "English");
    }
}
