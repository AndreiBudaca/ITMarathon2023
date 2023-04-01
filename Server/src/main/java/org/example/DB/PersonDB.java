package org.example.DB;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.example.Model.Person;
import org.example.Parser.JSONParser;

public class PersonDB {
    private final String fileName = "person.json";
    private List<Person> personList = new ArrayList<>(0);
    private int nextId = 0;

    public Integer getPersonId(String username){

        for (Person person: personList){
            if (person.getUsername().equals(username)){
                return person.getId();
            }
        }

        return -1;
    }

    public Integer getPersonId(String firstName, String lastName){

        for (Person person: personList){
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)){
                return person.getId();
            }
        }

        return -1;
    }

    public void addPerson(Person person){
        personList.add(person);
        save();
    }

    public void deletePerson(int id){

        for (Person person: personList){
            if (person.getId() == id){
                personList.remove(person);
                break;
            }
        }
    }

    public void modifyPerson(int personId, Person newPerson){
        newPerson.setId(personId);
        deletePerson(personId);
        addPerson(newPerson);
    }

    public Person getPerson(int id){
        for (Person person: personList){
            if (person.getId() == id)
                return person;
        }

        return null;
    }

    public List<Person> getPersonList() { return personList; }

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
            System.out.println("Failed to read DB!\n" + e.getLocalizedMessage() + "\n");
        }
    }
}
