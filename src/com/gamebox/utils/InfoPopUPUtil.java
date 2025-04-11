package com.gamebox.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.util.Optional;

public class InfoPopUPUtil {

    public static Optional<ButtonType> showGameBoxInfo(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("About GameBox");

        Label label = new Label(message);
        label.setWrapText(true);
        label.setMaxWidth(400);
        label.setMinHeight(Region.USE_PREF_SIZE);

        alert.getDialogPane().setContent(label);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        return alert.showAndWait();  // Return the result for caller to decide
    }
}
