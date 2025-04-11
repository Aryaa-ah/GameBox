package com.gamebox.reaction;

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
import java.util.ResourceBundle;

public class ReactionGameController implements Initializable {
    @FXML private VBox layout;
    @FXML private Label promptLabel;
    @FXML private Button clickMeButton;
    @FXML private Label resultLabel;
    @FXML private Label roundLabel;
    @FXML private Button exitButton;
    @FXML private Button infoButton;
    private ReactionGame game;
    private Stage stage;
    private long startTime;

    public void setStage(Stage stage) {
        this.stage = stage;
        this.game = new ReactionGame(5);
        setupNextRound();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clickMeButton.setDisable(true);
        clickMeButton.setOnAction(e -> handleClick());
        if (exitButton != null) {
            exitButton.setOnAction(e -> stage.setScene(new MainMenuView(stage).getScene()));
        }
    }

    private void setupNextRound() {
        if (!game.hasMoreRounds()) {
            showResults();
            return;
        }

        clickMeButton.setDisable(true);
        promptLabel.setText("Get ready...");
        resultLabel.setText("");
        roundLabel.setText("Round: " + game.getCurrentRound() + "/" + game.getTotalRounds());

        PauseTransition pause = new PauseTransition(Duration.seconds(1 + Math.random() * 2));
        pause.setOnFinished(e -> {
            promptLabel.setText("Click Now! 🟢");
            startTime = System.currentTimeMillis();
            clickMeButton.setDisable(false);
        });
        pause.play();
    }

    private void handleClick() {
        long reactionTime = System.currentTimeMillis() - startTime;
        game.recordReaction(reactionTime);
        resultLabel.setText("⏱ Reaction Time: " + reactionTime + " ms");
        setupNextRound();
    }

    private void showResults() {
        promptLabel.setText("Game Over!");
        clickMeButton.setDisable(true);

        StringBuilder results = new StringBuilder("\nTop Reaction Times:\n");
        int rank = 1;
        for (Long time : game.getTopReactions(3)) {
            results.append("#").append(rank++).append(": ").append(time).append(" ms\n");
        }
        resultLabel.setText(results.toString());

        // Add buttons to restart or exit
        Button playAgain = new Button("▶️ Play Again");
        Button exit = new Button("❌ Exit to Menu");

        playAgain.setOnAction(e -> {
            game.reset();
            setupNextRound();
            layout.getChildren().removeAll(playAgain, exit);
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
