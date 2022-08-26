package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.channels.AcceptPendingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EnglishLocaleControllerTest extends ControllerTester{
    EnglishLocaleControllerTest(){
        controller = new EnglishLocaleController();
    }
    @Test
    void execute() throws DAOException {
        when(request.getContextPath()).thenReturn("");
        when(request.getParameter("prev")).thenReturn("referer");
        String address = controller.execute(request,response);
        Assertions.assertEquals("referer",address);
    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.UNLOGGED,controller.accessLevel());
    }
}