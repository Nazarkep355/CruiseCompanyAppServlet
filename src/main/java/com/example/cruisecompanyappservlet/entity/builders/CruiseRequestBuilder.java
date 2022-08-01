package com.example.cruisecompanyappservlet.entity.builders;

import com.example.cruisecompanyappservlet.entity.*;

public class CruiseRequestBuilder {
    private long id;
    private User sender;
    private Cruise cruise;
    private String photo;
    private RoomClass roomClass;
    private Status status;
    public CruiseRequestBuilder sender(User sender){
        this.sender = sender;
        return this;
    }
    public CruiseRequestBuilder cruise(Cruise cruise){
        this.cruise = cruise;
        return this;
    }
    public CruiseRequestBuilder photo(String photo){
        this.photo=photo;
        return this;
    }
    public CruiseRequestBuilder id(long id){
        this.id=id;
        return this;
    }
    public CruiseRequestBuilder status(Status status){
        this.status =status;
        return this;
    }
    public CruiseRequestBuilder roomClass(RoomClass roomClass){
        this.roomClass = roomClass;
        return this;
    }
    public CruiseRequest build(){
        CruiseRequest cruiseRequest =new CruiseRequest();
        cruiseRequest.setId(id);
        cruiseRequest.setCruise(cruise);
        cruiseRequest.setSender(sender);
        cruiseRequest.setPhoto(photo);
        cruiseRequest.setStatus(status);
        cruiseRequest.setRoomClass(roomClass);
        return cruiseRequest;
    }
}
