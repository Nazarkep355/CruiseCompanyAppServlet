package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.when;

public class ControllerTester {
    @Mock
    protected HttpServletRequest request;
    @Mock
    protected HttpServletResponse response;
    @Mock
    protected HttpSession session;
    protected Controller controller;

    public ControllerTester() {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
    }
}
