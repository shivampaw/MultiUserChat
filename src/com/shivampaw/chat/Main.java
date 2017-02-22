package com.shivampaw.chat;

import com.shivampaw.chat.client.ClientMain;
import com.shivampaw.chat.server.ServerMain;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length == 1) {
            switch(args[0]) {
                case "server":
                    new ServerMain();
                    break;
                case "client":
                    new ClientMain();
                    break;
                default:
                    System.out.println("You need to start with either a server or client parameter.");
            }
        } else {
            System.out.println("You need to start with either a server or client parameter.");
        }
    }
}