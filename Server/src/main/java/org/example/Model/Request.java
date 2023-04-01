package org.example.Model;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {
    private Integer id;
    private final Integer senderId;
    private final List<RequestPerson> receivers;
    private final String location;

    private final String comment;

    public Request(Integer senderId, List<RequestPerson> receivers, String location, String comment) {
        this.senderId = senderId;
        this.receivers = receivers;
        this.location = location;
        this.comment = comment;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    public Integer getSenderId() {
        return senderId;
    }
    public List<RequestPerson> getReceivers() {
        return receivers;
    }
    public String getLocation() {
        return location;
    }
    public String getComment() {
        return comment;
    }
}
