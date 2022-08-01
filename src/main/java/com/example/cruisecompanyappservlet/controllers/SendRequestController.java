package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.*;
import com.example.cruisecompanyappservlet.entity.builders.CruiseRequestBuilder;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.CruiseRequestService;
import com.example.cruisecompanyappservlet.service.CruiseService;
import com.example.cruisecompanyappservlet.util.RequestReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendRequestController implements Controller {
    private CruiseService cruiseService;
    private CruiseRequestService cruiseRequestService;
    public SendRequestController(){
        cruiseRequestService = new CruiseRequestService();
        cruiseService = new CruiseService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        User user = (User) request.getSession().getAttribute("user");
        long id = Long.parseLong(request.getParameter("id"));
        String rClass = request.getParameter("roomClass");
        Cruise cruise = cruiseService.findById(id);
        try {
            CruiseRequest cruiseRequest = new CruiseRequestBuilder()
                    .cruise(cruise)
                    .roomClass(RoomClass.valueOf(rClass))
                    .status(Status.WAITING)
                    .sender(user)
                    .photo(RequestReader.saveImage(request))
                    .build();
            cruiseRequestService.insert(cruiseRequest);
            System.out.println("done");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        return "/";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.USER;
    }
}
