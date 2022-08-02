package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.TicketDAO;
import com.example.cruisecompanyappservlet.entity.CruiseRequest;
import com.example.cruisecompanyappservlet.entity.Status;
import com.example.cruisecompanyappservlet.entity.Ticket;
import com.example.cruisecompanyappservlet.entity.builders.TicketBuilder;

import java.time.Instant;
import java.util.Date;

public class TicketService {
    private TicketDAO ticketDAO;
    public TicketService(){
        ticketDAO = new TicketDAO();
    }
    public Ticket createTicketBy(CruiseRequest cruiseRequest){
            return new TicketBuilder()
                    .cruise(cruiseRequest.getCruise())
                    .owner(cruiseRequest.getSender())
                    .roomClass(cruiseRequest.getRoomClass())
                    .cost(cruiseRequest.getCruise().getCostByClass(cruiseRequest.getRoomClass()))
                    .purchaseDate(Date.from(Instant.now()))
                    .build();
    }
    public boolean insert(Ticket ticket){
        return ticketDAO.insert(ticket);
    }
}
