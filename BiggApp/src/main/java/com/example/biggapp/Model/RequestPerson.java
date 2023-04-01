package com.example.biggapp.Model;

import java.io.Serializable;

public class RequestPerson implements Serializable {
    private final Integer id;
    private Integer status = 0;

    public RequestPerson(int id) {
        this.id = id;
    }

    public RequestPerson(int id, int status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
