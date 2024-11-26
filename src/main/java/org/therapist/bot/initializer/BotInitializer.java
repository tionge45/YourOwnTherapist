package org.therapist.bot.initializer;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.therapist.bot.main.TherapistBot;

import java.util.List;

public class BotInitializer {
    public static void initializeBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // Register the bot instance
            TherapistBot therapistBot = new TherapistBot();
            botsApi.registerBot(therapistBot);

            List<BotCommand> commands = CommandRegistry.getCommands();
            therapistBot.execute(new SetMyCommands(commands, null, null));

            System.out.println("Bot successfully initialized and running!");
        } catch (Exception e) {
            System.err.println("Error initializing the bot: " + e.getMessage());
        }
    }
}
