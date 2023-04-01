package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;


    public ClientThread(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run(){

        try {
            // Create input / output stream
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Do logic
            out.println("Bau");
            out.flush();

            // Close stream
            out.close();
            in.close();
            clientSocket.close();
        }
        catch (Exception ignored){
        }
    }

}
