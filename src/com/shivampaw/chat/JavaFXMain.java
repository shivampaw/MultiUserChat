package com.shivampaw.chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXMain extends Application {
    public static Stage parentWindow;

    @Override
    public void start(Stage primaryStage) throws IOException {
        parentWindow = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("views/MainWindow.fxml"));
        primaryStage.setTitle("Multi User Chat");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    public static void main() {
        launch();
    }
}