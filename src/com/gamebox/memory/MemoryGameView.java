package com.gamebox.memory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class MemoryGameView {
    private final Stage stage;

    public MemoryGameView(Stage stage) {
        this.stage = stage;
    }

    public Scene getStartupScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MemoryGameView.fxml"));
            Parent root = loader.load();

            MemoryGameController controller = loader.getController();
            controller.setStage(stage, askTimerMode());

            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/com/gamebox/ui/style.css").toExternalForm());
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean askTimerMode() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Start With Timer?");
        alert.setHeaderText("Do you want to play with a timer?");
        alert.setContentText("Choose your game mode.");

        ButtonType yes = new ButtonType("✅ Yes");
        ButtonType no = new ButtonType("🚫 No");

        alert.getButtonTypes().setAll(yes, no);
        return alert.showAndWait().orElse(no) == yes;
    }
}
