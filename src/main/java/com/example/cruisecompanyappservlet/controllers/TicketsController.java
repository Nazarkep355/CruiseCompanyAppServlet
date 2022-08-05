package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Ticket;
import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TicketsController implements Controller {
    private TicketService ticketService;

    public TicketsController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public TicketsController(){
        ticketService = new TicketService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        int page = Integer.parseInt(request.getParameter("page"));
        User user = (User) request.getSession().getAttribute("user");
        List<Ticket> tickets = ticketService.findByUserPaginated(user,page);
        request.setAttribute("tickets",tickets);
        return  "tickets.jsp";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.USER;
    }
}
