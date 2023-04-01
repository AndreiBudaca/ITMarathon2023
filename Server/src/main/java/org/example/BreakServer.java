package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BreakServer {
    private ServerSocket serverSocket;

    public BreakServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void startServer() {

        while (true) {
            try {
                // Listen for new clients
                Socket newClient = serverSocket.accept();

                System.out.println("New client: " + newClient.getLocalAddress());
                // Start new thread to handle the client
                new ClientThread(newClient).start();
            }
            catch (Exception ignored){
                System.out.println("Client failed to connect");
            }
        }
    }
}
