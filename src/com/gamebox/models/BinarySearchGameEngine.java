package com.gamebox.models;

import java.util.*;

public class BinarySearchGameEngine {

    public static class TreeNode {
        public int val;
        public TreeNode left, right;
        public TreeNode(int val) { this.val = val; }
    }

    private TreeNode bstRoot;
    private TreeNode currentNode;
    private int target;
    private int guessCount;
    private Stack<TreeNode> navigationHistory = new Stack<>();
    private List<Integer> numbers;

    public void generateNewGame() {
        navigationHistory.clear();
        Set<Integer> uniqueNumbers = new HashSet<>();
        Random rand = new Random();

        while (uniqueNumbers.size() < 10) {
            uniqueNumbers.add(rand.nextInt(90) + 10);
        }
        numbers = new ArrayList<>(uniqueNumbers);
        target = numbers.get(rand.nextInt(numbers.size()));

        bstRoot = null;
        for (int num : numbers) {
            bstRoot = insertBST(bstRoot, num);
        }

        currentNode = bstRoot;
        guessCount = 0;
    }

    private TreeNode insertBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val) root.left = insertBST(root.left, val);
        else if (val > root.val) root.right = insertBST(root.right, val);
        return root;
    }

    public boolean canMoveLeft() {
        return currentNode.left != null;
    }

    public boolean canMoveRight() {
        return currentNode.right != null;
    }

    public boolean canGoBack() {
        return !navigationHistory.isEmpty();
    }

    public String moveLeft() {
        guessCount++;
        navigationHistory.push(currentNode);

        if (currentNode.left != null) {
            currentNode = currentNode.left;
            return checkTarget();
        } else {
            navigationHistory.pop(); // invalid move
            return "❌ No left child! Dead end. Use Back or Reset.";
        }
    }

    public String moveRight() {
        guessCount++;
        navigationHistory.push(currentNode);

        if (currentNode.right != null) {
            currentNode = currentNode.right;
            return checkTarget();
        } else {
            navigationHistory.pop();
            return "❌ No right child! Dead end. Use Back or Reset.";
        }
    }

    public String goBack() {
        if (canGoBack()) {
            currentNode = navigationHistory.pop();
            return "Moved back. Hint: " + (target < currentNode.val ? "Try left." : "Try right.");
        }
        return "You're already at the root.";
    }

    public void resetPosition() {
        currentNode = bstRoot;
        navigationHistory.clear();
    }

    private String checkTarget() {
        if (currentNode.val == target) {
            return "✅ Found the target " + target + " in " + guessCount + " steps!";
        }
        return "Hint: " + (target < currentNode.val ? "Try left." : "Try right.");
    }

    public int getCurrentValue() {
        return currentNode.val;
    }

    public int getGuessCount() {
        return guessCount;
    }

    public int getTarget() {
        return target;
    }

    public TreeNode getRootNode() {
        return bstRoot;
    }

    public TreeNode getCurrentNode() {
        return currentNode;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}
