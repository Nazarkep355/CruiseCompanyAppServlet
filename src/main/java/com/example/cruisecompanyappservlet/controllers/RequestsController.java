package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.CruiseRequest;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.CruiseRequestService;
import com.example.cruisecompanyappservlet.util.RequestReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RequestsController implements Controller {
    private CruiseRequestService cruiseRequestService;

    public RequestsController(CruiseRequestService cruiseRequestService) {
        this.cruiseRequestService = cruiseRequestService;
    }

    public RequestsController(){
        cruiseRequestService = new CruiseRequestService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        List<CruiseRequest> requests =RequestReader.getListOfRequestsFromRequest(request);
        request.setAttribute("requests",requests);
        return "requests.jsp";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.ADMIN;
    }
}
