package com.gamebox.alphabettrain;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AlphabetTrainView {
    private final Stage stage;

    public AlphabetTrainView(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamebox/alphabettrain/AlphabetTrainView.fxml"));
            Parent root = loader.load();
            AlphabetTrainGameController controller = loader.getController();
            controller.setStage(stage);
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/com/gamebox/css/alphabettrain.css").toExternalForm());
            return scene;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}