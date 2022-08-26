package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class HelloControllerTest extends ControllerTester {
    public HelloControllerTest() {
        controller = new HelloController();
    }

    @Test
    void execute() throws DAOException {
        when(session.getAttribute("user")).thenReturn(null);
        String address = controller.execute(request,response);
        Assertions.assertEquals("loginPage.jsp",address);
        when(session.getAttribute("user")).thenReturn(new User());
        address = controller.execute(request,response);
        Assertions.assertEquals("accountPage.jsp",address);

    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.UNLOGGED,controller.accessLevel());
    }
}