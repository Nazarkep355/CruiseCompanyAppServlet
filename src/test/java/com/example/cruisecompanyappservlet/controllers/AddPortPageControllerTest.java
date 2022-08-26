package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddPortPageControllerTest extends ControllerTester {
    public AddPortPageControllerTest() {
        super();
        controller = new AddPortPageController();
    }

    @Test
    void execute() throws DAOException {
        when(session.getAttribute("error")).thenReturn("PortInDB");
        String address = controller.execute(request, response);
        Assertions.assertEquals("addPort.jsp",address);
        verify(request,times(1)).setAttribute("error","PortInDB");
    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.ADMIN, controller.accessLevel());
    }
}