package com.gamebox.ui;

import com.gamebox.memory.MemoryGameView;
import com.gamebox.memoryrecall.MemoryRecallGameView;
import com.gamebox.pathfinder.PathFinderView;
import com.gamebox.reaction.ReactionGameView;
import com.gamebox.cardmatch.CardMatchView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView {
    private final VBox layout;

    public MainMenuView(Stage stage) {
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setPrefSize(800, 600);
        layout.getStyleClass().add("vbox"); // from style.css

        Label title = new Label("🎮 Choose a Game");
        title.getStyleClass().add("heading");

        Button memoryGameBtn = new Button("🧠 Flashcard Memory Game");
        memoryGameBtn.setOnAction(e -> stage.setScene(new MemoryGameView(stage).getStartupScene()));

        Button reactionGameBtn = new Button("⚡ Reaction Timer Game");
        reactionGameBtn.setOnAction(e -> stage.setScene(new ReactionGameView(stage).getScene()));

        Button memoryRecallBtn = new Button("🧩 Memory Recall Game");
        memoryRecallBtn.setOnAction(e -> stage.setScene(new MemoryRecallGameView(stage).getScene()));

        Button pathfinderBtn = new Button("🧭 Path Finder Game");
        pathfinderBtn.setOnAction(e -> stage.setScene(new PathFinderView(stage).getScene()));

        Button cardMatchBtn = new Button("🃏 Card Match Game");
        cardMatchBtn.setOnAction(e -> stage.setScene(new CardMatchView(stage).getScene()));

        // Apply global button style
        for (Button btn : new Button[]{memoryGameBtn, reactionGameBtn, memoryRecallBtn, pathfinderBtn, cardMatchBtn}) {
            btn.getStyleClass().add("button");
            btn.setPrefWidth(300);
        }

        layout.getChildren().addAll(title, memoryGameBtn, reactionGameBtn, memoryRecallBtn, pathfinderBtn, cardMatchBtn);
    }

    public VBox getView() {
        return layout;
    }

    public Scene getScene() {
        Scene scene = new Scene(getView(), 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/gamebox/css/menu.css").toExternalForm());
        return scene;
    }
}
