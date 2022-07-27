package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterPageController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("error") != null) {
            request.setAttribute("error",request.getSession().getAttribute("error"));
            request.getSession().setAttribute("error",null);
        }
        return "registerPage.jsp";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.UNLOGGED;
    }
}
