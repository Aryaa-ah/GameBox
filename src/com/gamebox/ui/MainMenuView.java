package com.gamebox.ui;

import com.gamebox.memory.MemoryGameView;
import com.gamebox.memoryrecall.MemoryRecallGameView;
import com.gamebox.pathfinder.PathFinderView;
import com.gamebox.reaction.ReactionGameView;
import com.gamebox.cardmatch.CardMatchView;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView {
    private final VBox layout;

    public MainMenuView(Stage stage) {
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Flashcard Memory Game
        Button memoryGameBtn = new Button("🧠 Flashcard Memory Game");
        memoryGameBtn.setOnAction(e -> {
            MemoryGameView view = new MemoryGameView(stage);
            stage.setScene(view.getStartupScene());
        });

        // Reaction Game
        Button reactionGameBtn = new Button("🚦 Reaction Timer Game");
        reactionGameBtn.setOnAction(e -> {
            ReactionGameView view = new ReactionGameView(stage);
            stage.setScene(view.getScene());
        });

        // Memory Recall Game
        Button memoryRecallBtn = new Button("🧠 Memory Recall Game");
        memoryRecallBtn.setOnAction(e -> {
            MemoryRecallGameView view = new MemoryRecallGameView(stage);
            stage.setScene(view.getScene());
        });

        // Path Finder Game
        Button pathfinderBtn = new Button("🧭 Path Finder Game");
        pathfinderBtn.setOnAction(e -> {
            PathFinderView view = new PathFinderView(stage);
            stage.setScene(view.getScene());
        });

        // ✅ Card Flip Match Game
        Button cardMatchBtn = new Button("🧩 Card Flip Match Game");
        cardMatchBtn.setOnAction(e -> {
            CardMatchView view = new CardMatchView(stage);
            stage.setScene(view.getScene());
        });

        layout.getChildren().addAll(
            memoryGameBtn,
            reactionGameBtn,
            memoryRecallBtn,
            pathfinderBtn,
            cardMatchBtn
        );
    }

    public VBox getView() {
        return layout;
    }

    public Scene getScene() {
        return new Scene(getView(), 600, 400);
    }
}
