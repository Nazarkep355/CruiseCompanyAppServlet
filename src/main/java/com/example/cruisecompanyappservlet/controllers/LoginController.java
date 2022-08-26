package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController implements Controller {
    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        try {
            User user = userService.loginUser(email, password);
            request.getSession().setAttribute("user",user);
        }catch (IllegalArgumentException e){
            request.getSession().setAttribute("error",e.getMessage());
        }
        return "redirect:/controller";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.UNLOGGED;
    }
}
