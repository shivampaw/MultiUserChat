package com.shivampaw.chat.server;

import com.shivampaw.chat.Main;
import com.shivampaw.chat.utils.Console;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;

public class CreateServer {
    @FXML
    public Button startBtn;
    @FXML
    private TextArea console;
    @FXML
    private TextField port;
    @FXML
    private Button stopBtn;
    @FXML
    private Label serverStatus;

    public static ArrayList<HandleIncomingClientCommunication> clients = new ArrayList<>();
    private boolean running = false;
    private ServerSocket ss;
    private int serverPort;

    // Required for JavaFX
    public CreateServer() {}

    public CreateServer(int port) throws IOException {
        this.serverPort = port;
        running = true;
        startServer();
    }

    public void initialize() {
        if(!Main.isHeadless) {
            PrintStream ps = new PrintStream(new Console(console));
            System.setOut(ps);
            System.setErr(ps);
        }
    }

    @FXML
    private void startServer() throws IOException {
        if(!Main.isHeadless)
            try {
                this.serverPort = Integer.parseInt(port.getText());
            } catch (NumberFormatException e) {
                this.serverPort = 0;
            }

        try {
            ss = new ServerSocket(this.serverPort);
        } catch (BindException e) {
            System.err.println("Desired port was unavailable. Choosing a random available one.");
            ss = new ServerSocket(0);
        }

        URL IPChecker = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(IPChecker.openStream()));
        String ip = in.readLine();
        in.close();

        String status = "Server started on IP " + ip + " and port " + ss.getLocalPort();
        System.out.println(status);
        System.out.println("Ensure you have port forwarding enabled if you want your server available globally.\n");

        if(!Main.isHeadless) {
            this.serverStatus.setText(status);
            toggleRunning();
        }

        Thread t = new Thread(() -> {
            while(running) {
                try {
                    Thread t1 = new Thread(new HandleIncomingClientCommunication(ss.accept()));
                    t1.setDaemon(true);
                    t1.start();
                } catch (SocketException e) {
                    System.out.println("Server stopped.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    private void toggleRunning() {
        running = !running;
        if(!running) {
            this.startBtn.setDisable(false);
            this.stopBtn.setDisable(true);
            this.port.setDisable(false);
        } else {
            this.startBtn.setDisable(true);
            this.stopBtn.setDisable(false);
            this.port.setDisable(true);
        }
    }

    @FXML
    private void stopServer() {
        System.out.println("Stopping server...");
        toggleRunning();
        this.serverStatus.setText("Enter the desired port to host the chat server.");
        try {
            ss.close();
        } catch (IOException e) {
            System.exit(0);
        }
        Platform.exit();
    }
}
