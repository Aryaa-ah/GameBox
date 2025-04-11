package com.gamebox.pathfinder;

import com.gamebox.ui.MainMenuView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PathFinderController implements Initializable {
    @FXML private GridPane gridPane;
    @FXML private Button newGridBtn;
    @FXML private Button exitButton;
    @FXML private Button findPathBtn;
    @FXML private Label moveLabel;
    @FXML private Label timerLabel;
    @FXML private Button infoButton;
    
    private PathFinderGame game;
    private Stage stage;
    private int playerRow = 0, playerCol = 0;
    private int moveCount = 0;
    private boolean useTimer = false;
    private long startTime;

    public void setStage(Stage stage) {
        this.stage = stage;
        this.game = new PathFinderGame();
        askTimerOption();
        drawGrid();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newGridBtn.setOnAction(e -> {
            game = new PathFinderGame();
            playerRow = 0;
            playerCol = 0;
            moveCount = 0;
            if (useTimer) startTime = System.currentTimeMillis();
            drawGrid();
        });

        exitButton.setOnAction(e -> stage.setScene(new MainMenuView(stage).getScene()));
        findPathBtn.setOnAction(e -> highlightShortestPath());
    }

    private void askTimerOption() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Enable Timer?");
        alert.setHeaderText("Would you like to enable timer tracking?");
        ButtonType yes = new ButtonType("✅ Yes");
        ButtonType no = new ButtonType("🚫 No");
        alert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> result = alert.showAndWait();
        useTimer = result.isPresent() && result.get() == yes;

        if (useTimer) {
            startTime = System.currentTimeMillis();
            timerLabel.setVisible(true);
        } else {
            timerLabel.setVisible(false);
        }
    }

    private void drawGrid() {
        gridPane.getChildren().clear();
        int[][] grid = game.getGrid();
        moveLabel.setText("Moves: " + moveCount);

        if (useTimer) {
            long elapsed = (System.currentTimeMillis() - startTime) / 1000;
            timerLabel.setText("Time: " + elapsed + "s");
        }

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(60, 60);

                Rectangle bg = new Rectangle(60, 60);
                bg.setStroke(Color.BLACK);

                if (game.isBlocked(row, col)) {
                    bg.setFill(Color.DARKGRAY);
                } else if (row == playerRow && col == playerCol) {
                    bg.setFill(Color.LIGHTBLUE);
                    cell.getChildren().addAll(bg, new Text("🧍"));
                } else if (row == 4 && col == 4) {
                    bg.setFill(Color.WHITE);
                    cell.getChildren().addAll(bg, new Text("🏁"));
                } else {
                    bg.setFill(Color.WHITE);
                    cell.getChildren().add(bg);
                }

                final int r = row, c = col;
                cell.setOnMouseClicked(e -> {
                    if (isValidMove(r, c)) {
                        playerRow = r;
                        playerCol = c;
                        moveCount++;
                        drawGrid();

                        if (playerRow == 4 && playerCol == 4) {
                            showSuccessDialog();
                        }
                    }
                });

                gridPane.add(cell, col, row);
            }
        }
    }

    private boolean isValidMove(int r, int c) {
        if (game.isBlocked(r, c)) return false;
        int dr = Math.abs(playerRow - r);
        int dc = Math.abs(playerCol - c);
        return (dr + dc == 1); // adjacent only
    }

    private void highlightShortestPath() {
        List<int[]> path = game.findShortestPathBFS();
        for (int[] pos : path) {
            int r = pos[0], c = pos[1];
            if ((r == 0 && c == 0) || (r == 4 && c == 4)) continue;
            StackPane pane = (StackPane) getNodeFromGridPane(r, c);
            pane.setStyle("-fx-background-color: lightgreen; -fx-border-color: black;");
        }
    }

    private Node getNodeFromGridPane(int row, int col) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }
        return null;
    }

    private void showSuccessDialog() {
    	highlightShortestPath();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Path Completed");
        alert.setHeaderText("🎉 You reached the goal!");

        String timeInfo = useTimer ? "⏱ Time: " + (System.currentTimeMillis() - startTime) / 1000 + "s\n" : "";

        // Calculate shortest path length using BFS
        int shortestMoves = game.findShortestPathBFS().size() - 1; // excluding start node

        alert.setContentText(
            "Well done!\n" +
            timeInfo +
            "🧍 Your Moves: " + moveCount + "\n" +
            "📍 Shortest Path: " + shortestMoves + " moves\n\nPlay again?"
        );

        ButtonType playAgain = new ButtonType("🔁 New Grid");
        ButtonType exit = new ButtonType("❌ Exit to Menu");
        alert.getButtonTypes().setAll(playAgain, exit);

        alert.showAndWait().ifPresent(response -> {
            if (response == playAgain) {
                game = new PathFinderGame();
                playerRow = 0;
                playerCol = 0;
                moveCount = 0;
                if (useTimer) startTime = System.currentTimeMillis();
                drawGrid();
            } else {
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
}
