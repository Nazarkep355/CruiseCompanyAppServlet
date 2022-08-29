package com.example.cruisecompanyappservlet.util;

import com.example.cruisecompanyappservlet.dao.CruiseDAO;
import com.example.cruisecompanyappservlet.entity.*;
import com.example.cruisecompanyappservlet.entity.builders.CruiseBuilder;
import com.example.cruisecompanyappservlet.entity.builders.RouteBuilder;
import com.example.cruisecompanyappservlet.entity.builders.UserBuilder;
import com.example.cruisecompanyappservlet.service.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

public class RequestReader {
    public RequestReader() {
        cruiseService = new CruiseService();
        routeService = new RouteService();
        cruiseRequestService = new CruiseRequestService();
        userService = new UserService();
        portService = new PortService();
    }

    private static PortService portService;
    private static UserService userService;
    static private CruiseRequestService cruiseRequestService;
    static private CruiseService cruiseService;
    static private RouteService routeService;

    public static List<Cruise> getListOfCruisesFromRequest(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        String city = request.getParameter("city");
        String actual = request.getParameter("actual");
        String onlyFree = request.getParameter("freeOnly");
        Order ord = Order.valueOf(Optional.ofNullable(request.getParameter("order")).orElse("ASC").toUpperCase());
        String order = ord.name();
        request.setAttribute("order",order);
        Boolean onlyFreeBool = (Boolean.parseBoolean(onlyFree) ||
                !StringUtils.isEmpty(onlyFree) && onlyFree.equals("on"));

        Boolean actualBool = (Boolean.parseBoolean(actual) ||
                !StringUtils.isEmpty(actual) && actual.equals("on"));

        request.setAttribute("page", page);
        request.setAttribute("actual", actualBool);
        request.setAttribute("onlyFree", onlyFreeBool);

        if (!StringUtils.isBlank(city)) {
            request.setAttribute("city", city);
            List<Cruise> cruises = cruiseService.getCruisesByCity(city, page, order);
            if (cruises.size() < 6) {
                request.setAttribute("max", true);
                return cruises;
            }
            request.setAttribute("max", false);
            return cruises.subList(0, 5);

        }
        if (onlyFreeBool) {
            List<Cruise> cruises = cruiseService.getAllActualCruisesWithFreePlacesPaginated(page, order);
            if (cruises.size() < 6) {
                request.setAttribute("max", true);
                return cruises;
            }
            request.setAttribute("max", false);
            return cruises.subList(0, 5);
        }
        if (actualBool) {
            List<Cruise> cruises = cruiseService.getActualCruisesPaginated(page, order);
            if (cruises.size() < 6) {
                request.setAttribute("max", true);
                return cruises;
            }
            request.setAttribute("max", false);
            return cruises.subList(0, 5);
        }
        List<Cruise> cruises = cruiseService.getPaginated(page, order);
        if (cruises.size() < 6) {
            request.setAttribute("max", true);
            return cruises;
        }
        request.setAttribute("max", false);
        return cruises.subList(0, 5);

    }

    public static String saveImage(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        String fileName = +(int) (Math.random() * Integer.MAX_VALUE) + filePart.getSubmittedFileName();
        String fullName1 = request.getRealPath("\\images\\") + "\\" + fileName;
        for (Part part : request.getParts()) {
            part.write(fullName1);
        }
        return fileName;
    }

    public static Date getDateFromString(String dateString) {
        Date date = new Date();
        date.setTime(0);
        if (!dateString.contains("T")) {
            String[] segments = dateString.split("-");
            date.setYear(Integer.parseInt(segments[0]) - 1900);
            date.setMonth(Integer.parseInt(segments[1]) - 1);
            date.setDate(Integer.parseInt(segments[2]));
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
            return date;
        } else {
            String[] segments = dateString.split("T");
            String[] hoursMins = segments[1].split(":");
            segments = segments[0].split("-");
            date.setYear(Integer.parseInt(segments[0]) - 1900);
            date.setMonth(Integer.parseInt(segments[1]) - 1);
            date.setDate(Integer.parseInt(segments[2]));
            date.setHours(Integer.parseInt(hoursMins[0]));
            date.setMinutes(Integer.parseInt(hoursMins[1]));
            date.setSeconds(0);
            return date;
        }
    }

    public static Cruise createCruise(HttpServletRequest request) {
        List<Staff> staff = (List<Staff>) request.getSession().getAttribute("staffList");
        request.getSession().setAttribute("staffList", null);
        String stringDate = request.getParameter("date");
        Long routeId = Long.parseLong(request.getParameter("route"));
        int premium = Integer.parseInt(request.getParameter("premium"));
        int middle = Integer.parseInt(request.getParameter("middle"));
        int econom = Integer.parseInt(request.getParameter("econom"));
        int premiumCost = Integer.parseInt(request.getParameter("premiumCost"));
        int middleCost = Integer.parseInt(request.getParameter("middleCost"));
        int economCost = Integer.parseInt(request.getParameter("economCost"));
        int seats = premium + middle + econom;
        Route route = routeService.findRouteById(routeId);
        Date date = getDateFromString(stringDate);
        HashMap<RoomClass, Integer> classIntegerHashMap = new HashMap<>();
        classIntegerHashMap.put(RoomClass.PREMIUM, premium);
        classIntegerHashMap.put(RoomClass.MIDDLE, middle);
        classIntegerHashMap.put(RoomClass.ECONOM, econom);
        HashMap<Port, Date> schedule = CruiseUtil.calculateSchedule(date, route.getPorts(), route.getDelays());
        return new CruiseBuilder()
                .route(route)
                .schedule(schedule)
                .costPremium(premiumCost)
                .costMiddle(middleCost)
                .costEconom(economCost)
                .seats(seats)
                .freePlaces(classIntegerHashMap)
                .staff(staff)
                .status(Status.WAITING).build();
    }

    public static void setError(HttpServletRequest request) {
        String error = (String) request.getSession().getAttribute("error");
        request.setAttribute("error", error);
    }

    public static Route getRouteFromRequest(HttpServletRequest request) {
        int portNumber = Integer.parseInt(request.getParameter("portNumber"));
        List<Port> ports = new ArrayList<>();
        List<Integer> delays = new ArrayList<>();
        for (int i = 1; i <= portNumber; i++) {
            String city = request.getParameter("city" + i);
            if (!portService.isPortExists(city)) {
                return null;
            }
            ports.add(portService.findPortByCity(city));
            if (portNumber == i) continue;
            delays.add(Integer.parseInt(request.getParameter("delay" + i)));
        }
        return new RouteBuilder()
                .ports(ports)
                .delays(delays)
                .build();
    }

    public static List<CruiseRequest> getListOfRequestsFromRequest(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        request.setAttribute("page", page);
        String cruiseId = request.getParameter("cruise");
        String statusParam = request.getParameter("status");
        if (cruiseId != null && !cruiseId.equals("")) {
            request.setAttribute("id", cruiseId);
            Cruise cruise = new CruiseBuilder()
                    .id(Long.parseLong(cruiseId))
                    .build();
            if (statusParam != null && !statusParam.equals("")) {
                request.setAttribute("status", statusParam);
                Status status = Status.valueOf(statusParam);
                return cruiseRequestService.findByCruiseAndStatusPaginated(cruise, status, page);
            }
            return cruiseRequestService.findByCruisePaginated(cruise, page);
        }
        return cruiseRequestService.getPaginatedList(page);

    }
}
