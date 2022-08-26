package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.service.StaffService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddStaffControllerTest extends ControllerTester {
    @Mock
    private StaffService staffService;

    public AddStaffControllerTest() {
        super();
        controller = new AddStaffController(staffService);
    }

    @Test
    void execute() throws DAOException {
            String oldName = "Jack";
            String newName = "John";
            String pos = "Captain";
            when(staffService.isStaffWithNameExists(oldName)).thenReturn(true);
            when(staffService.isStaffWithNameExists(newName)).thenReturn(false);


            when(request.getParameter("name")).thenReturn(oldName);
            when(request.getParameter("position")).thenReturn(pos);
            String address = controller.execute(request,response);
            Assertions.assertEquals("/controller?controller=addStaffPage",address);
            verify(session,times(1)).setAttribute("error","EmployeeAlreadyAdded");



            when(request.getParameter("name")).thenReturn(newName);
            address = controller.execute(request,response);
            Assertions.assertEquals("/controller?controller=staff&page=1",address);
            verify(staffService,times(1)).insert(newName,pos);


    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.ADMIN, controller.accessLevel());
    }
}