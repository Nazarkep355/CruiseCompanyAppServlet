package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.*;
import com.example.cruisecompanyappservlet.entity.*;
import com.example.cruisecompanyappservlet.entity.builders.TicketBuilder;
import com.example.cruisecompanyappservlet.util.DBHikariManager;
import com.example.cruisecompanyappservlet.util.EmailSessionBean;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import java.sql.Connection;
import java.util.List;

public class CruiseRequestService {
    private CruiseRequestDAO cruiseRequestDAO;
    private CruiseDAO cruiseDAO;
    private UserDAO userDAO;
    private TicketDAO ticketDAO;
    @EJB
    private EmailSessionBean emailSessionBean ;

    public CruiseRequestService(CruiseRequestDAO cruiseRequestDAO, CruiseDAO cruiseDAO, UserDAO userDAO, TicketDAO ticketDAO,EmailSessionBean emailSessionBean) {
        this.cruiseRequestDAO = cruiseRequestDAO;
        this.cruiseDAO = cruiseDAO;
        this.userDAO = userDAO;
        this.ticketDAO = ticketDAO;
        this.emailSessionBean = emailSessionBean;
    }

    public CruiseRequestService() {
        ticketDAO = new TicketDAO();
        cruiseRequestDAO = new CruiseRequestDAO();
        userDAO = new UserDAO();
        cruiseDAO = new CruiseDAO();
    }

    public CruiseRequest findById(long id) {
        return cruiseRequestDAO.findById(id);
    }

    public boolean insert(CruiseRequest cruiseRequest) {
        return cruiseRequestDAO.insert(cruiseRequest);
    }

    public List<CruiseRequest> getPaginatedList(int page) {
        int offset = (page * 5) - 5;
        return cruiseRequestDAO.findAllOrderByIdDescPaginated(offset);
    }

    public List<CruiseRequest> findByCruiseAndStatusPaginated(Cruise cruise, Status status, int page) {
        int offset = (page * 5) - 5;
        return cruiseRequestDAO.findByCruiseAndStatusPaginated(cruise, status, offset);
    }

    public List<CruiseRequest> findByUserPaginated(User user, int page) {
        int offset = (page * 5) - 5;
        return cruiseRequestDAO.findByUserPaginated(user, offset);
    }

    public List<CruiseRequest> findByCruisePaginated(Cruise cruise, int page) {
        int offset = (page * 5) - 5;
        return cruiseRequestDAO.findByCruisePaginated(cruise, offset);
    }

    public boolean changeRequest(CruiseRequest cruiseRequest, boolean status) throws DAOException, MessagingException {
        if (status) {
            Cruise cruise = cruiseRequest.getCruise();
            User user = cruiseRequest.getSender();
            if (cruise.getFreePlaces().get(cruiseRequest.getRoomClass()) <= 0) {
                cruiseRequest.setStatus(Status.REFUSED);
                return false;
            }
            user.setMoney(user.getMoney() - cruise.getCostByClass(cruiseRequest.getRoomClass()));
            cruise.getFreePlaces().put(cruiseRequest.getRoomClass(),
                    cruise.getFreePlaces().get(cruiseRequest.getRoomClass()) - 1);
            cruiseRequest.setStatus(Status.ACCEPTED);
            cruiseRequestDAO.createTicketTransaction(cruiseRequest);
            emailSessionBean.sendMessageAboutAccepting(cruiseRequest);
            return true;
        } else {
            emailSessionBean.sendMessageAboutRefusing(cruiseRequest);
            cruiseRequest.setStatus(Status.REFUSED);
            return false;
        }
    }
}
