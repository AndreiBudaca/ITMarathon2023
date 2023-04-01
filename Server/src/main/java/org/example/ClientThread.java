package org.example;

import org.example.DB.PersonDB;
import org.example.DB.RequestDB;
import org.example.Model.Person;
import org.example.Parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket clientSocket;
    private PrintWriter out;
    private final PersonDB personDatabase;
    private final RequestDB requestDatabase;


    public ClientThread(Socket clientSocket){
        this.clientSocket = clientSocket;

        personDatabase = new PersonDB();
        requestDatabase = new RequestDB();

        personDatabase.load();
        requestDatabase.load();
    }

    private void sendData(String dataType, String body){
        try{
            if (dataType.equals("ID")){
                out.print(personDatabase.getPersonId(body));
            }

            if (dataType.equals("PERSONS")){
                out.print(JSONParser.writeObject(personDatabase.getPersonList()));
            }

            if (dataType.equals("PERSON")){
                out.print(JSONParser.writeObject(personDatabase.getPerson(Integer.parseInt(body))));
            }

            if (dataType.equals("REQUESTS")){
                out.print(JSONParser.writeObject(requestDatabase.getRequestsContainingId(Integer.parseInt(body))));
            }

            if (dataType.equals("NAME")){
                out.print(personDatabase.getPersonId(body.split(" ")[0], body.split(" ")[1]));
            }

        }catch (Exception e){
            out.print("error");
        }
    }

    private void putData(String rawData){
        try {
            requestDatabase.addRequest(JSONParser.readRequest(rawData));
        }
        catch (Exception e){
            out.print("error");
        }
    }

    private void modifyData(String dataType, String body){
        try{
            if (dataType.equals("USER")){
                int id = Integer.parseInt(body.split(" ")[0]);
                Person newPerson = JSONParser.readPerson(body.split(" ")[1]);
            }
            if (dataType.equals("STATUS")){
                int requestId = Integer.parseInt(body.split(" ")[0]);
                int personId = Integer.parseInt(body.split(" ")[1]);
                int newStatus = Integer.parseInt(body.split(" ")[3]);

                requestDatabase.setNewStatus(requestId, personId, newStatus);
            }

        }catch (Exception e){
            out.print("error");
        }
    }

    @Override
    public void run(){

        try {
            // Create input / output stream
            out = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //Logic
            String messageHeader = in.readLine();
            String messageBody = in.readLine();

            // Get request
            if (messageHeader.split(" ")[0].equals("GET")){
                sendData(messageHeader.split(" ")[1], messageBody);
            }

            // Put request
            if (messageHeader.split(" ")[0].equals("PUT")){
                putData(messageBody);
            }

            // Update request
            if (messageHeader.split(" ")[0].equals("UPDATE")){
                putData(messageBody);
            }

            // Close stream
            out.flush();
            out.close();
            in.close();
            clientSocket.close();
        }
        catch (Exception ignored){
        }
    }

}
