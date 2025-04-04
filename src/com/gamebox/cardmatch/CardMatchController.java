package com.gamebox.cardmatch;

import com.gamebox.ui.MainMenuView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CardMatchController implements Initializable {
    @FXML private GridPane gridPane;
    @FXML private Button exitButton;
    @FXML private Label timerLabel;

    private final Button[] buttons = new Button[12];
    private final boolean[] flipped = new boolean[12];
    private CardMatchGame game;
    private int lastFlipped = -1;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        askTimerMode();
    }

    private void askTimerMode() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Start With Timer?");
        alert.setHeaderText("Do you want to play with a timer?");
        alert.setContentText("Choose your game mode.");

        ButtonType yes = new ButtonType("✅ Yes");
        ButtonType no = new ButtonType("🚫 No");
        alert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> result = alert.showAndWait();
        boolean useTimer = result.isPresent() && result.get() == yes;

        startGame(useTimer);
        if (useTimer) startTimerThread();
    }

    private void startGame(boolean useTimer) {
        game = new CardMatchGame(useTimer);
        var cards = game.getShuffledCards();

        for (int i = 0; i < 12; i++) {
            int idx = i;
            Button btn = new Button("❓");
            btn.setPrefSize(80, 80);
            btn.setStyle("-fx-font-size: 24px");

            btn.setOnAction(e -> handleFlip(idx));
            buttons[i] = btn;
            flipped[i] = false;

            gridPane.add(btn, i % 4, i / 4);
        }

        exitButton.setOnAction(e -> {
            stage.setScene(new MainMenuView(stage).getScene());
        });

        timerLabel.setVisible(useTimer);
    }

    private void handleFlip(int index) {
        if (game.isMatched(index) || flipped[index]) return;

        buttons[index].setText(game.getShuffledCards().get(index));
        flipped[index] = true;

        if (lastFlipped == -1) {
            lastFlipped = index;
        } else {
            game.incrementMoves();
            int prev = lastFlipped;
            lastFlipped = -1;

            if (game.isMatch(prev, index)) {
                game.markMatched(prev, index);
                if (game.allMatched()) showGameOver();
            } else {
                new Thread(() -> {
                    try { Thread.sleep(600); } catch (InterruptedException ignored) {}
                    javafx.application.Platform.runLater(() -> {
                        buttons[prev].setText("❓");
                        buttons[index].setText("❓");
                        flipped[prev] = false;
                        flipped[index] = false;
                    });
                }).start();
            }
        }
    }

    private void showGameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("🎉 All Matches Found!");

        String result = "Moves Taken: " + game.getMoves();
        if (game.isTimerEnabled()) {
            result += "\nTime: " + game.getElapsedTimeSeconds() + "s";
        }

        alert.setContentText(result);
        alert.showAndWait();
        stage.setScene(new MainMenuView(stage).getScene());
    }

    private void startTimerThread() {
        Thread timerThread = new Thread(() -> {
            while (!game.allMatched()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
                javafx.application.Platform.runLater(() -> {
                    timerLabel.setText("Time: " + game.getElapsedTimeSeconds() + "s");
                });
            }
        });
        timerThread.setDaemon(true);
        timerThread.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // handled in setStage()
    }
}
