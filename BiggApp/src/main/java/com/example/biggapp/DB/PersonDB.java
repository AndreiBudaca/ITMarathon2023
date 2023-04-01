package com.example.biggapp.DB;

import com.example.biggapp.Model.Person;
import com.example.biggapp.Parser.JSONParser;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PersonDB {
    private final String fileName = "person.json";
    private List<Person> personList = new ArrayList<>(0);
    private int nextId = 0;

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
            f.write(JSONParser.writeObject(personList));
            f.flush();
        }
        catch (Exception e){
            System.out.println("Failed to save DB!");
        }
    }

    public void load(){
        try{
            String content = Files.readString(Paths.get(fileName), StandardCharsets.US_ASCII);
            personList = JSONParser.readPersons(content);
        } catch (Exception e) {
            System.out.println("Failed to read DB!\n" + e.getLocalizedMessage());
        }
    }
}
