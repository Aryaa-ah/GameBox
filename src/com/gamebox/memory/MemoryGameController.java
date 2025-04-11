package com.gamebox.memory;

import com.gamebox.models.Flashcard;
import com.gamebox.ui.MainMenuView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class MemoryGameController {
    @FXML private Label emojiLabel;
    @FXML private Label scoreLabel;
    @FXML private Label timerLabel;
    @FXML private Label resultLabel;

    @FXML private Button option1;
    @FXML private Button option2;
    @FXML private Button option3;
    @FXML private Button option4;
    @FXML private Button nextBtn;
    @FXML private Button backBtn;
    @FXML private Button exitBtn;
    @FXML private Button infoButton;


    private Stage stage;
    private MemoryGame game;
    private List<Button> buttons;
    private Timeline timeline;
    private boolean useTimer;

    public void setStage(Stage stage, boolean useTimer) {
        this.stage = stage;
        this.useTimer = useTimer;
        this.game = new MemoryGame();
        this.buttons = Arrays.asList(option1, option2, option3, option4);

        setupHandlers();
        loadCard();

        if (useTimer) {
            startTimer();
        }
    }

    private void setupHandlers() {
        for (Button btn : buttons) {
            btn.setOnAction(e -> handleGuess(btn.getText()));
        }

        nextBtn.setOnAction(e -> {
            resultLabel.setVisible(false);
            game.nextCard();
            loadCard();
        });

        backBtn.setOnAction(e -> {
            resultLabel.setVisible(false);
            game.previousCard();
            loadCard();
        });

        exitBtn.setOnAction(e -> stage.setScene(new MainMenuView(stage).getScene()));
    }

    private void loadCard() {
        Flashcard currentCard = game.getCurrentCard();
        emojiLabel.setText(currentCard.getEmoji());
        List<String> options = game.getShuffledOptions(currentCard);

        for (int i = 0; i < 4; i++) {
            buttons.get(i).setText(options.get(i));
            buttons.get(i).setDisable(false);
        }

        scoreLabel.setText("Score: " + game.getScore());
        if (useTimer) timerLabel.setText("Time: " + game.getTime());
    }

    private void handleGuess(String selected) {
        boolean correct = game.checkAnswer(selected);
        resultLabel.setVisible(true);
        resultLabel.setText(correct ? "✅ Correct!" : "❌ Wrong! Answer: " + game.getCurrentCard().getLabel());
        resultLabel.setStyle(correct ? "-fx-text-fill: green;" : "-fx-text-fill: red;");

        for (Button btn : buttons) {
            btn.setDisable(true);
        }

        scoreLabel.setText("Score: " + game.getScore());
    }

    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            game.incrementTime();
            timerLabel.setText("Time: " + game.getTime());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    @FXML
    private void showInfoPopup() {
        String infoMessage =
            "Card Match is a classic memory game designed to:\n" +
            "• Improve short-term memory 🧠\n" +
            "• Enhance pattern recognition 🔍\n" +
            "• Strengthen visual recall 👁️\n\n" +
            "Find matching pairs as quickly and accurately as possible!\n\n" +
            "GameBox makes cognitive training fun and accessible.";

        String title = "Card Match Game";

        // Call utility method
        com.gamebox.utils.InfoPopUPUtil.showGameBoxInfo(infoMessage, title);
    }
}
