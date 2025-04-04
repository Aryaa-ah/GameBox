package com.gamebox.models;

public class Flashcard {
    private final String emoji;
    private final String answer;
    private final String category;

    public Flashcard(String emoji, String answer, String category) {
        this.emoji = emoji;
        this.answer = answer;
        this.category = category;
    }

    public String getEmoji() { return emoji; }
    public String getAnswer() { return answer; }
    public String getCategory() { return category; }
}
