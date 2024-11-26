package org.therapist.bot.ui;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguageMarkUp {
    public InlineKeyboardMarkup getLanguageMarkUp(){
        InlineKeyboardButton englishButton = new InlineKeyboardButton("English\uD83C\uDFF4\uDB40\uDC67\uDB40\uDC62\uDB40\uDC65\uDB40\uDC6E\uDB40\uDC67\uDB40\uDC7F ");
        englishButton.setCallbackData("LANGUAGE_EN");

        InlineKeyboardButton russianButton = new InlineKeyboardButton("Русский\uD83C\uDDF7\uD83C\uDDFA");
        russianButton.setCallbackData("LANGUAGE_RU");

        List<InlineKeyboardButton> row = Arrays.asList(englishButton, russianButton);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);

        return markup;

    }
}
