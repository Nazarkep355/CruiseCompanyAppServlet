package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.TicketDAO;
import com.example.cruisecompanyappservlet.entity.CruiseRequest;
import com.example.cruisecompanyappservlet.entity.Status;
import com.example.cruisecompanyappservlet.entity.Ticket;
import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.entity.builders.TicketBuilder;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class TicketService {
    private TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public TicketService(){
        ticketDAO = new TicketDAO();
    }

    public boolean insert(Ticket ticket){
        return ticketDAO.insert(ticket);
    }
    public List<Ticket> findByUserPaginated(User user,int page){
        int offset = page*5-5;
        return ticketDAO.findByUserPaginate(user,offset);
    }
}
