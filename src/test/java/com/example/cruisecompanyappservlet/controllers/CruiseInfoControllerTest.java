package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.entity.builders.CruiseBuilder;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.service.CruiseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CruiseInfoControllerTest extends ControllerTester{
    @Mock
    private CruiseService cruiseService;

    public CruiseInfoControllerTest() {
        controller = new CruiseInfoController(cruiseService);
    }

    @Test
    void execute() throws DAOException {
        Cruise cruise = new CruiseBuilder().id(1).build();
        when(cruiseService.findById(1)).thenReturn(cruise);
        when(request.getParameter("id")).thenReturn("1");
        String address = controller.execute(request,response);
        verify(request,times(1)).setAttribute("cruise",cruise);
        Assertions.assertEquals("cruiseInfo.jsp",address);

    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.UNLOGGED,controller.accessLevel());
    }
}