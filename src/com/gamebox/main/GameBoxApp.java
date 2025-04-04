package com.gamebox.main;

import com.gamebox.ui.MainMenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameBoxApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainMenuView menuView = new MainMenuView(primaryStage);
        primaryStage.setTitle("GameBox - Alzheimer Support");
        primaryStage.setScene(new Scene(menuView.getView(), 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
