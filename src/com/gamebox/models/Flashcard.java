package com.gamebox.models;


import java.util.List;

public class Flashcard {
    private final String emoji;
    private final String label;
    private final List<String> otherOptions;

    public Flashcard(String emoji, String label, List<String> otherOptions) {
        this.emoji = emoji;
        this.label = label;
        this.otherOptions = otherOptions;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getOtherOptions() {
        return otherOptions;
    }
}