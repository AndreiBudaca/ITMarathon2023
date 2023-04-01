package org.example;

import org.example.Model.Person;
import org.example.Model.PersonDB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket clientSocket;
    private PersonDB database;


    public ClientThread(Socket clientSocket, PersonDB database){
        this.clientSocket = clientSocket;
        this.database = database;
    }

    @Override
    public void run(){

        try {
            // Create input / output stream
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Do logic
            System.out.println(in.readLine());
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
