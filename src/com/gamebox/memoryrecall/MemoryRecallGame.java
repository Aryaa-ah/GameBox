package com.gamebox.memoryrecall;

import java.util.*;

public class MemoryRecallGame {
    private final List<String> emojiPool = Arrays.asList("🍎", "🐶", "🚗", "🌟", "🏀", "🐱", "🍕", "🎵", "🎈", "🦋");
    private final Random random = new Random();
    private int score = 0;
    private int round = 0;
    private final int totalRounds = 5;
    private String currentEmoji;

    public String getNextEmoji() {
        currentEmoji = emojiPool.get(random.nextInt(emojiPool.size()));
        return currentEmoji;
    }

    public List<String> getShuffledOptions() {
        Set<String> options = new HashSet<>();
        options.add(currentEmoji);
        while (options.size() < 4) {
            options.add(emojiPool.get(random.nextInt(emojiPool.size())));
        }
        List<String> shuffled = new ArrayList<>(options);
        Collections.shuffle(shuffled);
        return shuffled;
    }

    public boolean checkAnswer(String selectedEmoji) {
        if (selectedEmoji.equals(currentEmoji)) {
            score++;
            return true;
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    public int getRound() {
        return round + 1;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public boolean hasMoreRounds() {
        return round < totalRounds;
    }

    public void nextRound() {
        round++;
    }

    public void reset() {
        score = 0;
        round = 0;
    }
}