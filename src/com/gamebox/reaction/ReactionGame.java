package com.gamebox.reaction;

import java.util.PriorityQueue;

public class ReactionGame {
    private final PriorityQueue<Long> reactionTimes;
    private int totalRounds;
    private int currentRound;

    public ReactionGame(int rounds) {
        this.totalRounds = rounds;
        this.currentRound = 0;
        this.reactionTimes = new PriorityQueue<>();
    }

    public boolean hasMoreRounds() {
        return currentRound < totalRounds;
    }

    public void recordReaction(long timeTakenMillis) {
        reactionTimes.offer(timeTakenMillis);
        currentRound++;
    }

    public PriorityQueue<Long> getTopReactions(int topN) {
        PriorityQueue<Long> copy = new PriorityQueue<>(reactionTimes);
        PriorityQueue<Long> result = new PriorityQueue<>();
        for (int i = 0; i < topN && !copy.isEmpty(); i++) {
            result.offer(copy.poll());
        }
        return result;
    }

    public int getCurrentRound() {
        return currentRound + 1;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public void reset() {
        currentRound = 0;
        reactionTimes.clear();
    }
}
