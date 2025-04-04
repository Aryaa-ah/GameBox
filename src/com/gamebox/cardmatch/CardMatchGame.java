package com.gamebox.cardmatch;

import java.util.*;

public class CardMatchGame {
    private final List<String> cards = new ArrayList<>();
    private final List<String> shuffled;
    private final boolean[] matched;
    private int moves = 0;
    private long startTime;
    private boolean useTimer;

    public CardMatchGame(boolean useTimer) {
        cards.addAll(List.of("🐶", "🐱", "🐻", "🦊", "🐼", "🐵"));
        shuffled = new ArrayList<>();
        for (String emoji : cards) {
            shuffled.add(emoji);
            shuffled.add(emoji);
        }
        Collections.shuffle(shuffled);
        matched = new boolean[shuffled.size()];
        this.useTimer = useTimer;
        if (useTimer) startTime = System.currentTimeMillis();
    }

    public List<String> getShuffledCards() {
        return shuffled;
    }

    public boolean isMatched(int index) {
        return matched[index];
    }

    public void markMatched(int i1, int i2) {
        matched[i1] = true;
        matched[i2] = true;
    }

    public boolean allMatched() {
        for (boolean m : matched) if (!m) return false;
        return true;
    }

    public int getMoves() {
        return moves;
    }

    public void incrementMoves() {
        moves++;
    }

    public boolean isMatch(int i1, int i2) {
        return shuffled.get(i1).equals(shuffled.get(i2));
    }

    public long getElapsedTimeSeconds() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    public boolean isTimerEnabled() {
        return useTimer;
    }
}