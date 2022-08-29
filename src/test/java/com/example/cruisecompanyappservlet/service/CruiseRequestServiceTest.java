package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.*;
import com.example.cruisecompanyappservlet.entity.*;
import com.example.cruisecompanyappservlet.entity.builders.CruiseBuilder;
import com.example.cruisecompanyappservlet.entity.builders.CruiseRequestBuilder;
import com.example.cruisecompanyappservlet.util.EmailSessionBean;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CruiseRequestServiceTest {
    @Mock
    private CruiseRequestDAO cruiseRequestDAO;
    @Mock
    private TicketDAO ticketDAO;
    @Mock
    private CruiseDAO cruiseDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    EmailSessionBean emailSessionBean;
    private List<CruiseRequest> requests;

    CruiseRequestService requestService;

    public CruiseRequestServiceTest() {
        MockitoAnnotations.initMocks(this);
        requestService = new CruiseRequestService(cruiseRequestDAO, cruiseDAO, userDAO, ticketDAO,emailSessionBean);
        requests = new ArrayList<>();
        Cruise cruise = new Cruise();
        for (int i = 0; i < 6; i++) {
            CruiseRequest request = new CruiseRequestBuilder()
                    .id(i + 1)
                    .cruise(cruise)
                    .roomClass(RoomClass.PREMIUM)
                    .sender(new User())
                    .status(Status.WAITING)
                    .build();
            requests.add(request);
        }
    }

    @Test
    void findById() {
        requestService.findById(1L);
        verify(cruiseRequestDAO, times(1)).findById(1l);
    }

    @Test
    void insert() {
        CruiseRequest request = new CruiseRequestBuilder()
                .id(1l)
                .build();
        requestService.insert(request);
        verify(cruiseRequestDAO, times(1)).insert(request);

    }

    @Test
    void getPaginatedList() {
        requestService.getPaginatedList(1);
        verify(cruiseRequestDAO, times(1)).findAllOrderByIdDescPaginated(0);
        requestService.getPaginatedList(2);
        verify(cruiseRequestDAO, times(1)).findAllOrderByIdDescPaginated(5);
    }

    @Test
    void findByCruiseAndStatusPaginated() {
        Cruise cruise = new Cruise();
        requestService.findByCruiseAndStatusPaginated(cruise, Status.WAITING, 1);
        verify(cruiseRequestDAO, times(1))
                .findByCruiseAndStatusPaginated(cruise, Status.WAITING, 0);
    }

    @Test
    void findByUserPaginated() {
        User user = new User();
        requestService.findByUserPaginated(user, 1);
        verify(cruiseRequestDAO, times(1)).findByUserPaginated(user, 0);
    }

    @Test
    void findByCruisePaginated() {
        Cruise cruise = new Cruise();
        requestService.findByCruisePaginated(cruise, 1);
        verify(cruiseRequestDAO, times(1)).findByCruisePaginated(cruise, 0);
    }

    @Test
    void changeRequest() throws DAOException, MessagingException {
        HashMap<RoomClass,Integer> hashMap = new HashMap<>();
        hashMap.put(RoomClass.PREMIUM,150);
        Cruise cruise = new CruiseBuilder()
                .costPremium(150)
                .freePlaces(hashMap)
                .build();
        User user = new User();
        CruiseRequest request = new CruiseRequestBuilder()
                .cruise(cruise)
                .roomClass(RoomClass.PREMIUM)
                .status(Status.WAITING)
                .sender(user)
                .build();
        requestService.changeRequest(request,false);
        Assertions.assertEquals(Status.REFUSED,request.getStatus());
        requestService.changeRequest(request,true);
        Assertions.assertEquals(Status.ACCEPTED,request.getStatus());

    }
}