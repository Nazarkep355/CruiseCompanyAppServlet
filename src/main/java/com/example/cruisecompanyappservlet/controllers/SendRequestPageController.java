package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.entity.RoomClass;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.CruiseService;
import com.example.cruisecompanyappservlet.util.RequestReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendRequestPageController implements Controller {
    private CruiseService cruiseService;
    public SendRequestPageController(){
        cruiseService = new CruiseService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        RequestReader.setError(request);
        long id = Long.parseLong(request.getParameter("id"));
        RoomClass roomClass = RoomClass.valueOf(request.getParameter("room"));
        Cruise cruise = cruiseService.findById(id);
        request.setAttribute("cost",cruise.getCostByClass(roomClass));
        request.setAttribute("roomClass",roomClass);
        request.setAttribute("cruise",cruise);
        return "sendRequest.jsp";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.USER;
    }
}
