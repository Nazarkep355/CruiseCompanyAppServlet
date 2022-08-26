package com.example.cruisecompanyappservlet.frontcontroller;

import com.example.cruisecompanyappservlet.controllers.*;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.entity.CruiseRequest;
import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.service.*;
import com.example.cruisecompanyappservlet.util.LocaleUtil;
import com.example.cruisecompanyappservlet.util.SecurityUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import javax.servlet.ServletContext;
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

    @Override
    public void init() {
        ServletContext context = getServletContext();
        CruiseRequestService cruiseRequestService = (CruiseRequestService) context.getAttribute("cruiseRequestService");
        CruiseService cruiseService = (CruiseService) context.getAttribute("cruiseService");
        PortService portService = (PortService) context.getAttribute("portService");
        RouteService routeService = (RouteService) context.getAttribute("routeService");
        StaffService staffService = (StaffService) context.getAttribute("staffService");
        TicketService ticketService = (TicketService) context.getAttribute("ticketService");
        UserService userService = (UserService) context.getAttribute("userService");


        controllers.put("changeToEn", new EnglishLocaleController());
        controllers.put("changeToUA", new UkrainianLocaleController());
        controllers.put("loginPage", new LoginPageController());
        controllers.put(null, new HelloController());
        controllers.put("registerPage", new RegisterPageController());
        controllers.put("register", new RegisterController(userService));
        controllers.put("login", new LoginController(userService));
        controllers.put("cruises", new CruisesController());
        controllers.put("signOut", new SignOutController());
        controllers.put("planCruisePage", new PlanCruisePageController(routeService));
        controllers.put("chooseStaff", new ChooseStaffPageController(staffService));
        controllers.put("numOfStaff", new ChooseNumOfStaffController());
        controllers.put("planCruise", new PlanCruiseController(cruiseService));
        controllers.put("cruiseInfo", new CruiseInfoController(cruiseService));
        controllers.put("sendRequestPage", new SendRequestPageController(cruiseService));
        controllers.put("sendRequest", new SendRequestController(cruiseService,cruiseRequestService));
        controllers.put("requests", new RequestsController(cruiseRequestService));
        controllers.put("requestInfo", new InfoAboutRequestController(cruiseRequestService));
        controllers.put("responseRequest", new ResponseRequestController(cruiseRequestService,cruiseService));
        controllers.put("tickets", new TicketsController(ticketService));
        controllers.put("addPort", new AddPortController(portService));
        controllers.put("addPortPage", new AddPortPageController());
        controllers.put("ports", new PortsController(portService));
        controllers.put("createRoutePage", new CreateRoutePageController());
        controllers.put("createRoute",new CreateRouteController(routeService));
        controllers.put("addStaffPage",new AddStaffPageController());
        controllers.put("addStaff",new AddStaffController(staffService));
        controllers.put("staff",new StaffController(staffService));
        controllers.put("changeMoneyPage",new ChangeMoneyPageController());
        controllers.put("changeMoney",new ChangeMoneyController(userService));
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
                if(address.startsWith("redirect:")){
                    address=address.split("rect:")[1];
                    response.sendRedirect(address);
                }else {
                    request.getRequestDispatcher(address).forward(request,response);
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
                if(redirect.startsWith("redirect:")){
                    redirect=redirect.split("rect:")[1];
                    response.sendRedirect(redirect);
                }else {
                    response.sendRedirect(redirect);
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
    public void destroy() {
    }


    boolean isNeedToRedirect(HttpServletRequest request) {
        if (request.getAttribute("redirect") != null) {
            return (boolean) request.getAttribute("redirect");
        }
        return false;
    }
}