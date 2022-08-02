package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.CruiseRequestDAO;
import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.*;
import com.example.cruisecompanyappservlet.util.EmailSessionBean;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import java.util.List;

public class CruiseRequestService {
    private CruiseRequestDAO cruiseRequestDAO;
    private CruiseService cruiseService;
    private UserService userService;
    private TicketService ticketService;
    @EJB
    private EmailSessionBean emailSessionBean = new EmailSessionBean();

    public CruiseRequestService() {
        ticketService = new TicketService();
        cruiseRequestDAO = new CruiseRequestDAO();
        userService = new UserService();
        cruiseService = new CruiseService();
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
            userService.update(user);
            cruiseService.update(cruise);
            cruiseRequestDAO.update(cruiseRequest);
            Ticket ticket =ticketService.createTicketBy(cruiseRequest);
            ticketService.insert(ticket);
            emailSessionBean.sendMessageAboutAccepting(cruiseRequest);
            return true;
        } else {
            emailSessionBean.sendMessageAboutRefusing(cruiseRequest);
            return false;
        }
    }
}
