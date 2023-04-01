package org.example;

import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) {
        try {
            BreakServer server = new BreakServer(12345);
            server.startServer();
        }
        catch (Exception e){
            System.exit(-1);
        }
    }
}