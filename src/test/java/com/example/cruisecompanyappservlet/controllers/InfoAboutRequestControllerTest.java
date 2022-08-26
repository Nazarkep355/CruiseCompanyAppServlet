package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.entity.CruiseRequest;
import com.example.cruisecompanyappservlet.entity.RoomClass;
import com.example.cruisecompanyappservlet.entity.builders.CruiseBuilder;
import com.example.cruisecompanyappservlet.entity.builders.CruiseRequestBuilder;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.service.CruiseRequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InfoAboutRequestControllerTest extends ControllerTester{
    @Mock
    private CruiseRequestService requestService;
    public InfoAboutRequestControllerTest() {
        controller = new InfoAboutRequestController(requestService);
    }

    @Test
    void execute() throws DAOException {
        Cruise cruise = new CruiseBuilder().id(1l).costPremium(1500).build();
        CruiseRequest cruiseRequest = new CruiseRequestBuilder().roomClass(RoomClass.PREMIUM).cruise(cruise).build();
        when(request.getParameter("id")).thenReturn("1");
        when(requestService.findById(1)).thenReturn(cruiseRequest);
        String address = controller.execute(request,response);
        verify(request,times(1)).setAttribute("cost",1500);
        verify(request,times(1)).setAttribute("cRequest",cruiseRequest);
        Assertions.assertEquals("requestInfo.jsp",address);

    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.ADMIN,controller.accessLevel());
    }
}