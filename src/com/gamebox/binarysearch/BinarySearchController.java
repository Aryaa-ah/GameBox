package com.gamebox.binarysearch;

import com.gamebox.ui.MainMenuView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.*;

public class BinarySearchController {
    @FXML
    private Label feedbackLabel;
    @FXML
    private Label guessLabel;
    @FXML
    private Label currentNodeLabel;
    @FXML
    private Label targetLabel;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;
    @FXML
    private Button backButton; // Back button
    @FXML
    private Button resetPositionButton; // Reset position button
    @FXML
    private Button nextButton;
    @FXML
    private Button exitButton;

    private List<Integer> numbers;
    private int target;
    private int guessCount = 0;
    private TreeNode bstRoot;
    private TreeNode currentNode;
    private Stack<TreeNode> navigationHistory; // To track visited nodes
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        navigationHistory = new Stack<>();
        generateBST();
        currentNode = bstRoot;
        updateCurrentNodeLabel();
        
        // Initialize guess count
        guessCount = 0;
        guessLabel.setText("Guesses: 0");
        
        // Set target display
        targetLabel.setText("Target Number: " + target);
        
        // Check if root is already the target
        if (bstRoot.val == target) {
            feedbackLabel.setText("The root node is the target! Lucky start!");
        } else {
            feedbackLabel.setText("Navigate the BST to find the hidden number.");
        }

        // Set button actions
        leftButton.setOnAction(e -> moveLeft());
        rightButton.setOnAction(e -> moveRight());
        backButton.setOnAction(e -> goBack());
        resetPositionButton.setOnAction(e -> resetPosition());
        nextButton.setOnAction(e -> resetGame());
        exitButton.setOnAction(e -> stage.setScene(new MainMenuView(stage).getScene()));
        
        // Update button states initially
        updateButtonState();
        
        // Print initial tree structure to console
        System.out.println("Initial BST structure:");
        printTree();
    }

    private void generateBST() {
        // Generate unique random numbers
        Set<Integer> uniqueNumbers = new HashSet<>();
        Random rand = new Random();
        while (uniqueNumbers.size() < 10) {
            uniqueNumbers.add(rand.nextInt(90) + 10);
        }
        numbers = new ArrayList<>(uniqueNumbers);
        
        // Create a sorted copy for selecting the target
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        Collections.sort(sortedNumbers);
        
        // Select a random target from the sorted list
        target = sortedNumbers.get(rand.nextInt(sortedNumbers.size()));
        System.out.println("Target (in BST): " + target);
        
        // Build the BST with the original unsorted list
        bstRoot = null;
        System.out.println("Building BST with numbers: " + numbers);
        for (int num : numbers) {
            System.out.println("Inserting: " + num);
            bstRoot = insertBST(bstRoot, num);
        }
        
        // Print the tree structure after building
        System.out.println("BST construction complete.");
    }

    private TreeNode insertBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val) root.left = insertBST(root.left, val);
        else if (val > root.val) root.right = insertBST(root.right, val);
        return root;
    }

    private void moveLeft() {
        guessCount++; // Always increment guess count for any attempt
        guessLabel.setText("Guesses: " + guessCount);
        
        // Save current position in history before moving
        navigationHistory.push(currentNode);
        
        if (currentNode.left != null) {
            currentNode = currentNode.left;
            updateCurrentNodeLabel();
            checkTargetFound();
            
            // Print current tree state with player position
            System.out.println("Moved LEFT to node " + currentNode.val);
            printTree();
        } else {
            // We push and then immediately pop since we didn't actually move
            navigationHistory.pop();
            
            // Check if current node is the target when hitting a deadend
            if (currentNode.val == target) {
                winGame();
            } else {
                feedbackLabel.setText("❌ No left child! Dead end. Target is " + 
                    (target < currentNode.val ? "smaller" : "greater") + " than current node." +
                    " Try going back or reset position.");
                    
                System.out.println("HIT DEAD END (LEFT) at node " + currentNode.val);
            }
        }
        
        updateButtonState();
    }

    private void moveRight() {
        guessCount++; // Always increment guess count for any attempt
        guessLabel.setText("Guesses: " + guessCount);
        
        // Save current position in history before moving
        navigationHistory.push(currentNode);
        
        if (currentNode.right != null) {
            currentNode = currentNode.right;
            updateCurrentNodeLabel();
            checkTargetFound();
            
            // Print current tree state with player position
            System.out.println("Moved RIGHT to node " + currentNode.val);
            printTree();
        } else {
            // We push and then immediately pop since we didn't actually move
            navigationHistory.pop();
            
            // Check if current node is the target when hitting a deadend
            if (currentNode.val == target) {
                winGame();
            } else {
                feedbackLabel.setText("❌ No right child! Dead end. Target is " + 
                    (target < currentNode.val ? "smaller" : "greater") + " than current node." +
                    " Try going back or reset position.");
                    
                System.out.println("HIT DEAD END (RIGHT) at node " + currentNode.val);
            }
        }
        
        updateButtonState();
    }
    
    private void goBack() {
        if (!navigationHistory.isEmpty()) {
            TreeNode previousNode = navigationHistory.pop();
            System.out.println("Going back from node " + currentNode.val + " to node " + previousNode.val);
            
            currentNode = previousNode;
            updateCurrentNodeLabel();
            feedbackLabel.setText("Moved back to previous node. Target is " + 
                (target < currentNode.val ? "smaller" : "greater") + " than current node.");
            updateButtonState();
            
            printTree();
        } else {
            feedbackLabel.setText("You're at the root node already.");
        }
    }
    
    private void resetPosition() {
        // Clear navigation history and return to root
        System.out.println("Resetting position from node " + currentNode.val + " to root node " + bstRoot.val);
        
        navigationHistory.clear();
        currentNode = bstRoot;
        updateCurrentNodeLabel();
        feedbackLabel.setText("Position reset to root node. Target is " + 
            (target < currentNode.val ? "smaller" : "greater") + " than current node.");
        updateButtonState();
        
        printTree();
    }
    
    private void updateButtonState() {
        // Update button enabled/disabled state based on current node
        leftButton.setDisable(currentNode.left == null);
        rightButton.setDisable(currentNode.right == null);
        backButton.setDisable(navigationHistory.isEmpty());
    }

    private void checkTargetFound() {
        if (currentNode.val == target) {
            winGame();
        } else if (target < currentNode.val) {
            feedbackLabel.setText("Target is smaller. Try going left.");
        } else {
            feedbackLabel.setText("Target is greater. Try going right.");
        }
    }

    private void winGame() {
        System.out.println("TARGET FOUND! Node value: " + currentNode.val + " in " + guessCount + " steps.");
        
        feedbackLabel.setText("✅ Correct! You found the target.");
        leftButton.setDisable(true);
        rightButton.setDisable(true);
        backButton.setDisable(true);
        resetPositionButton.setDisable(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You Win!");
        alert.setHeaderText("Well done!");
        alert.setContentText("You found the number " + target + " in " + guessCount + " steps.");
        alert.showAndWait();
    }

    private void updateCurrentNodeLabel() {
        currentNodeLabel.setText("Current Node: " + currentNode.val);
    }

    private void resetGame() {
        System.out.println("\n==== STARTING NEW GAME ====\n");
        
        // Clear navigation history
        navigationHistory.clear();
        
        // Reset game state
        generateBST();
        currentNode = bstRoot;
        guessCount = 0;
        
        // Update UI
        updateCurrentNodeLabel();
        targetLabel.setText("Target Number: " + target);
        guessLabel.setText("Guesses: 0");
        
        // Check if the root is already the target
        if (currentNode.val == target) {
            feedbackLabel.setText("The root node is the target! Lucky start!");
        } else {
            feedbackLabel.setText("New round started. Navigate to find the number!");
        }
        
        // Enable buttons
        leftButton.setDisable(false);
        rightButton.setDisable(false);
        backButton.setDisable(true); // Disabled at start since we're at root
        resetPositionButton.setDisable(false);
        
        // Print the new tree
        printTree();
    }
    

    private boolean printPathToTarget(TreeNode node, List<Integer> path) {
        if (node == null) return false;
        
        // Add current node to path
        path.add(node.val);
        
        // Check if current node is target
        if (node.val == target) {
            System.out.print("Root");
            for (int i = 0; i < path.size(); i++) {
                System.out.print(" -> " + path.get(i));
            }
            System.out.println();
            return true;
        }
        
        // Check if target is in left or right subtree
        boolean found = false;
        if (target < node.val) {
            found = printPathToTarget(node.left, path);
        } else {
            found = printPathToTarget(node.right, path);
        }
        
        // Remove current node from path if target not found in this subtree
        if (!found) {
            path.remove(path.size() - 1);
        }
        
        return found;
    }
    



    /**
     * Prints a visual representation of the BST to the console
     */
    private void printTree() {
        System.out.println("\n==== Binary Search Tree Structure ====");
        
        // Build a string representation of the tree structure
        StringBuilder treeBuilder = new StringBuilder();
        buildTreeString(bstRoot, "", true, treeBuilder);
        
        // Print the tree
        System.out.println(treeBuilder.toString());
        
        System.out.println("====================================");
        
        // Also print a path to the target for reference
        System.out.println("Path to target " + target + ":");
        printPathToTarget(bstRoot, new ArrayList<>());
        System.out.println();
    }

    /**
     * Builds a string representation of the tree for debugging
     */
    private void buildTreeString(TreeNode node, String prefix, boolean isLeft, StringBuilder builder) {
        if (node == null) return;
        
        // Process right child first (so it appears at the top)
        buildTreeString(node.right, prefix + (isLeft ? "│   " : "    "), false, builder);
        
        // Add current node
        builder.append(prefix);
        builder.append(isLeft ? "└── " : "┌── ");
        
        // Add node value and any markers
        String nodeText = String.valueOf(node.val);
        if (node.val == target) {
            nodeText += " (TARGET)";
        }
        if (node == currentNode) {
            nodeText += " <-- CURRENT";
        }
        builder.append(nodeText).append("\n");
        
        // Process left child
        buildTreeString(node.left, prefix + (isLeft ? "    " : "│   "), true, builder);
    }

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }
}