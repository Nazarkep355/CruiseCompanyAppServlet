package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.CruiseRequest;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.CruiseRequestService;
import com.example.cruisecompanyappservlet.service.CruiseService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ResponseRequestController implements Controller {
    private CruiseRequestService cruiseRequestService;
    private static Logger logger = Logger.getLogger(ResponseRequestController.class);
    CruiseService cruiseService;

    public ResponseRequestController(CruiseRequestService cruiseRequestService, CruiseService cruiseService) {
        this.cruiseRequestService = cruiseRequestService;
        this.cruiseService = cruiseService;
    }

    public ResponseRequestController(){
        cruiseRequestService = new CruiseRequestService();
        cruiseService = new CruiseService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        try{Boolean resp = Boolean.valueOf(request.getParameter("response"));
        long requestId = Long.parseLong(request.getParameter("id"));
        CruiseRequest cruiseRequest = cruiseRequestService.findById(requestId);
        cruiseRequestService.changeRequest(cruiseRequest,resp);
        return "redirect:/controller?controller=requests&page=1";}
        catch (MessagingException e){
            String message = "Can't send email";
            logger.info(message,e);
            throw new RuntimeException(message,e);
        }
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.ADMIN;
    }
}
