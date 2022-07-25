package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginPageController implements Controller {
    public LoginPageController(){

    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "loginPage.jsp";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.UNLOGGED;
    }
}
