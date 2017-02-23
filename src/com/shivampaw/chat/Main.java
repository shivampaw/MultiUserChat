package com.shivampaw.chat;

import com.shivampaw.chat.server.CreateServer;

import java.io.IOException;

public class Main {
    public static boolean isHeadless = false;

    public static void main(String[] args) throws IOException {
        if(args.length > 0 && args[0].equals("headless")) {
            isHeadless = true;
            System.out.println("Starting headless server");
            int port = 0;
            if(args.length == 2) {
                try {
                    port = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    port = 0;
                }
            }
            new CreateServer(port);
        } else {
            JavaFXMain.main();
        }
    }
}
