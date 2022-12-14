package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Port;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.PortService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class PortsController implements Controller {
    private PortService portService;

    public PortsController(PortService portService) {
        this.portService = portService;
    }

    public PortsController(){
        portService = new PortService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        int page = Integer.parseInt(request.getParameter("page"));
        List<Port> ports = portService.findPortsPaginated(page);
        if(ports.size()<6){
            request.setAttribute("max",true);
            request.setAttribute("ports",ports);
        }else {
            request.setAttribute("max",false);
            request.setAttribute("ports",ports.subList(0,5));
        }
        request.setAttribute("page",page);
        return "ports.jsp";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.UNLOGGED;
    }
}
