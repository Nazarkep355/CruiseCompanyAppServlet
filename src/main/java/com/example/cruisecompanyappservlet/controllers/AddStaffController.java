package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.StaffService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddStaffController implements Controller {
    private StaffService staffService;

    public AddStaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        String name = request.getParameter("name");
        String position = request.getParameter("position");
        if(staffService.isStaffWithNameExists(name)){
            request.getSession().setAttribute("error","EmployeeAlreadyAdded");
            return "/controller?controller=addStaffPage";
        }
        else staffService.insert(name,position);
        return "/controller?controller=staff&page=1";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.ADMIN;
    }
}
