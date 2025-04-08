package com.gamebox.memory;

import java.util.*;

import com.gamebox.models.Flashcard;

public class MemoryGame {
    private final List<Flashcard> cards;
    private int currentIndex;
    private int score;
    private int time;

    public MemoryGame() {
        cards = new ArrayList<>();
        initializeCards();
        Collections.shuffle(cards);
        currentIndex = 0;
        score = 0;
        time = 0;
    }

    private void initializeCards() {
        cards.add(new Flashcard("🍎", "Apple", List.of("Banana", "Orange", "Grapes")));
        cards.add(new Flashcard("🐶", "Dog", List.of("Cat", "Cow", "Elephant")));
        cards.add(new Flashcard("🚗", "Car", List.of("Bus", "Train", "Bike")));
        cards.add(new Flashcard("🏠", "House", List.of("Tent", "Hut", "Palace")));
        cards.add(new Flashcard("📱", "Phone", List.of("Tablet", "Laptop", "Watch")));
    }

    public Flashcard getCurrentCard() {
        return cards.get(currentIndex);
    }

    public void nextCard() {
        if (currentIndex < cards.size() - 1) {
            currentIndex++;
        }
    }

    public void previousCard() {
        if (currentIndex > 0) {
            currentIndex--;
        }
    }

    public List<String> getShuffledOptions(Flashcard card) {
        List<String> options = new ArrayList<>(card.getOtherOptions());
        options.add(card.getLabel());
        Collections.shuffle(options);
        return options;
    }

    public boolean checkAnswer(String guess) {
        boolean correct = guess.equals(getCurrentCard().getLabel());
        if (correct) {
            score++;
        }
        return correct;
    }

    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }

    public void incrementTime() {
        time++;
    }
}
