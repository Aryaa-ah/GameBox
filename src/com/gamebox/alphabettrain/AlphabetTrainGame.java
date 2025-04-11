// === AlphabetTrainGame.java ===
package com.gamebox.alphabettrain;

import java.util.*;

public class AlphabetTrainGame {
    private final List<Character> sequence;
    private int score;
    private char correctNextLetter;

    public AlphabetTrainGame() {
        this.sequence = generateSequence();
        this.score = 0;
        this.correctNextLetter = calculateNextLetter();
    }

    private List<Character> generateSequence() {
        List<Character> allLetters = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            allLetters.add(c);
        }
        Collections.shuffle(allLetters);
        return allLetters.subList(0, 5);
    }

    private char calculateNextLetter() {
        char lastChar = sequence.get(sequence.size() - 1);
        return lastChar == 'Z' ? 'A' : (char) (lastChar + 1);
    }

    public boolean checkAnswer(String input) {
        if (input.length() != 1) return false;
        char answer = Character.toUpperCase(input.charAt(0));
        boolean correct = answer == correctNextLetter;
        if (correct) score++;
        return correct;
    }

    public void next() {
        sequence.clear();
        sequence.addAll(generateSequence());
        correctNextLetter = calculateNextLetter();
    }

    public int getScore() {
        return score;
    }

    public List<Character> getCurrentSequence() {
        return sequence;
    }

    public char getCorrectNextLetter() {
        return correctNextLetter;
    }
}