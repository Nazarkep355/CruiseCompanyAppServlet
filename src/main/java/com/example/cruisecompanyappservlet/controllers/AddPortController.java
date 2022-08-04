package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Port;
import com.example.cruisecompanyappservlet.entity.builders.PortBuilder;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.PortService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AddPortController implements Controller {
    private PortService portService;
    public AddPortController(){
        portService = new PortService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        String city = request.getParameter("city");
        if(portService.isPortExists(city)){
            request.getSession().setAttribute("error","PortInDB");
            return "/controller?controller=addPortPage";
        }else {
            portService.insert(new PortBuilder()
                    .city(city)
                    .build());
            return "/controller?controller=ports&page=1";

        }
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.ADMIN;
    }
}
