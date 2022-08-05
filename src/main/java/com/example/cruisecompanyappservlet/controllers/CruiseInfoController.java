package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.CruiseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CruiseInfoController implements Controller {
    private CruiseService cruiseService;

    public CruiseInfoController(CruiseService cruiseService) {
        this.cruiseService = cruiseService;
    }

    public CruiseInfoController(){
        cruiseService = new CruiseService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        long id = Long.parseLong(request.getParameter("id"));
        Cruise cruise = cruiseService.findById(id);
        request.setAttribute("cruise",cruise);
        return "cruiseInfo.jsp";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.UNLOGGED;
    }
}
