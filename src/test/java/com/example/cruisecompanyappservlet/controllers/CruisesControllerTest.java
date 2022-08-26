package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.entity.Status;
import com.example.cruisecompanyappservlet.entity.builders.CruiseBuilder;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.util.RequestReader;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CruisesControllerTest extends ControllerTester{
    public CruisesControllerTest() {
        controller = new CruisesController();
    }

    @Test
    void execute() throws DAOException {
        try(MockedStatic<RequestReader> readerMockedStatic = mockStatic(RequestReader.class)){
            List<Cruise> cruises = new ArrayList<>();
            readerMockedStatic.when(()->RequestReader.getListOfRequestsFromRequest(request)).thenReturn(cruises);
            String address = controller.execute(request,response);
            Assertions.assertEquals("cruises.jsp",address);
            verify(request,times(1)).setAttribute("cruises",cruises);

        }
    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.UNLOGGED,controller.accessLevel());
    }
}