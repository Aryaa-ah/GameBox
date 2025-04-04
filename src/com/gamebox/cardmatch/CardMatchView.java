package com.gamebox.cardmatch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CardMatchView {
    private final Stage stage;

    public CardMatchView(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gamebox/cardmatch/CardMatchView.fxml"));
            Parent root = loader.load();
            CardMatchController controller = loader.getController();
            controller.setStage(stage);
            return new Scene(root, 400, 400);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}