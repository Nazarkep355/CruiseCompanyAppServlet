package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.UserService;
import com.example.cruisecompanyappservlet.util.ValidateUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterController implements Controller {
    private Logger logger = Logger.getLogger(RegisterController.class);
    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    public RegisterController() {
        userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        try {
            if (ValidateUtil.isEmailValid(email) && ValidateUtil.isPasswordValid(password)) {
                if (userService.isUserWithEmailExist(email)) {
                    request.getSession().setAttribute("error", "emailInUse");
                    return "/controller?controller=registerPage";
                }
                User user = userService.registerUser(email, password, name);
                request.getSession().setAttribute("user", user);
                return "/controller";
            } else {
                request.getSession().setAttribute("error", "EnterWrongFormat");
                return "/controller?controller=registerPage";
            }
        } catch (DAOException e) {
            logger.info(e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.UNLOGGED;
    }
}
