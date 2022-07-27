package com.example.cruisecompanyappservlet.util;

import com.example.cruisecompanyappservlet.dao.CruiseDAO;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.service.CruiseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RequestReader {
    public RequestReader() {
        cruiseService = new CruiseService();
    }

    static private CruiseService cruiseService;

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
}
