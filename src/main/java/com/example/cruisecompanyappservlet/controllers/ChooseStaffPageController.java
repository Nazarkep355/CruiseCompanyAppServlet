package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.dao.StaffDAO;
import com.example.cruisecompanyappservlet.entity.Staff;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.StaffService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ChooseStaffPageController implements Controller {
    private StaffService staffService;

    public ChooseStaffPageController(StaffService staffService) {
        this.staffService = staffService;
    }

    public ChooseStaffPageController() {
        staffService = new StaffService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        int page = Integer.parseInt(request.getParameter("page"));
        int current = Integer.parseInt(request.getParameter("current"));
        int of = Integer.parseInt(request.getParameter("of"));
        String idStr = request.getParameter("id");
        if (idStr != null) {
            Long id = Long.parseLong(idStr);
            List<Staff> staffList = (List<Staff>) request.getSession().getAttribute("staffList");
            if (staffList == null||current==1) {
                staffList = new ArrayList<>();
                staffList.add(staffService.findById(id));
                request.getSession().setAttribute("staffList", staffList);
            } else {
                staffList.add(staffService.findById(id));
            }
        }
        if (current >= of)
            return "redirect:/controller?controller=planCruisePage";
        request.setAttribute("current", current);
        List<Staff> extract = staffService.findStaffPaginated(page);
        request.setAttribute("page", page);
        request.setAttribute("of", of);
        request.setAttribute("staff",extract);
        return "chooseStaff.jsp";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.ADMIN;
    }
}
