package org.example.Parser;

import com.google.gson.Gson;
import org.example.Model.Person;
import org.example.Model.Request;
import org.example.Model.RequestPerson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    private static final Gson gson = new Gson();

    public static List<Person> readPersons(String rawJSON){
        JSONObject responseJSONObj = new JSONObject("{\"data\":" + rawJSON + "}");
        JSONArray persons = responseJSONObj.getJSONArray("data");
        List<Person> personList = new ArrayList<>(0);

        for (int i = 0; i < persons.length(); ++i){
            personList.add(new Person(
                    persons.getJSONObject(i).getInt("id"),
                    persons.getJSONObject(i).getString("firstName"),
                    persons.getJSONObject(i).getString("lastName"),
                    persons.getJSONObject(i).getString("department"),
                    persons.getJSONObject(i).getString("officeName"),
                    persons.getJSONObject(i).getString("teamName"),
                    persons.getJSONObject(i).getInt("floorNumber"),
                    persons.getJSONObject(i).getString("username")
            ));
        }

        return personList;
    }

    public static Person readPerson(String rawJSON){
        JSONObject responseJSONObj = new JSONObject(rawJSON);

        return new Person(
                responseJSONObj.getInt("id"),
                responseJSONObj.getString("firstName"),
                responseJSONObj.getString("lastName"),
                responseJSONObj.getString("department"),
                responseJSONObj.getString("officeName"),
                responseJSONObj.getString("teamName"),
                responseJSONObj.getInt("floorNumber"),
                responseJSONObj.getString("username")
        );
    }

    public static List<Request> readRequests(String rawJSON){

        JSONObject responseJSONObj = new JSONObject("{\"data\":" + rawJSON + "}");
        JSONArray requests = responseJSONObj.getJSONArray("data");
        List<Request> requestList = new ArrayList<>(0);

        for (int i = 0; i < requests.length(); ++i){
            List<RequestPerson> receivers = new ArrayList<>(0);

            JSONArray recArray = requests.getJSONObject(i).getJSONArray("receivers");
            for (int j = 0; j < recArray.length(); ++j){
                receivers.add(new RequestPerson(
                        recArray.getJSONObject(j).getInt("id"),
                        recArray.getJSONObject(j).getInt("status")
                ));
            }

            requestList.add(new Request(
                    requests.getJSONObject(i).getInt("senderId"),
                    receivers,
                    requests.getJSONObject(i).getString("location"),
                    requests.getJSONObject(i).getString("comment")
            ));
        }

        return requestList;
    }

    public static Request readRequest(String rawJSON){

        JSONObject responseJSONObj = new JSONObject(rawJSON);

        List<RequestPerson> receivers = new ArrayList<>(0);

        JSONArray recArray = responseJSONObj.getJSONArray("receivers");
        for (int j = 0; j < recArray.length(); ++j){
            receivers.add(new RequestPerson(
                    recArray.getJSONObject(j).getInt("id"),
                    recArray.getJSONObject(j).getInt("status")
            ));
        }

        return new Request(
                responseJSONObj.getInt("senderId"),
                receivers,
                responseJSONObj.getString("location"),
                responseJSONObj.getString("comment")
        );
    }

    public static String writeObject(Object obj){
        return gson.toJson(obj);
    }
}