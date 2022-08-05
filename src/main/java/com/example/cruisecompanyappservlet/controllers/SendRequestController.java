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

    public SendRequestController(CruiseService cruiseService, CruiseRequestService cruiseRequestService) {
        this.cruiseService = cruiseService;
        this.cruiseRequestService = cruiseRequestService;
    }

    public SendRequestController() {
        cruiseRequestService = new CruiseRequestService();
        cruiseService = new CruiseService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        User user = (User) request.getSession().getAttribute("user");
        long id = Long.parseLong(request.getParameter("id"));
        String rClass = request.getParameter("roomClass");
        Cruise cruise = cruiseService.findById(id);
        if (cruise.getFreePlaces().get(RoomClass.valueOf(rClass)) > 0) {
            try {
                System.out.println("request creating");
                CruiseRequest cruiseRequest = new CruiseRequestBuilder()
                        .cruise(cruise)
                        .roomClass(RoomClass.valueOf(rClass))
                        .status(Status.WAITING)
                        .sender(user)
                        .photo(RequestReader.saveImage(request))
                        .build();
                cruiseRequestService.insert(cruiseRequest);

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }else{
            request.getSession().setAttribute("error","NoFreePlaces");
            request.setAttribute("redirect",true);
            return "redirect:/controller?controller=sendRequestPage&id="+id+"&room="+rClass;
        }

        return "redirect:/controller";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.USER;
    }
}
