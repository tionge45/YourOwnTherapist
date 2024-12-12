package org.therapist.bot.initializer;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.therapist.bot.commands.LanguageCommand;
import org.therapist.bot.commands.EmotionCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistry {
    public static List<BotCommand> getCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/start", "Start the bot"));
        commands.add(new BotCommand("/language", "Change language"));
        commands.add(new BotCommand("/emotions", "Show emotions"));
        commands.add(new BotCommand("/general_stats", "See your general emotion stats"));
        commands.add(new BotCommand("daily_stats", "See your daily emotion stats "));
        return commands;





    }
}
 