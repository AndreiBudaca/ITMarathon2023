package com.example.biggapp.ServerCommunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ClientSocket {

    private final int serverPort = 12345;
    private final String serverIP = "127.0.0.1";
    private Socket commSocket;
    private PrintWriter out;
    private BufferedReader in;

    private void sendMessage(String header, String body){
        out.print(header);
        out.print("\r\n");
        out.print(body);

        out.flush();
    }

    private String readMessage() throws IOException {
        return in.readLine();
    }

    public void test(){
        sendMessage("Hello server", "");

        try {
            System.out.println(readMessage());
        }
        catch (Exception e){
            System.out.println("Error reading from server");
        }

    }

    public ClientSocket() throws IOException {
        commSocket = new Socket(serverIP, serverPort);
        out = new PrintWriter(commSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(commSocket.getInputStream()));
    }

}
