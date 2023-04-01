package org.example.DB;

import org.example.Model.Request;
import org.example.Model.RequestPerson;
import org.example.Parser.JSONParser;

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

    public List<Request> getRequestsContainingId(int id){

        List<Request> containingList = new ArrayList<>(0);

        for (Request request: requestList){

            boolean contains = request.getSenderId() == id;

            if (!contains){
                for (RequestPerson reqPerson: request.getReceivers()) {
                    if (reqPerson.getId() == id) {
                        contains = true;
                        break;
                    }
                }
            }

            if (contains)
                containingList.add(request);
        }

        return containingList;
    }

    public void setNewStatus(int requestId, int receiverId, int newStatus){
        for (Request request: requestList){
            if (request.getId() == requestId){
                for (RequestPerson receiver: request.getReceivers()){
                    if (receiver.getId() == receiverId)
                        receiver.setStatus(newStatus);
                }
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
            System.out.println("Failed to read DB!\n" + e.getLocalizedMessage() + "\n");
        }
    }
}
