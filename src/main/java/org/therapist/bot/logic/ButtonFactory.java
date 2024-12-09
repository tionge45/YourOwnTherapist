package org.therapist.bot.logic;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.therapist.bot.utilities.Emotion;

import java.util.ArrayList;
import java.util.List;

public class ButtonFactory {

    public static InlineKeyboardMarkup getLanguageButtons() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton englishButton = new InlineKeyboardButton("🇬🇧 English");
        englishButton.setCallbackData("LANGUAGE_EN");

        InlineKeyboardButton russianButton = new InlineKeyboardButton("🇷🇺 Русский");
        russianButton.setCallbackData("LANGUAGE_RU");

        List<InlineKeyboardButton> row = List.of(englishButton, russianButton);
        markup.setKeyboard(List.of(row));

        return markup;
    }

    public static InlineKeyboardMarkup getEmotionButtons(String language) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        // Using the Emotion enum to generate emotion buttons dynamically
        for (Emotion emotion : Emotion.values()) {
            InlineKeyboardButton button = new InlineKeyboardButton(emotion.getTranslation(language));
            button.setCallbackData("EMOTION_" + emotion.name());
            rows.add(List.of(button));
        }

        markup.setKeyboard(rows);
        return markup;
    }
}
