package org.therapist.bot.commands;

import org.therapist.bot.utilities.Emotion;

public class EmotionCommand {
    // Get emotion translation based on language
    public String getEmotionTranslation(String emotionName, String language) {
        Emotion emotion = Emotion.valueOf(emotionName);
        return emotion.getTranslation(language);
    }
}
