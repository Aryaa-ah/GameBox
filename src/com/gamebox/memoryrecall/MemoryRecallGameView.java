package com.gamebox.memoryrecall;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MemoryRecallGameView {
    private final Stage stage;

    public MemoryRecallGameView(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamebox/memoryrecall/MemoryRecallGameView.fxml"));
            Parent root = loader.load();

            MemoryRecallGameController controller = loader.getController();
            controller.setStage(stage);

            return new Scene(root, 600, 400);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
