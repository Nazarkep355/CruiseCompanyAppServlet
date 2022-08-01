package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.CruiseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendRequestPageController implements Controller {
    private CruiseService cruiseService;
    public SendRequestPageController(){
        cruiseService = new CruiseService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        long id = Long.parseLong(request.getParameter("id"));
        String roomClass = request.getParameter("room");
        System.out.println(roomClass);
        Cruise cruise = cruiseService.findById(id);
        if(roomClass.equalsIgnoreCase("PREMIUM"))
            request.setAttribute("cost",cruise.getCostPremium());
        if(roomClass.equalsIgnoreCase("MIDDLE"))
            request.setAttribute("cost",cruise.getCostMiddle());
        if(roomClass.equalsIgnoreCase("ECONOM"))
            request.setAttribute("cost",cruise.getCostEconom());
        request.setAttribute("roomClass",roomClass);
        request.setAttribute("cruise",cruise);
        return "sendRequest.jsp";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.USER;
    }
}
