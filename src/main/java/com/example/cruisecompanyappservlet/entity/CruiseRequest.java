package com.example.cruisecompanyappservlet.entity;

public class CruiseRequest {
    private long id;
    private User sender;
    private Cruise cruise;
    private String photo;
    private RoomClass roomClass;
    private Status status;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Cruise getCruise() {
        return cruise;
    }

    public void setCruise(Cruise cruise) {
        this.cruise = cruise;
    }

    public String getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return "CruiseRequest{" +
                "id=" + id +
                ", sender=" + sender +
                ", cruise=" + cruise +
                ", photo='" + photo + '\'' +
                ", roomClass=" + roomClass +
                ", status=" + status +
                '}';
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public RoomClass getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(RoomClass roomClass) {
        this.roomClass = roomClass;
    }
}
