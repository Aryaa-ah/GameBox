package com.gamebox.cardmatch;

import com.gamebox.ui.MainMenuView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class CardMatchController {
    @FXML private GridPane cardGrid;
    @FXML private Label infoLabel;
    @FXML private Button restartBtn;
    @FXML private Button exitBtn;
    @FXML private Button infoButton;
    @FXML private Label timerLabel;

    private CardMatchGame game;
    private Stage stage;
    private Map<Button, String> buttonToSymbol;
    private Button firstSelected = null;
    private int moveCount = 0;
    private boolean useTimer = false;
    private Timeline timer;
    private int elapsedSeconds = 0;

    public void setStage(Stage stage, boolean useTimer) {
        this.stage = stage;
        this.useTimer = useTimer;
        initializeGame();

        if (useTimer) {
            startTimer();
        }
    }

    private void initializeGame() {
        game = new CardMatchGame();
        buttonToSymbol = new HashMap<>();
        moveCount = 0;
        elapsedSeconds = 0;

        infoLabel.setText("Moves: 0");

        cardGrid.getChildren().clear();
        List<String> cards = game.getShuffledCards();
        int index = 0;

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                String symbol = cards.get(index++);
                Button btn = new Button("❓");
                btn.getStyleClass().add("card-button");

                btn.setOnAction(e -> handleCardClick(btn, symbol));
                cardGrid.add(btn, col, row);
                buttonToSymbol.put(btn, symbol);
            }
        }

        restartBtn.setOnAction(e -> {
            if (timer != null) timer.stop();
            setStage(stage, useTimer); // restart fresh
        });

        exitBtn.setOnAction(e -> {
            if (timer != null) timer.stop();
            stage.setScene(new MainMenuView(stage).getScene());
        });
    }

    private void handleCardClick(Button btn, String symbol) {
        if (btn.getText().equals(symbol) || (firstSelected != null && btn == firstSelected)) return;

        btn.setText(symbol);

        if (firstSelected == null) {
            firstSelected = btn;
        } else {
            moveCount++;
            infoLabel.setText("Moves: " + moveCount);

            String firstSymbol = buttonToSymbol.get(firstSelected);
            if (firstSymbol.equals(symbol)) {
                firstSelected = null;
                if (gameOver()) {
                    showResultDialog();
                }
            } else {
                Timer delay = new Timer();
                delay.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        javafx.application.Platform.runLater(() -> {
                            firstSelected.setText("❓");
                            btn.setText("❓");
                            firstSelected = null;
                        });
                    }
                }, 600);
            }
        }
    }

    private boolean gameOver() {
        return cardGrid.getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .allMatch(btn -> !btn.getText().equals("❓"));
    }

    private void showResultDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("🎉 Game Complete!");
        alert.setHeaderText("Well done!");

        String timeText = useTimer ? "\nTime: " + elapsedSeconds + "s" : "";
        alert.setContentText("You completed the game in " + moveCount + " moves." + timeText);

        ButtonType replay = new ButtonType("🔁 Restart");
        ButtonType menu = new ButtonType("🏠 Main Menu");

        alert.getButtonTypes().setAll(replay, menu);
        alert.showAndWait().ifPresent(result -> {
            if (result == replay) {
                if (timer != null) timer.stop();
                setStage(stage, useTimer);
            } else {
                if (timer != null) timer.stop();
                stage.setScene(new MainMenuView(stage).getScene());
            }
        });
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

    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            elapsedSeconds++;
            timerLabel.setText("Time: " + elapsedSeconds + "s");
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

}
