package com.gamebox.memoryrecall;

import com.gamebox.ui.MainMenuView;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MemoryRecallGameController implements Initializable {
    @FXML private VBox layout;
    @FXML private Label emojiLabel;
//    @FXML private Label scoreLabel;
    @FXML private Label roundLabel;
    @FXML private Label resultLabel;
    @FXML private Button option1, option2, option3, option4;
    @FXML private Button exitButton;
    @FXML private Button infoButton;

    private final Button[] optionButtons = new Button[4];
    private MemoryRecallGame game;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        game = new MemoryRecallGame();
        loadNextRound();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        optionButtons[0] = option1;
        optionButtons[1] = option2;
        optionButtons[2] = option3;
        optionButtons[3] = option4;

        for (Button btn : optionButtons) {
            btn.setOnAction(e -> handleChoice(btn.getText()));
        }

        exitButton.setOnAction(e -> stage.setScene(new MainMenuView(stage).getScene()));
    }

    private void loadNextRound() {
        if (!game.hasMoreRounds()) {
            showGameOver();
            return;
        }

        String emoji = game.getNextEmoji();
        emojiLabel.setText(emoji);
        resultLabel.setText("");
        for (Button btn : optionButtons) {
            btn.setDisable(true);
            btn.setText("");
        }

       // scoreLabel.setText("Score: " + game.getScore());
        roundLabel.setText("Round: " + game.getRound() + "/" + game.getTotalRounds());

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            emojiLabel.setText("❓ Which emoji did you see?");
            List<String> options = game.getShuffledOptions();
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(options.get(i));
                optionButtons[i].setDisable(false);
            }
        });
        pause.play();
    }

    private void handleChoice(String selected) {
        for (Button btn : optionButtons) btn.setDisable(true);
        boolean correct = game.checkAnswer(selected);
        resultLabel.setText(correct ? "✅ Correct!" : "❌ Incorrect!");
        game.nextRound();
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(e -> loadNextRound());
        delay.play();
    }

    private void showGameOver() {
        emojiLabel.setText("🎉 Game Over!");
        resultLabel.setText("Final Score: " + game.getScore() + "/" + game.getTotalRounds());

        Button playAgain = new Button("🔁 Play Again");
        Button exit = new Button("🏠 Exit to Menu");

        playAgain.setOnAction(e -> {
            game.reset();
            layout.getChildren().removeAll(playAgain, exit);
            loadNextRound();
        });
        exit.setOnAction(e -> stage.setScene(new MainMenuView(stage).getScene()));

        layout.getChildren().addAll(playAgain, exit);
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