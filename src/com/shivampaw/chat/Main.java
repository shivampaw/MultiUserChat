package com.shivampaw.chat;

import com.shivampaw.chat.server.HeadlessServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Main extends Application {
    public static Stage parentWindow;

    @Override
    public void start(Stage primaryStage) throws Exception {
        parentWindow = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("views/MainWindow.fxml"));
        primaryStage.setTitle("Multi User Chat");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        if(GraphicsEnvironment.isHeadless()) {
            try {
                new HeadlessServer();
            } catch (IOException e) {
                System.err.println("Could not start server.");
                e.printStackTrace();
            }
        } else {
            launch(args);
        }
    }
}