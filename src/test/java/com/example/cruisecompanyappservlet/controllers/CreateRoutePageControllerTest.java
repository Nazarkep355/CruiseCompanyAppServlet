package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CreateRoutePageControllerTest extends ControllerTester {
    public CreateRoutePageControllerTest() {
        controller  = new CreateRoutePageController();
    }

    @Test
    void execute() throws DAOException {
        String address = controller.execute(request,response);
        Assertions.assertEquals("createRoute.jsp",address);
        verify(session,times(1)).getAttribute("error");
    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.ADMIN,controller.accessLevel());
    }
}