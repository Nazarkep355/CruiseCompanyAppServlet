package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Staff;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.StaffService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class StaffController implements Controller {
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    private StaffService staffService;
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        int page = Integer.parseInt(request.getParameter("page"));
        List<Staff> staff = staffService.findStaffPaginated(page);
        request.setAttribute("page",page);
        request.setAttribute("staff",staff);
        return "staff.jsp";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.ADMIN;
    }
}
