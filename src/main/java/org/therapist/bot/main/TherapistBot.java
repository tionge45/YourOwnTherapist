package org.therapist.bot.main;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.therapist.bot.logic.BotLogic;
import org.therapist.bot.commands.EmotionCommand;
import org.therapist.bot.commands.LanguageCommand;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TherapistBot extends TelegramLongPollingBot {
    private final BotLogic botLogic = new BotLogic();

    @Override
    public void onUpdateReceived(Update update) {

            if (update.hasMessage() && update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                Long chatId = update.getMessage().getChatId();

                try {
                    switch (messageText) {
                        case "/start", "/language":
                            SendMessage response = botLogic.handleStartCommand(chatId);
                            execute(response);
                            break;

                        case "/emotion":
                            SendMessage emotionResponse = botLogic.handleEmotionCommand(
                                    botLogic.getLanguageCommand().getLanguage(), chatId);
                            execute(emotionResponse);
                            break;

                        default:

                            SendMessage unknownResponse = new SendMessage();
                            unknownResponse.setChatId(chatId);
                            unknownResponse.setChatId("Sorry, I didn't understand that. Please use /start, /language, or /emotion.");
                            execute(unknownResponse);
                            break;
                    }
                } catch(TelegramApiException e){
                    e.printStackTrace();

                    }
                }

            if (update.hasCallbackQuery()) {
                String callbackData = update.getCallbackQuery().getData();
                Long chatId = update.getCallbackQuery().getMessage().getChatId();

                try {

                    if (callbackData.startsWith("LANGUAGE_")) {
                        String language = callbackData.equals("LANGUAGE_EN") ? "EN" : "RU";
                        SendMessage response = botLogic.handleLanguageSelection(language, chatId);
                        execute(response);
                    } else if (callbackData.startsWith("EMOTION_")) {
                        String emotionName = callbackData.substring("EMOTION_".length());
                        String language = botLogic.getLanguageCommand().getLanguage();
                        String responseText = botLogic.generateEmotionResponse(emotionName, language);

                        SendMessage response = new SendMessage();
                        response.setChatId(chatId);
                        response.setText(responseText);
                        execute(response);
                    }
                } catch(TelegramApiException e){
                    e.printStackTrace();
                }
            }
    }


        @Override
    public String getBotUsername() {
        return "MyTherapistBot"; // Replace with your bot username
    }


    public String readBotToken() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "C:\\Users\\Tionge\\Documents\\JAVA\\token.txt"));
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
