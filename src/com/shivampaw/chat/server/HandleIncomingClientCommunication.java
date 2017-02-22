package com.shivampaw.chat.server;

import com.sun.corba.se.impl.activation.ServerMain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class HandleIncomingClientCommunication implements Runnable {
    private Socket socket;
    private String name;
    private DataInputStream dis;

    public HandleIncomingClientCommunication(Socket s) throws IOException {
        this.socket = s;
        this.dis = new DataInputStream(this.socket.getInputStream());
        this.name = dis.readUTF();
        CreateServer.clients.add(this);

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
            CreateServer.clients.remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToAllClients(String message) {
        for (HandleIncomingClientCommunication handler : CreateServer.clients) {
            try {
                DataOutputStream dos = new DataOutputStream(handler.socket.getOutputStream());
                dos.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(message);
    }
}
