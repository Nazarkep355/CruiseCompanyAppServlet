package com.example.cruisecompanyappservlet.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocaleUtil {
    public static void addLocale(HttpServletRequest request){
        String locale = (String) request.getSession().getAttribute("locale");
        if(locale==null)
            request.getSession().setAttribute("locale","en");
    }
}
