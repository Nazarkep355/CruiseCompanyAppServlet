package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOutController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        request.getSession().setAttribute("user",null);
        System.out.println("singedOut");
        return "/controller";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.USER;
    }
}
