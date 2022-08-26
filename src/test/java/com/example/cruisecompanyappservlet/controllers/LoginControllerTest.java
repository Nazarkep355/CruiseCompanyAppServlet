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

class LoginControllerTest extends ControllerTester{
    @Mock
    private UserService userService;
    public LoginControllerTest() {
        controller = new LoginController(userService);
    }

    @Test
    void execute() throws DAOException {
        String erEmail = "erMail";
        String password = "password";
        String email = "email@gmail.com";
        String error= "LogError";
        User user = new UserBuilder().email(email).password(password).build();
        when(userService.loginUser(email,password)).thenReturn(user);
        when(userService.loginUser(erEmail,password)).thenThrow(new IllegalArgumentException(error));
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        String address = controller.execute(request,response);
        Assertions.assertEquals("redirect:/controller",address);
        verify(session,times(0)).setAttribute("error",error);
        when(request.getParameter("email")).thenReturn(erEmail);
        address = controller.execute(request,response);
        Assertions.assertEquals("redirect:/controller",address);
        verify(session,times(1)).setAttribute("error",error);
    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.UNLOGGED,controller.accessLevel());
    }
}