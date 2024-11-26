package org.therapist.bot.logic;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.therapist.bot.commands.EmotionCommand;
import org.therapist.bot.commands.LanguageCommand;
import org.therapist.bot.messages.Translator;
import org.therapist.bot.ui.LanguageMarkUp;

@Getter
@Setter
public class BotLogic {
    private final EmotionCommand emotionCommand = new EmotionCommand();
    private final LanguageCommand languageCommand = new LanguageCommand();

    // Handle /start command
    public SendMessage handleStartCommand(Long chatId) {
        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText("Welcome to My Therapist Bot! " +
                "Choose your preferred Language: English or Русский.");

        // Create language selection buttons
        InlineKeyboardMarkup languageMarkup = new LanguageMarkUp().getLanguageMarkUp();
        response.setReplyMarkup(languageMarkup);

        return response;
    }

    // Handle language selection
    public SendMessage handleLanguageSelection(String language, Long chatId) {
        languageCommand.setLanguage(language);
        String greeting = Translator.getTranslation(language, "start");

        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText(greeting);

        // Show emotion selection after language greeting
        InlineKeyboardMarkup emotionKeyboard = emotionCommand.createEmotionKeyboard(language);
        response.setReplyMarkup(emotionKeyboard);

        return response;
    }

    // Handle /emotion command
    public SendMessage handleEmotionCommand(String language, Long chatId) {
        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText("How are you feeling today?");

        // Create emotion buttons based on the selected language
        InlineKeyboardMarkup emotionKeyboard = emotionCommand.createEmotionKeyboard(language);
        response.setReplyMarkup(emotionKeyboard);

        return response;
    }


    // Generate response after selecting emotion
    public String generateEmotionResponse(String emotionName, String language) {
        return emotionCommand.generateEmotionResponse(emotionName, language);  // Response after emotion is selected
    }
}
