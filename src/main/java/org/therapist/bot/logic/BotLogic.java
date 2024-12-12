package org.therapist.bot.logic;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.therapist.bot.commands.LanguageCommand;
import org.therapist.bot.emotionstats.EmotionStats;
import org.therapist.bot.fileutils.FileReaderUtil;
import org.therapist.bot.fileutils.FileUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Getter
public class BotLogic {
    private final Map<String, List<String>> englishResponses;
    private final Map<String, List<String>> russianResponses;
    private final Random random = new Random();
    private final LanguageCommand languageCommand = new LanguageCommand();
    private final EmotionStats emotionStats = new EmotionStats();

    public BotLogic() {
        englishResponses = FileUtils.loadResponses("C:\\JAVA\\MyOwnTherapist\\emotion_responses_english.txt");
        russianResponses = FileUtils.loadResponses("C:\\JAVA\\MyOwnTherapist\\emotion_responses_russian.txt");
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

        SendMessage greetingMessage = new SendMessage();
        greetingMessage.setChatId(chatId);
        greetingMessage.setText(greetingText);

        SendMessage emotionPromptMessage = new SendMessage();
        emotionPromptMessage.setChatId(chatId);
        emotionPromptMessage.setText(greetingText);
        emotionPromptMessage.setText(promptText);
        emotionPromptMessage.setReplyMarkup(ButtonFactory.getEmotionButtons(language));

        return emotionPromptMessage;
    }

    // Handle general stats command
    public SendMessage handleGeneralStatsCommand(Long chatId) {
        String generalStats = emotionStats.getGeneralEmotionStats(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(generalStats);
        return message;
    }

    // Handle daily stats command
    public SendMessage handleDailyStatsCommand(Long chatId) {
        String dailyStats = emotionStats.getUserEmotionStatsForToday(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(dailyStats);
        return message;
    }

    public void recordEmotionForUser(Long chatId, String emotion) {
        emotionStats.recordEmotionForUser(chatId, emotion);
    }

    public String generateEmotionResponse(String emotion, String language) {
        return FileReaderUtil.readResponseFromFiles(emotion, language);
    }
}
