package org.example;

import org.example.DB.PersonDB;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BreakServer {
    private ServerSocket serverSocket;
    private PersonDB database;

    public BreakServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        database = new PersonDB();
        database.load();
    }

    public void startServer() {

        while (true) {
            try {
                // Listen for new clients
                Socket newClient = serverSocket.accept();

                System.out.println("New client: " + newClient.getLocalAddress());
                // Start new thread to handle the client
                new ClientThread(newClient, database).start();
            }
            catch (Exception ignored){
                System.out.println("Client failed to connect");
            }
        }
    }
}
