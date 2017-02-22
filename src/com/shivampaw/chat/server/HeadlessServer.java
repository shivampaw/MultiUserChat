package com.shivampaw.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URL;

public class HeadlessServer {
    private boolean running = true;
    private ServerSocket ss;

    public HeadlessServer() throws IOException {
        ss = new ServerSocket(0);

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
}
