package com.shivampaw.chat.client;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class MessageHandler implements Runnable {
    private Socket socket;

    public MessageHandler(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        try (DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            while (true) {
                System.out.println(dis.readUTF());
            }
        } catch (EOFException e) {
            System.out.println("Server closed connection.");
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Something went wrong...exiting");
            System.exit(0);
        }
    }

}
