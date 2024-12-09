package org.therapist.bot.utilities;

public enum Emotion {
    HAPPY("Happy 😊", "Счастлив 😊"),
    EXCITED("Excited 🤩", "Взволнован 🤩"),
    CALM("Calm 😌", "Спокойный 😌"),
    CONTENT("Content 😃", "Довольный 😃"),
    ANXIOUS("Anxious 😰", "Тревожный 😰"),
    STRESSED("Stressed 😩", "Стресс 😩"),
    SAD("Sad 😢", "Грустный 😢"),
    ANGRY("Angry 😡", "Злой 😡"),
    FRUSTRATED("Frustrated 😤", "Раздраженный 😤"),
    HOPELESS("Hopeless 😞", "Безнадежный 😞");

    private final String english;
    private final String russian;

    Emotion(String english, String russian) {
        this.english = english;
        this.russian = russian;
    }

    public String getEnglish() {
        return english;
    }

    public String getRussian() {
        return russian;
    }

    public String getTranslation(String language) {
        return language.equalsIgnoreCase("RU") ? russian : english;
    }
}
