package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Port;
import com.example.cruisecompanyappservlet.entity.builders.PortBuilder;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.service.PortService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddPortControllerTest extends ControllerTester {
    @Mock
    private PortService portService;


    public AddPortControllerTest() {
        super();
        controller = new AddPortController(portService);
    }

    @Test
    void execute() throws DAOException {
        String oldCity = "oldCity";
        String newCity = "newCity";
        Port newPort = new PortBuilder()
                .city(newCity)
                .build();
        when(portService.isPortExists(oldCity)).thenReturn(true);
        when(portService.isPortExists(newCity)).thenReturn(false);


        when(request.getParameter("city")).thenReturn(oldCity);
        String address = controller.execute(request, response);
        Assertions.assertEquals("redirect:/controller?controller=addPortPage", address);
        verify(session, times(1)).setAttribute("error", "PortInDB");


        when(request.getParameter("city")).thenReturn(newCity);
        address = controller.execute(request, response);
        Assertions.assertEquals("redirect:/controller?controller=ports&page=1", address);
        verify(portService, times(1)).insert(newPort);
    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.ADMIN, controller.accessLevel());
    }
}