package com.gamebox.binarysearch;

import com.gamebox.ui.MainMenuView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class BinarySearchController {
    @FXML
    private Label feedbackLabel;
    @FXML
    private Label guessLabel;
    @FXML
    private FlowPane numberButtons;
//    @FXML
//    private VBox treeDisplay;
    @FXML
    private Button nextButton;
    @FXML
    private Button exitButton;

    private List<Integer> numbers;
    private int target;
    private int guessCount = 0;
    private TreeNode bstRoot;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        generateUnsortedList();
        drawNumberButtons();
      //  drawTree(bstRoot, 0);
        feedbackLabel.setText("Click a number to start searching (via BST).");
        guessLabel.setText("Guesses: 0");

        nextButton.setOnAction(e -> resetGame());
        exitButton.setOnAction(e -> stage.setScene(new MainMenuView(stage).getScene()));
    }

    private void generateUnsortedList() {
        numbers = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            numbers.add(rand.nextInt(90) + 10);
        }

        bstRoot = null;
        for (int num : numbers) {
            bstRoot = insertBST(bstRoot, num);
        }

        Collections.sort(numbers);
        target = numbers.get(rand.nextInt(numbers.size()));
        System.out.println("Target (in BST): " + target);
    }

    private TreeNode insertBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val) root.left = insertBST(root.left, val);
        else root.right = insertBST(root.right, val);
        return root;
    }

    private void drawNumberButtons() {
        numberButtons.getChildren().clear();
        for (int num : numbers) {
            Button btn = new Button(String.valueOf(num));
            btn.setOnAction(e -> handleGuess(num));
            btn.setPrefWidth(40);
            numberButtons.getChildren().add(btn);
        }
    }

//    private void drawTree(TreeNode node, int level) {
//        treeDisplay.getChildren().clear();
//        drawTreeRecursive(node, 0, "Root");
//    }
//
//    private void drawTreeRecursive(TreeNode node, int level, String position) {
//        if (node == null) return;
//        Label label = new Label(" ".repeat(level * 4) + position + ": " + node.val);
//        treeDisplay.getChildren().add(label);
//        drawTreeRecursive(node.left, level + 1, "L");
//        drawTreeRecursive(node.right, level + 1, "R");
//    }

    private void handleGuess(int guess) {
        guessCount++;
        guessLabel.setText("Guesses: " + guessCount);
        String pathFeedback = searchBSTWithFeedback(bstRoot, guess);
        feedbackLabel.setText(pathFeedback);

        if (guess == target) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You Win!");
            alert.setHeaderText("Well done!");
            alert.setContentText("You found the number " + target + " in " + guessCount + " guesses using a BST.");
            alert.showAndWait();
        }
    }

    private String searchBSTWithFeedback(TreeNode root, int guess) {
        if (root == null) return "❌ Not found (this shouldn't happen)";
        if (guess == root.val) return "✅ Correct! The number was " + guess;
        if (guess < root.val) return "Too Low! BST went left.";
        return "Too High! BST went right.";
    }

    private void resetGame() {
        guessCount = 0;
        generateUnsortedList();
        drawNumberButtons();
      //  drawTree(bstRoot, 0);
        feedbackLabel.setText("New round started. Try again!");
        guessLabel.setText("Guesses: 0");
    }

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }
}