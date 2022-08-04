package com.example.cruisecompanyappservlet.frontcontroller;

import com.example.cruisecompanyappservlet.controllers.*;
import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.util.LocaleUtil;
import com.example.cruisecompanyappservlet.util.SecurityUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/controller")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class MainServlet extends HttpServlet {
    private HashMap<String, Controller> controllers = new HashMap<>();
    private Logger logger = Logger.getLogger(MainServlet.class);

    public void init() {
        controllers.put("changeToEn", new EnglishLocaleController());
        controllers.put("changeToUA", new UkrainianLocaleController());
        controllers.put("loginPage", new LoginPageController());
        controllers.put(null, new HelloController());
        controllers.put("registerPage", new RegisterPageController());
        controllers.put("register", new RegisterController());
        controllers.put("login", new LoginController());
        controllers.put("cruises", new CruisesController());
        controllers.put("signOut", new SignOutController());
        controllers.put("planCruisePage", new PlanCruisePageController());
        controllers.put("chooseStaff", new ChooseStaffPageController());
        controllers.put("numOfStaff", new ChooseNumOfStaffController());
        controllers.put("planCruise", new PlanCruiseController());
        controllers.put("cruiseInfo", new CruiseInfoController());
        controllers.put("sendRequestPage", new SendRequestPageController());
        controllers.put("sendRequest", new SendRequestController());
        controllers.put("requests", new RequestsController());
        controllers.put("requestInfo", new InfoAboutRequestController());
        controllers.put("responseRequest", new ResponseRequestController());
        controllers.put("tickets", new TicketsController());
        controllers.put("addPort", new AddPortController());
        controllers.put("addPortPage", new AddPortPageController());
        controllers.put("ports", new PortsController());
        controllers.put("createRoutePage", new CreateRoutePageController());
        controllers.put("createRoute",new CreateRouteController());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            SecurityUtil.insertUserInformation(request);
            LocaleUtil.addLocale(request);
            String controllerName = request.getParameter("controller");
            User user = (User) request.getSession().getAttribute("user");
            Controller controller = controllers.get(controllerName);
            System.out.println("get" + SecurityUtil.isAccessGranted(user, controller));
            if (SecurityUtil.isAccessGranted(user, controller)) {
                String address = controller.execute(request, response);
                if (isNeedToRedirect(request)) {
                    response.sendRedirect(address);
                } else {
                    request.getRequestDispatcher(address).forward(request, response);
                }

            } else {
                response.sendRedirect("/controller");
            }
        } catch (Throwable e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            SecurityUtil.insertUserInformation(request);
            String controllerName = request.getParameter("controller");
            User user = (User) request.getSession().getAttribute("user");
            Controller controller = controllers.get(controllerName);
            System.out.println("post" + SecurityUtil.isAccessGranted(user, controller));
            if (SecurityUtil.isAccessGranted(user, controller)) {
                String redirect = controller.execute(request, response);
                response.sendRedirect(redirect);
            } else {
                response.sendRedirect("/controller");
            }
        } catch (Throwable e) {
            logger.info(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
    }


    boolean isNeedToRedirect(HttpServletRequest request) {
        if (request.getAttribute("redirect") != null) {
            return (boolean) request.getAttribute("redirect");
        }
        return false;
    }
}