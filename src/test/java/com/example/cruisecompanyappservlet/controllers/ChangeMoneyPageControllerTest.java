package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeMoneyPageControllerTest extends ControllerTester{
    public ChangeMoneyPageControllerTest(){
        controller = new ChangeMoneyPageController();
    }
    @Test
    void execute() throws DAOException {

        String address = controller.execute(request,response);
        Assertions.assertEquals("changeMoney.jsp",address);
    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.USER,controller.accessLevel());
    }
}