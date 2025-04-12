package com.gamebox.binarysearch;

import com.gamebox.models.BinarySearchGameEngine;
import com.gamebox.ui.MainMenuView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class BinarySearchController {

    @FXML private Label feedbackLabel;
    @FXML private Label guessLabel;
    @FXML private Label currentNodeLabel;
    @FXML private Label targetLabel;
    @FXML private Button leftButton;
    @FXML private Button rightButton;
    @FXML private Button backButton;
    @FXML private Button resetPositionButton;
    @FXML private Button nextButton;
    @FXML private Button exitButton;
    @FXML private Button infoButton;

    private BinarySearchGameEngine engine = new BinarySearchGameEngine();
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        // Initialize game
        engine.generateNewGame();
        updateUI("Navigate the BST to find the hidden number.");

        // Setup button actions
        leftButton.setOnAction(e -> updateUI(engine.moveLeft()));
        rightButton.setOnAction(e -> updateUI(engine.moveRight()));
        backButton.setOnAction(e -> updateUI(engine.goBack()));
        resetPositionButton.setOnAction(e -> {
            engine.resetPosition();
            updateUI("Reset to root. Try again!");
        });
        nextButton.setOnAction(e -> {
            engine.generateNewGame();
            updateUI("New round started. Navigate to find the number!");
        });
        exitButton.setOnAction(e -> stage.setScene(new MainMenuView(stage).getScene()));
        infoButton.setOnAction(e -> showInstructions());
    }

    private void updateUI(String message) {
        feedbackLabel.setText(message);
        currentNodeLabel.setText("Current Node: " + engine.getCurrentValue());
        guessLabel.setText("Guesses: " + engine.getGuessCount());
        targetLabel.setText("Target Number: " + engine.getTarget());

        leftButton.setDisable(!engine.canMoveLeft());
        rightButton.setDisable(!engine.canMoveRight());
        backButton.setDisable(!engine.canGoBack());

        if (engine.getCurrentValue() == engine.getTarget()) {
            // Game won — show popup and disable controls
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You Win!");
            alert.setHeaderText("✅ Target Found!");
            alert.setContentText("You found the number " + engine.getTarget() + " in " + engine.getGuessCount() + " steps.");
            alert.showAndWait();

            leftButton.setDisable(true);
            rightButton.setDisable(true);
            backButton.setDisable(true);
            resetPositionButton.setDisable(true);
        }
    }

    private void showInstructions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("How to Play");
        alert.setHeaderText("Binary Search Tree Game");
        alert.setContentText(
            "🎯 Goal: Find the hidden number inside a Binary Search Tree (BST).\n\n" +
            "🔹 If the number is smaller than the current node, go LEFT.\n" +
            "🔹 If it's greater, go RIGHT.\n" +
            "🔁 Use BACK to undo or RESET to go to the root.\n" +
            "🧠 Think logically — it's a game of deduction!"
        );
        alert.showAndWait();
    }
}
