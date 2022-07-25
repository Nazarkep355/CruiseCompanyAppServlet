package com.example.cruisecompanyappservlet.frontcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    String execute(HttpServletRequest request, HttpServletResponse response);
    AccessLevel accessLevel();
}
