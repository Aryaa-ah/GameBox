package com.gamebox.binarysearch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BinarySearchView {
    private final Stage stage;

    public BinarySearchView(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamebox/binarysearch/BinarySearchView.fxml"));
            Parent root = loader.load();

            // Get the controller and set the stage for navigation
            BinarySearchController controller = loader.getController();
            controller.setStage(stage);

            Scene scene = new Scene(root, 600, 500);
            scene.getStylesheets().add(getClass().getResource("/com/gamebox/css/bst.css").toExternalForm());

            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
