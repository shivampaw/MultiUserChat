package com.shivampaw.chat;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length > 0 && args[0].equals("headless")) {
            System.out.println("Starting headless server");
            int port = Integer.parseInt(args[1]);
            new HeadlessServerMain(port);
        } else {
            JavaFXMain.main(new String[]{});
        }
    }
}
