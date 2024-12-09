package org.therapist.bot.main;

import org.springframework.scheduling.support.SimpleTriggerContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.therapist.bot.logic.BotLogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TherapistBot extends TelegramLongPollingBot {
    private final BotLogic botLogic = new BotLogic();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
    }

    private void handleTextMessage(Update update) {
        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        String username = update.getMessage().getFrom().getUserName();
        String language = botLogic.getLanguageCommand().getLanguage();

        try {
            SendMessage response;
            switch (messageText) {
                case "/start", "language" -> response = botLogic.handleStartCommand(chatId, username);
                //case "/language" -> response = botLogic.handleLanguageSelection(language, chatId, username);
                case "/emotion", "/affirmation", "activities"-> {
                    response = botLogic.handleLanguageSelection(language, chatId, update.getMessage().
                            getFrom().getUserName());
                }
                default -> {
                    response = new SendMessage();
                    response.setChatId(chatId);
                    response.setText("Sorry, I didn't understand that. Please use /start, /language, or /emotion.");
                }
            }
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleCallbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String username = update.getCallbackQuery().getFrom().getUserName();

        try {
            if (callbackData.startsWith("LANGUAGE_")) {
                String language = callbackData.equals("LANGUAGE_EN") ? "EN" : "RU";
                SendMessage response = botLogic.handleLanguageSelection(language, chatId, username);
                execute(response);

            } else if (callbackData.startsWith("EMOTION_")) {
                String emotion = callbackData.substring("EMOTION_".length());
                String currentLanguage = botLogic.getLanguageCommand().getLanguage();
                String responseText = botLogic.generateEmotionResponse(emotion, currentLanguage);

                SendMessage response = new SendMessage();
                response.setChatId(chatId);
                response.setText(responseText);
                execute(response);
            } else {
                SendMessage response = new SendMessage();
                response.setChatId(chatId);
                response.setText("Sorry, something went wrong. Please try again.");
                execute(response);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "MyTherapistBot"; // Replace with your bot username
    }

    public String readBotToken() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Tionge\\Documents\\JAVA\\token.txt"))) {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotToken() {
        return readBotToken();
    }
}
