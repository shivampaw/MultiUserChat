package com.shivampaw.chat.client;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class HandleIncomingServerCommunication implements Runnable {
    private Socket socket;
    private JoinServer client;

    public HandleIncomingServerCommunication(Socket s, JoinServer client) {
        this.socket = s;
        this.client = client;
    }

    @Override
    public void run() {
        try (DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            while (JoinServer.running) {
                System.out.println(dis.readUTF());
            }
        } catch (EOFException e) {
            System.out.println("The chat server has been shutdown.");
            client.toggleConnection();
        } catch (IOException e) {
            System.err.println("Could not connect to the chat server.");
        } catch (NullPointerException e) {
            System.err.println("Chat server not available!");
        }
    }

}
