package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.entity.CruiseRequest;
import com.example.cruisecompanyappservlet.entity.RoomClass;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.CruiseRequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InfoAboutRequestController implements Controller {
    private CruiseRequestService cruiseRequestService;
    public InfoAboutRequestController(){
        cruiseRequestService = new CruiseRequestService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        long id = Long.parseLong(request.getParameter("id"));
        CruiseRequest cruiseRequest = cruiseRequestService.findById(id);
        Cruise cruise = cruiseRequest.getCruise();
        int cost = cruise.getCostByClass(cruiseRequest.getRoomClass());
        request.setAttribute("cost",cost);
        request.setAttribute("cRequest",cruiseRequest);
        return "requestInfo.jsp";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.ADMIN;
    }
}
