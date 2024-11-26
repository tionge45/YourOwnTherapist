package org.therapist.bot.messages;

import java.util.Map;

public class Translator {
    private static final Map<String, Map<String, String>> translations = Map.of(
            "EN", Map.of(
                    "start", "Hi! How are you doing today?",
                    "error", "I didn’t understand that. Please try again."
            ),
            "RU", Map.of(
                    "start", "Привет! Как вы себя чувствуете сегодня?",
                    "error", "Я не понял ваш ответ. Попробуйте ещё раз."
            )
    );

    public static String getTranslation(String language, String key) {
        return translations.getOrDefault(language, translations.get("EN")).getOrDefault(key, "Message not found");
    }
}
