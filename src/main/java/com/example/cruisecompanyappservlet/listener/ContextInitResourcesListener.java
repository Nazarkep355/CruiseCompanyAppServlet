package com.example.cruisecompanyappservlet.listener;

import com.example.cruisecompanyappservlet.dao.*;
import com.example.cruisecompanyappservlet.service.*;
import com.example.cruisecompanyappservlet.util.EmailSessionBean;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

@WebListener
public class ContextInitResourcesListener implements ServletContextListener {
    public ContextInitResourcesListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        ServletContext context = sce.getServletContext();
        initDAO(context);
        initServices(context);
    }

    @EJB
    private EmailSessionBean emailSessionBean = new EmailSessionBean();

    private void initDAO(ServletContext context) {
        try {
            emailSessionBean.setProps();
        }catch (IOException e){
            System.out.println("catched IO");
            System.out.println(e.getMessage());
        }
        PortDAO portDAO = new PortDAO();
        context.setAttribute("portDAO", portDAO);


        RouteDAO routeDAO = new RouteDAO((PortDAO) context.getAttribute("portDAO"));
        context.setAttribute("routeDAO", routeDAO);


        StaffDAO staffDAO = new StaffDAO();
        context.setAttribute("staffDAO", staffDAO);


        UserDAO userDAO = new UserDAO();
        context.setAttribute("userDAO", userDAO);


        CruiseDAO cruiseDAO = new CruiseDAO((StaffDAO) context.getAttribute("staffDAO"),
                (RouteDAO) context.getAttribute("routeDAO"));
        context.setAttribute("cruiseDAO", cruiseDAO);


        CruiseRequestDAO cruiseRequestDAO = new CruiseRequestDAO((CruiseDAO) context.getAttribute("cruiseDAO"),
                (UserDAO) context.getAttribute("userDAO"));
        context.setAttribute("cruiseRequestDAO", cruiseRequestDAO);

        TicketDAO ticketDAO = new TicketDAO((CruiseDAO) context.getAttribute("cruiseDAO"),
                (UserDAO) context.getAttribute("userDAO"));
        context.setAttribute("ticketDAO", ticketDAO);
    }

    private void initServices(ServletContext context) {
        CruiseDAO cruiseDAO = (CruiseDAO) context.getAttribute("cruiseDAO");
        CruiseRequestDAO cruiseRequestDAO = (CruiseRequestDAO) context.getAttribute("cruiseRequestDAO");
        PortDAO portDAO = (PortDAO) context.getAttribute("portDAO");
        RouteDAO routeDAO = (RouteDAO) context.getAttribute("routeDAO");
        StaffDAO staffDAO = (StaffDAO) context.getAttribute("staffDAO");
        TicketDAO ticketDAO = (TicketDAO) context.getAttribute("ticketDAO");
        UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");


        PortService portService = new PortService(portDAO);
        context.setAttribute("portService", portService);


        CruiseService cruiseService = new CruiseService(cruiseDAO);
        context.setAttribute("cruiseService", cruiseService);


        CruiseRequestService cruiseRequestService = new CruiseRequestService(cruiseRequestDAO, cruiseDAO, userDAO, ticketDAO, emailSessionBean);
        context.setAttribute("cruiseRequestService", cruiseRequestService);


        RouteService routeService = new RouteService(routeDAO);
        context.setAttribute("routeService", routeService);


        StaffService staffService = new StaffService(staffDAO);
        context.setAttribute("staffService", staffService);


        TicketService ticketService = new TicketService(ticketDAO);
        context.setAttribute("ticketService", ticketService);


        UserService userService = new UserService(userDAO);
        context.setAttribute("userService", userService);
    }


}
