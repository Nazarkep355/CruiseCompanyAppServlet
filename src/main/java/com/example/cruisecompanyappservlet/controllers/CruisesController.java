package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.util.RequestReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CruisesController implements Controller {
    private RequestReader requestReader;

    public CruisesController(RequestReader requestReader) {
        this.requestReader = requestReader;
    }

    public CruisesController(){
        requestReader=new RequestReader();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        List<Cruise> cruises = RequestReader.getListOfCruisesFromRequest(request);
        request.setAttribute("cruises",cruises);
        return "cruises.jsp";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.UNLOGGED;
    }
}
