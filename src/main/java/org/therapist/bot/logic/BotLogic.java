package org.therapist.bot.logic;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.therapist.bot.commands.LanguageCommand;
import org.therapist.bot.fileutils.FileReaderUtil;
import org.therapist.bot.fileutils.FileUtils;
import org.therapist.bot.utilities.Emotion;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Getter
public class BotLogic {
    private final Map<String, List<String>> englishResponses;
    private final Map<String, List<String>> russianResponses;
    private final Random random = new Random();
    private final LanguageCommand languageCommand = new LanguageCommand();

    public BotLogic() {
        englishResponses = FileUtils.loadResponses("C:\\JAVA\\MyOwnTherapist\\emotion_responses_english");
        russianResponses = FileUtils.loadResponses("C:\\JAVA\\MyOwnTherapist\\emotion_responses_russian");
    }

    // Handle /start command
    public SendMessage handleStartCommand(Long chatId, String username) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hello @" + username +
                ", Welcome to My Therapist Bot! Choose your preferred Language:");
        message.setReplyMarkup(ButtonFactory.getLanguageButtons());
        return message;
    }

    // Handle language selection
    public SendMessage handleLanguageSelection(String language, Long chatId, String username) {
        languageCommand.setLanguage(language);

        String greetingText = (language.equalsIgnoreCase("RU"))
                ? "Здравствуйте, @" + username + "! Добро пожаловать в My Therapist Bot."
                : "Hello, @" + username + "! Welcome to My Therapist Bot.";

        String promptText = (language.equalsIgnoreCase("RU"))
                ? "Отлично! Как вы себя чувствуете сейчас? Выберите из вариантов ниже:"
                : "Great! Now, how are you feeling right now? Choose from the options below:";

        // Greeting message
        SendMessage greetingMessage = new SendMessage();
        greetingMessage.setChatId(chatId);
        greetingMessage.setText(greetingText);

        // Emotion prompt message
        SendMessage emotionPromptMessage = new SendMessage();
        emotionPromptMessage.setChatId(chatId);
        emotionPromptMessage.setText(promptText);
        emotionPromptMessage.setReplyMarkup(ButtonFactory.getEmotionButtons(language));

        // Send messages in sequence
        return emotionPromptMessage; // Just return the emotion prompt message
    }

    // Handle emotion selection and provide a response
    public String generateEmotionResponse(String emotion, String language) {
        return FileReaderUtil.readResponseFromFiles(emotion, language);
    }

    public LanguageCommand getLanguageCommand() {
        return languageCommand;
    }
}
