package com.example.cruisecompanyappservlet.entity.builders;

import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.entity.RoomClass;
import com.example.cruisecompanyappservlet.entity.Ticket;
import com.example.cruisecompanyappservlet.entity.User;

import java.util.Date;

public class TicketBuilder {
    private long id;
    private Cruise cruise;
    private User owner;
    private RoomClass roomClass;
    private int cost;
    private Date purchaseDate;
    public TicketBuilder id(long id){
        this.id =id;
        return this;
    }
    public TicketBuilder cruise(Cruise cruise){
        this.cruise =cruise;
        return this;
    }
    public TicketBuilder owner(User owner){
        this.owner = owner;
        return this;
    }
    public TicketBuilder roomClass(RoomClass roomClass){
        this.roomClass = roomClass;
        return this;
    }
    public TicketBuilder cost(int cost){
        this.cost =cost;
        return this;
    }
    public TicketBuilder purchaseDate(Date date){
        this.purchaseDate = date;
        return this;
    }
    public Ticket build(){
        Ticket ticket=new Ticket();
        ticket.setId(id);
        ticket.setCruise(cruise);
        ticket.setOwner(owner);
        ticket.setRoomClass(roomClass);
        ticket.setCost(cost);
        ticket.setPurchaseDate(purchaseDate);
        return ticket;
    }
}
