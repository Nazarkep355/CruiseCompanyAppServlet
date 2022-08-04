package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.util.RequestReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateRoutePageController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        RequestReader.setError(request);
        return "createRoute.jsp";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.ADMIN;
    }
}
