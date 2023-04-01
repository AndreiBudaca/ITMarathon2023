package com.example.biggapp.DB;

import com.example.biggapp.Model.Request;
import com.example.biggapp.Parser.JSONParser;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RequestDB {
    private final String fileName = "requests.json";
    private List<Request> requestList = new ArrayList<>(0);
    private int nextId = 0;


    public void addRequest(Request request){
        request.setId(nextId);
        ++nextId;
        System.out.println("Saving...");
        requestList.add(request);
        save();
        System.out.println("Saved on disk");
    }

    public void deleteRequest(int id){

        for (Request request: requestList){
            if (request.getId() == id){
                requestList.remove(request);
                break;
            }
        }
    }

    public void save(){
        try {
            FileWriter f = new FileWriter(fileName);
            f.write(JSONParser.writeObject(requestList));
            f.flush();
        }
        catch (Exception e){
            System.out.println("Failed to save DB!");
        }
    }

    public void load(){
        try{
            String content = Files.readString(Paths.get(fileName), StandardCharsets.US_ASCII);
            requestList = JSONParser.readRequests(content);

        } catch (Exception e) {
            System.out.println("Failed to read DB!\n" + e.getLocalizedMessage());
        }
    }
}
