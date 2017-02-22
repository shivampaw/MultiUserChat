package com.shivampaw.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private String name;
    private DataInputStream dis;

    public ClientHandler(Socket s) throws IOException {
        this.socket = s;
        this.dis = new DataInputStream(this.socket.getInputStream());
        this.name = dis.readUTF();
        ServerMain.clients.add(this);

        writeToAllClients(this.name + " has joined.");
    }

    @Override
    public void run() {
        try {
            while (true) {
                String receivedInput = dis.readUTF();

                if(receivedInput.equals("")) continue;
                if(receivedInput.equals("/quit")) throw new EOFException();

                String message = "[" + this.name + "]\t\t" + receivedInput;
                writeToAllClients(message);
            }
        } catch (EOFException e) {
            writeToAllClients(this.name + " has left.");
            ServerMain.clients.remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToAllClients(String message) {
        for (ClientHandler handler : ServerMain.clients) {
            if(handler.equals(this))
                continue;
            try {
                DataOutputStream dos = new DataOutputStream(handler.socket.getOutputStream());
                dos.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
