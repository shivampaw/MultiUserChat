package com.shivampaw.chat;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length == 1 && args[0].equals("headless")) {
            System.out.println("Starting headless server");
            new HeadlessServerMain();
        } else {
            JavaFXMain.main(new String[]{});
        }
    }
}
