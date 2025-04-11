package com.gamebox.alphabettrain;

import com.gamebox.ui.MainMenuView;
import com.gamebox.utils.InfoPopUPUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


import java.util.List;

public class AlphabetTrainGameController {

    @FXML private Label scoreLabel;
    @FXML private Label sequenceLabel;
    @FXML private TextField answerField;
    @FXML private Button submitButton;
    @FXML private Label feedbackLabel;
    @FXML private Button exitButton;
    @FXML private Button infoButton;

    private Timeline timer;
    private int timeLeft = 10;
    @FXML private Label timerLabel;

    private AlphabetTrainGame game;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        this.game = new AlphabetTrainGame();
        loadCurrentSequence();
        setupActions();
    }

    private void setupActions() {
        // Handle submit logic
    	startTimer();
        submitButton.setOnAction(e -> {
            String input = answerField.getText().trim();
            if (!input.isEmpty()) {
                boolean correct = game.checkAnswer(input);
                feedbackLabel.setText(correct ? "✅ Correct!" : "❌ Wrong!");
                feedbackLabel.setStyle("-fx-text-fill: " + (correct ? "green;" : "red;"));
                answerField.clear();
                game.next();
                loadCurrentSequence();
            }
        });

        // Info popup content and title
        String infoMessage = 
            "GameBox is a collection of cognitive games designed to:\n" +
            "• Stimulate memory 🧠\n" +
            "• Improve focus 🎯\n" +
            "• Strengthen response time ⚡\n\n" +
            "These games are especially helpful for individuals with Alzheimer's or cognitive challenges.\n\n" +
            "Thank you for playing!";
        
        String title = "Alphabet Train";

        // Exit and Info button actions
        exitButton.setOnAction(e -> stage.setScene(new MainMenuView(stage).getScene()));
        infoButton.setOnAction(e -> InfoPopUPUtil.showGameBoxInfo(infoMessage, title));
    }

    private void loadCurrentSequence() {
        List<Character> sequence = game.getCurrentSequence();
        StringBuilder builder = new StringBuilder();
        for (char c : sequence) {
            builder.append(c).append(" ");
        }
        sequenceLabel.setText(builder.toString().trim());
        scoreLabel.setText("Score: " + game.getScore());
    }
    private void startTimer() {
        timeLeft = 10;
        timerLabel.setText("Time: " + timeLeft + "s");

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft + "s");
            if (timeLeft <= 0) {
                timer.stop();
                feedbackLabel.setText("⏰ Time's up!");
                feedbackLabel.setStyle("-fx-text-fill: orange;");
                nextRound();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void nextRound() {
        game.next();
        loadCurrentSequence();
        startTimer();
        answerField.clear();
    }

}
