package com.gamebox.memory;

import com.gamebox.models.Flashcard;
import com.gamebox.ui.MainMenuView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class MemoryGameController implements Initializable {
    @FXML private VBox layout;
    @FXML private Label emojiLabel, scoreLabel, timerLabel, resultLabel;
    @FXML private Button choice0, choice1, choice2, choice3;
    @FXML private Button nextBtn, backBtn, exitBtn;

    private final Button[] choiceButtons = new Button[4];
    private Stage stage;
    private boolean useTimer;
    private Flashcard currentCard;
    private MemoryGame game;
    private Timeline timer;
    private int timeLeft = 5;

    public void setStage(Stage stage, boolean useTimer) {
        this.stage = stage;
        this.useTimer = useTimer;
        timerLabel.setVisible(useTimer);
        game = new MemoryGame();
        loadNextCard();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceButtons[0] = choice0;
        choiceButtons[1] = choice1;
        choiceButtons[2] = choice2;
        choiceButtons[3] = choice3;

        nextBtn.setOnAction(e -> loadNextCard());
        backBtn.setOnAction(e -> loadLastCard());
        exitBtn.setOnAction(e -> stage.setScene(new MainMenuView(stage).getScene()));

        for (Button btn : choiceButtons) {
            btn.setOnAction(e -> handleChoice(((Button)e.getSource()).getText()));
        }
    }

    private void loadNextCard() {
        stopTimer();
        if (!game.hasMoreCards()) {
            emojiLabel.setText("✅");
            resultLabel.setText("Game Over! Final Score: " + game.getScore() + "/" + game.getTotalQuestions());
            for (Button btn : choiceButtons) btn.setDisable(true);
            showEndPopup();
            return;
        }

        currentCard = game.getNextCard();
        emojiLabel.setText(currentCard.getEmoji());
        List<String> choices = game.getShuffledChoices(currentCard.getAnswer());
        for (int i = 0; i < 4; i++) {
            choiceButtons[i].setText(choices.get(i));
            choiceButtons[i].setDisable(false);
        }

        resultLabel.setText("");
        if (useTimer) startTimer();
    }

    private void handleChoice(String selected) {
        stopTimer();
        for (Button btn : choiceButtons) btn.setDisable(true);
        if (selected.equals(currentCard.getAnswer())) {
            resultLabel.setText("✅ Correct!");
            game.increaseScore();
        } else {
            resultLabel.setText("❌ Wrong! Answer: " + currentCard.getAnswer());
        }
        scoreLabel.setText("Score: " + game.getScore());
    }

    private void loadLastCard() {
        stopTimer();
        Flashcard last = game.peekLastShown();
        if (last != null) {
            emojiLabel.setText(last.getEmoji());
            resultLabel.setText("Last Seen: " + last.getAnswer());
        } else {
            resultLabel.setText("No history available.");
        }
    }

    private void startTimer() {
        timeLeft = 5;
        timerLabel.setText("Time: " + timeLeft);
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);
            if (timeLeft == 0) {
                stopTimer();
                resultLabel.setText("⏱️ Time's up! Answer: " + currentCard.getAnswer());
                for (Button btn : choiceButtons) btn.setDisable(true);
            }
        }));
        timer.setCycleCount(5);
        timer.play();
    }

    private void stopTimer() {
        if (timer != null) timer.stop();
    }

    private void showEndPopup() {
        Stage popup = new Stage();
        popup.setTitle("Game Finished");
        popup.initOwner(stage);

        Label message = new Label("You've completed all flashcards.\nWhat would you like to do next?");
        Button playAgain = new Button("▶️ Play Again");
        Button exit = new Button("❌ Exit to Menu");

        playAgain.setOnAction(e -> {
            popup.close();
            MemoryGameView view = new MemoryGameView(stage);
            stage.setScene(view.getStartupScene());
        });

        exit.setOnAction(e -> {
            popup.close();
            stage.setScene(new MainMenuView(stage).getScene());
        });

        VBox box = new VBox(15, message, playAgain, exit);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 20;");
        popup.setScene(new Scene(box, 300, 160));
        popup.initModality(javafx.stage.Modality.WINDOW_MODAL);
        popup.showAndWait();
    }
}
