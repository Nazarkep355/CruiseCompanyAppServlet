package com.example.cruisecompanyappservlet.frontcontroller;

import com.example.cruisecompanyappservlet.controllers.HelloController;
import com.example.cruisecompanyappservlet.controllers.LoginPageController;
import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.util.SecurityUtil;

import java.io.*;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/")
public class MainServlet extends HttpServlet {
    private HashMap<String, Controller> controllers = new HashMap<>();


    public void init() {
        controllers.put("loginPage", new LoginPageController());
        controllers.put(null, new HelloController());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String controllerName = request.getParameter("controller");
        User user = (User) request.getSession().getAttribute("user");
        Controller controller = controllers.get(controllerName);
        if (SecurityUtil.isAccessGranted(user, controller)) {
            String address = controller.execute(request, response);
            if (isNeedToRedirect(request)) {
                response.sendRedirect(address);
            } else {
                request.getRequestDispatcher(address).forward(request, response);
            }
        } else {
            response.sendRedirect("/");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String controllerName = request.getParameter("controller");
        User user = (User) request.getSession().getAttribute("user");
        Controller controller = controllers.get(controllerName);
        if (SecurityUtil.isAccessGranted(user, controller)) {
            String redirect = controller.execute(request, response);
            response.sendRedirect(redirect);
        } else {
            response.sendRedirect("/");
        }
    }

    @Override
    public void destroy() {
    }


    boolean isNeedToRedirect(HttpServletRequest request) {
        if (request.getAttribute("redirect") != null)
            return (boolean) request.getAttribute("redirect");
        return false;
    }
}