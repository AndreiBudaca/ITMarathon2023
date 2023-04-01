package com.example.biggapp.Model;

import java.io.Serializable;

public class Person implements Serializable {
    private Integer id;
    private final String firstName;
    private final String lastName;
    private final String department;
    private final String officeName;
    private final String teamName;
    private final Integer floorNumber;
    private final String username;

    public Person(String firstName, String lastName, String department, String officeName, String teamName, int floorNumber, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.officeName = officeName;
        this.teamName = teamName;
        this.floorNumber = floorNumber;
        this.username = username;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    public String getOfficeName() {
        return officeName;
    }

    public String getTeamName() {
        return teamName;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public String getUsername() {
        return username;
    }
}
