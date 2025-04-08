package com.gamebox.pathfinder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PathFinderView {
    private final Stage stage;

    public PathFinderView(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamebox/pathfinder/PathFinderView.fxml"));
            Parent root = loader.load();

            PathFinderController controller = loader.getController();
            controller.setStage(stage);

            return new Scene(root, 800, 600);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}