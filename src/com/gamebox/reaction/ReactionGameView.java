package com.gamebox.reaction;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ReactionGameView {
    private final Stage stage;

    public ReactionGameView(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamebox/reaction/ReactionGameView.fxml"));
            Parent root = loader.load();

            ReactionGameController controller = loader.getController();
            controller.setStage(stage);

            return new Scene(root, 600, 400);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
