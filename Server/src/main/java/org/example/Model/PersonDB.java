package org.example.Model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;

public class PersonDB {
    private final String fileName = "person.json";
    private final List<Person> personList = new ArrayList<>(0);
    private int nextId = 0;
    private final Gson gson = new Gson();

    public Integer getPersonId(String username, String password){

        for (Person person: personList){
            if (person.getUsername().equals(username) && person.getPassword().equals(password)){
                return person.getId();
            }
        }

        return -1;
    }

    public void addPerson(Person person){
        person.setId(nextId);
        ++nextId;
        System.out.println("Saving...");
        personList.add(person);
        save();
        System.out.println("Saved on disk");
    }

    public void deletePerson(int id){

        for (Person person: personList){
            if (person.getId() == id){
                personList.remove(person);
                break;
            }
        }
    }

    public void save(){
        try {
            FileWriter f = new FileWriter(fileName);
            f.write(gson.toJson(personList));
            f.flush();
        }
        catch (Exception e){
            System.out.println("Failed to save DB!");
        }
    }

    public void load(){
        try{
            String content = Files.readString(Paths.get(fileName), StandardCharsets.US_ASCII);
            JSONObject responseJSONObj = new JSONObject("{\"data\": " + content + "}");
            JSONArray persons = responseJSONObj.getJSONArray("data");

            for (int i = 0; i < persons.length(); ++i){
                personList.add(new Person(
                        persons.getJSONObject(i).getString("firstName"),
                        persons.getJSONObject(i).getString("lastName"),
                        persons.getJSONObject(i).getString("department"),
                        persons.getJSONObject(i).getString("officeName"),
                        persons.getJSONObject(i).getString("teamName"),
                        persons.getJSONObject(i).getInt("floorNumber"),
                        persons.getJSONObject(i).getString("username"),
                        persons.getJSONObject(i).getString("password")

                ));
            }
        } catch (Exception e) {
            System.out.println("Failed to read DB!\n" + e.getLocalizedMessage());
        }
    }
}
