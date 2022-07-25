package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        try{String error= (String) request.getSession().getAttribute("error");
            if(error!=null){
                request.setAttribute("error",error);
                request.getSession().setAttribute("error",null);}

        }catch (Throwable e){
            throw new RuntimeException(e);
        }
        if(user==null)
            return "loginPage.jsp";
        else return "AccountPage.jsp";
    }
    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.UNLOGGED;
    }
}
