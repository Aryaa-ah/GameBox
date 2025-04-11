package com.gamebox.ui;

import com.gamebox.memory.MemoryGameView;
import com.gamebox.memoryrecall.MemoryRecallGameView;
import com.gamebox.pathfinder.PathFinderView;
import com.gamebox.reaction.ReactionGameView;
import com.gamebox.cardmatch.CardMatchView;
import com.gamebox.alphabettrain.AlphabetTrainView;
import com.gamebox.utils.InfoPopUPUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Optional;

public class MainMenuView {
    private final VBox layout;

    public MainMenuView(Stage stage) {
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setPrefSize(800, 600);
        layout.getStyleClass().add("vbox");

        Label title = new Label("🎮 GameBox");
        title.getStyleClass().add("heading");

        // Info button at the top
        Button infoButton = createWrappedButton("Learn more about the game", e -> {
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("About GameBox");
            infoAlert.setHeaderText("Welcome to GameBox 🧠🎮");
            infoAlert.setContentText(
                "GameBox is a collection of simple, fun cognitive games designed to stimulate memory, " +
                "attention, and processing — especially helpful for Alzheimer's patients.\n\n" +
                "Each game is carefully crafted to engage and entertain while providing mental exercise."
            );
            infoAlert.showAndWait();
        });

        // Create game buttons with wrapped labels
        Button memoryGameBtn = createWrappedButton("🧠 Flashcard Memory Game", e -> {
            String info = "Flashcard Memory helps improve recall and recognition through visual association. "
                        + "Great for strengthening short-term memory. 🧠";
            Optional<ButtonType> result = InfoPopUPUtil.showGameBoxInfo(info, "Flashcard Memory Game");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                stage.setScene(new MemoryGameView(stage).getStartupScene());
            }
        });

        Button reactionGameBtn = createWrappedButton("⚡ Reaction Timer Game", e -> {
            String info = "Test and improve your reflexes! Hit the button as quickly as you can. ⚡ "
                        + "Great for practicing response time and attention.";
            Optional<ButtonType> result = InfoPopUPUtil.showGameBoxInfo(info, "Reaction Timer Game");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                stage.setScene(new ReactionGameView(stage).getScene());
            }
        });

        Button memoryRecallBtn = createWrappedButton("🧩 Memory Recall Game", e -> {
            String info = "Remember the emoji you saw and pick the correct one. 👁️ "
                        + "This game sharpens visual recall and attention to detail.";
            Optional<ButtonType> result = InfoPopUPUtil.showGameBoxInfo(info, "Memory Recall Game");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                stage.setScene(new MemoryRecallGameView(stage).getScene());
            }
        });

        Button pathfinderBtn = createWrappedButton("🧭 Path Finder Game", e -> {
            String info = "Find the path through the grid. A great puzzle to boost logical thinking and memory! 🧭";
            Optional<ButtonType> result = InfoPopUPUtil.showGameBoxInfo(info, "Path Finder Game");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                stage.setScene(new PathFinderView(stage).getScene());
            }
        });

        Button cardMatchBtn = createWrappedButton("🃏 Card Match Game", e -> {
            String info = "Match identical emoji cards by memory. A timeless game for pattern and memory training. 🃏";
            Optional<ButtonType> result = InfoPopUPUtil.showGameBoxInfo(info, "Card Match Game");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                stage.setScene(new CardMatchView(stage).getScene());
            }
        });

        Button binarySearchBtn = createWrappedButton("🔍 Binary Search Game", e -> {
            String info = "Guess the target number in a sorted array using binary search logic. 🔍\n"
                        + "Get hints like 'Too High' or 'Too Low' until you guess right.\n"
                        + "Ideal for understanding divide-and-conquer efficiency!";
            Optional<ButtonType> result = InfoPopUPUtil.showGameBoxInfo(info, "Binary Search Game");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                stage.setScene(new com.gamebox.binarysearch.BinarySearchView(stage).getScene());
            }
        });

        // Arrange game buttons in 2 rows
        HBox row1 = new HBox(20, memoryGameBtn, reactionGameBtn, memoryRecallBtn);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(20, pathfinderBtn, cardMatchBtn, binarySearchBtn);
        row2.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(title, infoButton, row1, row2);
    }

    private Button createWrappedButton(String text, EventHandler<ActionEvent> handler) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Double.MAX_VALUE);

        Button button = new Button();
        button.setGraphic(label);
        button.setPrefWidth(220);
        button.setPrefHeight(80);
        button.setOnAction(handler);
        button.getStyleClass().add("button");

        return button;
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
