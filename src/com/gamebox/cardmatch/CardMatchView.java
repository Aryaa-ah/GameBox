package com.gamebox.cardmatch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

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
            
            boolean useTimer = askTimer(); // Ask timer choice
            controller.setStage(stage, useTimer); // Pass both args
            
            return new Scene(root, 800, 600); // Increased for better UI
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean askTimer() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Enable Timer?");
        alert.setHeaderText("Would you like to enable the timer for this game?");
        ButtonType yes = new ButtonType("✅ Yes");
        ButtonType no = new ButtonType("🚫 No");
        alert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yes;
    }
}
