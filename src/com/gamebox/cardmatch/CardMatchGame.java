package com.gamebox.cardmatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardMatchGame {
    private final List<String> cards;

    public CardMatchGame() {
        cards = new ArrayList<>();
        initializeCards();
        Collections.shuffle(cards);
    }

    private void initializeCards() {
        String[] emojiPairs = {
            "🍎", "🍌", "🍇", "🍓",
            "🐶", "🐱", "🐷", "🐸"
        };

        for (String emoji : emojiPairs) {
            cards.add(emoji);
            cards.add(emoji); // Add pair
        }
    }

    public List<String> getShuffledCards() {
        return new ArrayList<>(cards); // Return a copy
    }
}
