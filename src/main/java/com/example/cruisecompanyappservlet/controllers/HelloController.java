package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloController implements Controller {
    Logger logger = Logger.getLogger(HelloController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
           try {
            String error = (String) request.getSession().getAttribute("error");
            if (error != null) {
                request.setAttribute("error", error);
                request.getSession().setAttribute("error", null);
            }
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                return "loginPage.jsp";
            } else {
                return "accountPage.jsp";
            }
        } catch (Throwable e) {
            logger.info(e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.UNLOGGED;
    }
}
