package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.entity.builders.UserBuilder;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChangeMoneyControllerTest extends ControllerTester {
    @Mock
    private UserService userService;
    public ChangeMoneyControllerTest(){
        super();
        controller = new ChangeMoneyController(userService);
    }
    @Test
    void execute() throws DAOException {
        User user = new UserBuilder()
                .name("USER1")
                .build();
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("money")).thenReturn("150");
        String address = controller.execute(request,response);
        verify(userService,times(1)).ChangeUserMoney(user,150);
        Assertions.assertEquals("/controller",controller.execute(request,response));

    }

    @Test
    void accessLevel() {
        assertEquals(AccessLevel.USER,controller.accessLevel());
    }
}