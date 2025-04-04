package com.gamebox.pathfinder;

import java.util.*;

public class PathFinderGame {
    private final int ROWS = 5;
    private final int COLS = 5;
    private final int[][] grid = new int[ROWS][COLS];

    public PathFinderGame() {
        generateObstacles();
    }

    private void generateObstacles() {
        Random rand = new Random();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = rand.nextDouble() < 0.2 ? -1 : 0; // -1 is obstacle
            }
        }
        grid[0][0] = 0;
        grid[ROWS - 1][COLS - 1] = 0;
    }

    public int[][] getGrid() {
        return grid;
    }

    public boolean isBlocked(int r, int c) {
        return grid[r][c] == -1;
    }

    public static class Node {
        int row, col;
        Node parent;

        public Node(int row, int col, Node parent) {
            this.row = row;
            this.col = col;
            this.parent = parent;
        }
    }

    public List<int[]> findShortestPathBFS() {
        boolean[][] visited = new boolean[ROWS][COLS];
        Queue<Node> queue = new LinkedList<>();

        queue.add(new Node(0, 0, null));
        visited[0][0] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        Node endNode = null;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int r = current.row;
            int c = current.col;

            if (r == ROWS - 1 && c == COLS - 1) {
                endNode = current;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nc >= 0 && nr < ROWS && nc < COLS && !visited[nr][nc] && grid[nr][nc] != -1) {
                    visited[nr][nc] = true;
                    queue.add(new Node(nr, nc, current));
                }
            }
        }

        List<int[]> path = new ArrayList<>();
        while (endNode != null) {
            path.add(new int[]{endNode.row, endNode.col});
            endNode = endNode.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
