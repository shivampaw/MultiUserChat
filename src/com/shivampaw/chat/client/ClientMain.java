package com.shivampaw.chat.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {

    public ClientMain() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a host\t");
        String host = scanner.next();
        System.out.print("Enter a port\t");
        int port = scanner.nextInt();
        System.out.print("Enter your nickname\t");
        String name = scanner.next();

        Socket s = new Socket(host, port);
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        dos.writeUTF(name);

        System.out.println("To quit send a message saying \"/quit\"");

        new Thread(new MessageHandler(s)).start();

        while(true) {
            String message = scanner.nextLine();
            dos.writeUTF(message);
            if(message.equals("/quit"))
                System.exit(0);
        }
    }
}
