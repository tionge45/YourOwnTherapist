package org.therapist.bot.commands;

import lombok.Getter;

import java.util.Locale;

@Getter
public class LanguageCommand {
    private String selectedLanguage = "EN"; // Default language

    // Toggle language between English and Russian
    public void setLanguage(String language) {
        if (language.equalsIgnoreCase("EN") || language.equalsIgnoreCase("RU")) {
            selectedLanguage = language.toUpperCase(Locale.ROOT);
        }
    }

    // Get the current language
    public String getLanguage() {
        return selectedLanguage;
    }
}
