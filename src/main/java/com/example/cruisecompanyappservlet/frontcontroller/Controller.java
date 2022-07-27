package com.example.cruisecompanyappservlet.frontcontroller;

import com.example.cruisecompanyappservlet.dao.DAOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException;
    AccessLevel accessLevel();
}
