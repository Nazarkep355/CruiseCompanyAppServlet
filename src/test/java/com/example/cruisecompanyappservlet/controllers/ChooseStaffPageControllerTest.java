package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.service.StaffService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ChooseStaffPageControllerTest extends ControllerTester{
    @Mock
    private StaffService staffService;
        public ChooseStaffPageControllerTest(){
            controller = new ChooseStaffPageController(staffService);
        }
    @Test
    void execute() throws DAOException {
            when(request.getParameter("page")).thenReturn("1");
            when(request.getParameter("current")).thenReturn("2");
            when(request.getParameter("of")).thenReturn("2");
            when(request.getParameter("id")).thenReturn("1");
            String address = controller.execute(request,response);
            Assertions.assertEquals("redirect:/controller?controller=planCruisePage",address);
            when(request.getParameter("current")).thenReturn("1");
            address = controller.execute(request,response);
            Assertions.assertEquals("chooseStaff.jsp",address);

    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.ADMIN,controller.accessLevel());
        }
}