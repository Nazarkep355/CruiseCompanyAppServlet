package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddStaffPageControllerTest extends ControllerTester{
    public AddStaffPageControllerTest(){
        super();
        controller = new AddPortPageController();
    }
    @Test
    void execute() throws DAOException {
        String error = "EmployeeAlreadyAdded";
        when(session.getAttribute("error")).thenReturn(error);
        String address = controller.execute(request,response);
        verify(request,times(1)).setAttribute("error",error);

    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.ADMIN,controller.accessLevel());
    }
}