package com.shivampaw.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;

public class ServerMain {
    public static ArrayList<ClientHandler> clients = new ArrayList<>();

    public ServerMain() throws IOException {
        ServerSocket ss = new ServerSocket(0);

        URL IPChecker = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(IPChecker.openStream()));
        String ip = in.readLine();

        System.out.println("Server started on IP " + ip + " and port " + ss.getLocalPort());
        System.out.println("Ensure you have port forwarding enabled if you want your server available globally.");

        while(true) {
            new Thread(new ClientHandler(ss.accept())).start();
        }

    }
}
