package com.shivampaw.chat.client;

import com.shivampaw.chat.utils.Console;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class JoinServer {
    @FXML
    private TextField message;
    @FXML
    private TextArea console;
    @FXML
    private TextField host;
    @FXML
    private TextField port;
    @FXML
    private Button connectBtn;
    @FXML
    private TextField nickname;
    @FXML
    private Label connectionStatus;

    private Socket socket;
    private DataOutputStream dos;
    private boolean connected = false;

    public static boolean running = true;

    public void initialize() throws IOException {
        PrintStream ps = new PrintStream(new Console(console));
        System.setOut(ps);
        System.setErr(ps);

        this.message.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER) && !this.message.getText().equals("")) {
                try {
                    this.dos.writeUTF(this.message.getText());
                    this.message.setText("");
                } catch (IOException e) {
                    this.connectionStatus.setText("Error sending your message. Connection has been closed.");
                    toggleConnection();
                }
            }
        });
    }

    @FXML
    private void connect() {
        String host = this.host.getText();
        int port = Integer.parseInt(this.port.getText());
        String nickname = this.nickname.getText();

        try {
            this.socket = new Socket(host, port);
            this.dos = new DataOutputStream(this.socket.getOutputStream());
            this.dos.writeUTF(nickname);
            toggleConnection();
            connectionStatus.setText("Connected to chat on " + host + ":" + port);
            this.console.clear();
        } catch (IOException e) {
            connectionStatus.setText("Could not connect to chat server.");
        }

        Thread t = new Thread(new HandleIncomingServerCommunication(this.socket, this));
        t.setDaemon(true);
        t.start();
    }

    public void toggleConnection() {
        connected = !connected;
        if(!connected) {
            this.message.setDisable(true);
            this.host.setDisable(false);
            this.port.setDisable(false);
            this.nickname.setDisable(false);
            this.connectBtn.setDisable(false);
        } else {
            this.message.setDisable(false);
            this.host.setDisable(true);
            this.port.setDisable(true);
            this.nickname.setDisable(true);
            this.connectBtn.setDisable(true);
        }
    }

    @FXML
    private void exitChat() {
        running = false;
        Platform.exit();
    }

}
