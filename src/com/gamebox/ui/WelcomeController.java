package com.gamebox.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WelcomeController {
    @FXML private Button startButton;

    public void initialize() {
        startButton.setOnAction(e -> {
            Stage stage = (Stage) startButton.getScene().getWindow();
            MainMenuView menu = new MainMenuView(stage);
            stage.setScene(menu.getScene());
        });
    }
}