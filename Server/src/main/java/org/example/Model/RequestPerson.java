package org.example.Model;

public class RequestPerson {
    private final int id;
    private boolean accepted = false;

    public RequestPerson(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
