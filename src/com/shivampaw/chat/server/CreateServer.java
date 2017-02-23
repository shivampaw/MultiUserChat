package com.shivampaw.chat.server;

import com.shivampaw.chat.Main;
import com.shivampaw.chat.utils.Console;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;

public class CreateServer {
    @FXML
    private TextArea console;

    public static ArrayList<HandleIncomingClientCommunication> clients = new ArrayList<>();
    private boolean running = true;
    private ServerSocket ss;
    private int port;

    // This is required to support JavaFX starting.
    public CreateServer() {
        this.port = 0;
    }

    public CreateServer(int port) {
        this.port = port;
    }

    public void initialize() throws IOException {
        if(!Main.isHeadless) {
            PrintStream ps = new PrintStream(new Console(console));
            System.setOut(ps);
            System.setErr(ps);
        }

        try {
            ss = new ServerSocket(this.port);
        } catch (BindException e) {
            System.err.println("Desired port was unavailable");
            ss = new ServerSocket(0);
        }

        URL IPChecker = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(IPChecker.openStream()));
        String ip = in.readLine();

        System.out.println("Server started on IP " + ip + " and port " + ss.getLocalPort());
        System.out.println("Ensure you have port forwarding enabled if you want your server available globally.");

        Thread t = new Thread(() -> {
            while(running) {
                try {
                    Thread t1 = new Thread(new HandleIncomingClientCommunication(ss.accept()));
                    t1.setDaemon(true);
                    t1.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }


    @FXML
    private void stopServer() {
        System.out.println("Stopping server...");
        running = false;
        try {
            ss.close();
        } catch (IOException e) {
            System.exit(0);
        }
        Platform.exit();
    }
}
