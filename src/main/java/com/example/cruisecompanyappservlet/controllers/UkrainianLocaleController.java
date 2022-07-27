package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UkrainianLocaleController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute("locale","ua");
        return request.getContextPath()+request.getParameter("prev");
    }


    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.UNLOGGED;
    }
}
