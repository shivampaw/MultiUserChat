package com.shivampaw.chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MainController {
    @FXML
    private void joinServer() throws IOException {
        Main.parentWindow.getScene().setRoot(FXMLLoader.load(getClass().getResource("views/JoinServer.fxml")));
    }

    @FXML
    private void createServer() throws IOException {
        Main.parentWindow.getScene().setRoot(FXMLLoader.load(getClass().getResource("views/CreateServer.fxml")));
    }
}
