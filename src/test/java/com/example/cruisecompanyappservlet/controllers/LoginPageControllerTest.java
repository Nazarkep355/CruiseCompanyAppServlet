package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginPageControllerTest extends ControllerTester{
    public LoginPageControllerTest(){
        controller = new LoginPageController();
    }
    @Test
    void execute() throws DAOException {
        Assertions.assertEquals("loginPage.jsp",controller.execute(request,response));
    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.UNLOGGED,controller.accessLevel());
    }
}