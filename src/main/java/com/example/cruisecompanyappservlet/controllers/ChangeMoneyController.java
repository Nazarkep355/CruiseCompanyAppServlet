package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeMoneyController implements Controller {
    private UserService userService;

    public ChangeMoneyController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        int money = Integer.parseInt(request.getParameter("money"));
        User user = (User) request.getSession().getAttribute("user");
        userService.ChangeUserMoney(user,money);
        return "/controller";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.USER;
    }
}
