package com.example.biggapp.ServerCommunication;

import com.example.biggapp.Model.Person;
import com.example.biggapp.Model.Request;
import com.example.biggapp.Parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ClientSocket {

    private final int serverPort = 12345;
    private final String serverIP = "127.0.0.1";
    private Socket commSocket;
    private PrintWriter out;
    private BufferedReader in;

    private void sendMessage(String header, String body){
        out.print(header);
        out.print("\n");
        out.print(body);
        out.print("\n");
        out.flush();
    }

    private String readMessage() throws IOException {
        return in.readLine();
    }

    public int getId(String username){
        String header = "GET ID";

        sendMessage(header, username);

        try {
            return Integer.parseInt(readMessage());
        }
        catch (Exception e){
            return -1;
        }
    }

    public List<Person> getAllUsers(){
        String header = "GET PERSONS";
        String body = "";

        sendMessage(header, body);

        try{
            return JSONParser.readPersons(readMessage());
        }
        catch (Exception e){
            return new ArrayList<>(0);
        }
    }

    public List<Request> getRequests(int userID){
        String header = "GET REQUESTS";
        String body = Integer.toString(userID);

        sendMessage(header, body);

        try{
            return JSONParser.readRequests(readMessage());
        }
        catch (Exception e){
            return new ArrayList<>(0);
        }
    }

    public Person getPerson(int personID){
        String header = "GET PERSON";
        String body = Integer.toString(personID);

        sendMessage(header, body);

        try{
            return JSONParser.readPerson(readMessage());
        }
        catch (Exception e){
            return new Person("", "", "", "", "", -1, "");
        }
    }

    public int getPersonID(String firstName, String lastName){
        String header = "GET PERSON";
        String body = firstName + " " + lastName;

        sendMessage(header, body);

        try{
            return Integer.parseInt(readMessage());
        }
        catch (Exception e){
            return -1;
        }
    }

    public void putReqest(Request request){
        String header = "PUT";
        String body = JSONParser.writeObject(request);
        sendMessage(header, body);
    }

    public void updateUser(int userId, Person newUser){
        String header = "UPDATE USER";
        String body = userId + " " + JSONParser.writeObject(newUser);
        sendMessage(header, body);
    }

    public void updateStatus(int requestId, int personId, int status){
        String header = "UPDATE STATUS";
        String body = requestId + " " + personId + " " + status;
        sendMessage(header, body);
    }


    public void test(){
        System.out.println(getId("asd"));

    }

    public ClientSocket() throws IOException {
        commSocket = new Socket(serverIP, serverPort);
        out = new PrintWriter(commSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(commSocket.getInputStream()));
    }

}
