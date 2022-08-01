package com.example.cruisecompanyappservlet.util;

import com.example.cruisecompanyappservlet.dao.CruiseDAO;
import com.example.cruisecompanyappservlet.entity.*;
import com.example.cruisecompanyappservlet.entity.builders.CruiseBuilder;
import com.example.cruisecompanyappservlet.service.CruiseService;
import com.example.cruisecompanyappservlet.service.RouteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RequestReader {
    public RequestReader() {
        cruiseService = new CruiseService();
        routeService = new RouteService();
    }

    static private CruiseService cruiseService;
    static private RouteService routeService;

    public static List<Cruise> getListOfCruisesFromRequest(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        String city = request.getParameter("city");
        String actual = request.getParameter("actual");
        String onlyFree = request.getParameter("freeOnly");
        request.setAttribute("page", page);
        request.setAttribute("actual", Boolean.parseBoolean(actual));
        request.setAttribute("onlyFree", Boolean.parseBoolean(onlyFree));
        if (city != null && !city.equals("")) {
            request.setAttribute("city", city);
            return cruiseService.getCruisesByCity(city, page);
        }
        if (Boolean.parseBoolean(onlyFree)) {
            return cruiseService.getAllActualCruisesWithFreePlacesPaginated(page);
        }
        if (Boolean.parseBoolean(actual)) {
            return cruiseService.getActualCruisesPaginated(page);
        } else
            return cruiseService.getPaginated(page);


    }
    public static String saveImage(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        String fullName = "C:\\Users\\Quant\\CruiseCompanyAppServlet\\src\\main\\images\\" +(int)(Math.random()*Integer.MAX_VALUE)+fileName;
        for (Part part : request.getParts()) {
            part.write(fullName);
        }
        return fullName;
    }
    public static Date getDateFromString(String dateString){
        Date date= new Date();
        date.setTime(0);
        if(!dateString.contains("T")){
            String[]segments = dateString.split("-");
            date.setYear(Integer.parseInt(segments[0]));
            date.setMonth(Integer.parseInt(segments[1])-1);
            date.setDate(Integer.parseInt(segments[2]));
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
            return date;
        }else {
            String[] segments= dateString.split("T");
            String[] hoursMins=segments[1].split(":");
            segments = segments[0].split("-");
            date.setYear(Integer.parseInt(segments[0]));
            date.setMonth(Integer.parseInt(segments[1])-1);
            date.setDate(Integer.parseInt(segments[2]));
            date.setHours(Integer.parseInt(hoursMins[0]));
            date.setMinutes(Integer.parseInt(hoursMins[1]));
            date.setSeconds(0);
            return date;}
    }
    public static Cruise createCruise(HttpServletRequest request){
        List<Staff> staff = (List<Staff>) request.getSession().getAttribute("staffList");
        request.getSession().setAttribute("staffList",null);
        String stringDate = request.getParameter("date");
        Long routeId = Long.parseLong(request.getParameter("route"));
        int premium = Integer.parseInt(request.getParameter("premium"));
        int middle = Integer.parseInt(request.getParameter("middle"));
        int econom = Integer.parseInt(request.getParameter("econom"));
        int premiumCost =Integer.parseInt(request.getParameter("premiumCost"));
        int middleCost =Integer.parseInt(request.getParameter("middleCost"));
        int economCost =Integer.parseInt(request.getParameter("economCost"));
        int seats = premium+middle+econom;
        Route route = routeService.findRouteById(routeId);
        Date date = getDateFromString(stringDate);
        HashMap<RoomClass,Integer> classIntegerHashMap = new HashMap<>();
        classIntegerHashMap.put(RoomClass.PREMIUM,premium);
        classIntegerHashMap.put(RoomClass.MIDDLE,middle);
        classIntegerHashMap.put(RoomClass.ECONOM,econom);
        HashMap<Port,Date> schedule = CruiseUtil.calculateSchedule(date,route.getPorts(),route.getDelays());
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
}
