package com.gamebox.memory;

import com.gamebox.models.Flashcard;
import com.gamebox.utils.FlashcardStack;

import java.util.*;

public class MemoryGame {
    private final List<Flashcard> flashcards = new ArrayList<>();
    private final FlashcardStack<Flashcard> shownStack = new FlashcardStack<>();
    private int currentIndex = 0;
    private int score = 0;

    public MemoryGame() {
        loadFlashcards();
        Collections.shuffle(flashcards);
    }

    private void loadFlashcards() {
        flashcards.add(new Flashcard("🍎", "Apple", "Fruit"));
        flashcards.add(new Flashcard("🐶", "Dog", "Animal"));
        flashcards.add(new Flashcard("🚗", "Car", "Vehicle"));
        flashcards.add(new Flashcard("🎵", "Music", "Sound"));
        flashcards.add(new Flashcard("🍌", "Banana", "Fruit"));
        flashcards.add(new Flashcard("🐱", "Cat", "Animal"));
        flashcards.add(new Flashcard("🚲", "Bicycle", "Vehicle"));
        flashcards.add(new Flashcard("🎸", "Guitar", "Sound"));
    }

    public Flashcard getNextCard() {
        if (currentIndex < flashcards.size()) {
            Flashcard card = flashcards.get(currentIndex++);
            shownStack.push(card);
            return card;
        }
        return null;
    }

    public Flashcard peekLastShown() {
        return shownStack.isEmpty() ? null : shownStack.peek();
    }

    public List<String> getShuffledChoices(String correctAnswer) {
        Set<String> choices = new HashSet<>();
        choices.add(correctAnswer);
        Random rand = new Random();
        while (choices.size() < 4) {
            Flashcard card = flashcards.get(rand.nextInt(flashcards.size()));
            choices.add(card.getAnswer());
        }
        List<String> shuffled = new ArrayList<>(choices);
        Collections.shuffle(shuffled);
        return shuffled;
    }

    public void increaseScore() { score++; }

    public int getScore() { return score; }

    public int getTotalQuestions() { return currentIndex; }

    public boolean hasMoreCards() {
        return currentIndex < flashcards.size();
    }
}
