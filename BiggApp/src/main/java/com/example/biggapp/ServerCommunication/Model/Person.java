package com.example.biggapp.ServerCommunication.Model;

import java.io.Serializable;

class Person implements Serializable {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String department;
    private final String officeName;
    private final String teamName;
    private final int floorNumber;

    public Person(int id, String firstName, String lastName, String department, String officeName, String teamName, int floorNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.officeName = officeName;
        this.teamName = teamName;
        this.floorNumber = floorNumber;
    }

    public int getId() {
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

    public int getFloorNumber() {
        return floorNumber;
    }
}
