package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.TicketDAO;
import com.example.cruisecompanyappservlet.entity.Ticket;
import com.example.cruisecompanyappservlet.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TicketServiceTest {

    @Mock
    private TicketDAO ticketDAO;
    private TicketService ticketService;
    public TicketServiceTest(){
        MockitoAnnotations.initMocks(this);
        ticketService = new TicketService(ticketDAO);
    }
    @Test
    void insert() {
        Ticket ticket = new Ticket();
        ticketService.insert(ticket);
        verify(ticketDAO,times(1)).insert(ticket);
    }

    @Test
    void findByUserPaginated() {
        User user = new User();
        ticketService.findByUserPaginated(user,1);
        verify(ticketDAO,times(1)).findByUserPaginate(user,0);
    }
}