package com.example.cruisecompanyappservlet.jstl;

import com.example.cruisecompanyappservlet.entity.Cruise;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class CruiseOut extends TagSupport {


    public int doStartTag() throws JspException {
        try {
            List<Cruise> cruises = (List<Cruise>) pageContext.getRequest().getAttribute("cruises");
            JspWriter writer = pageContext.getOut();
            for (Cruise cruise : cruises) {
                String startTr = String.format("<tr style=\"table-layout: fixed\"\n" +
                        " onclick=\"location.replace('/controller?controller=cruiseInfo&id=%s')\">", cruise.getId());
                writer.write(startTr);
                writer.write("<td scope=\"row\">" + cruise.departureDate() + "</td>");
                writer.write("<td scope=\"row\">" + cruise.daysInJourney() + "</td>");
                writer.write("<td scope=\"row\" style=\"max-height: 40px;max-width: 300px;overflow: auto\">");
                writer.write(cruise.getRoute().routeToString());
                writer.write("</td> </tr>");
            }
            return TagSupport.EVAL_BODY_INCLUDE;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


//     <tr style="table-layout: fixed"
//    onclick="location.replace('/controller?controller=cruiseInfo&id=${cruise.getId()}')">
//            <td scope="row">${cruise.departureDate()}</td>
//            <td scope="row">${cruise.daysInJourney()}</td>
//            <td scope="row" style="max-height: 40px;max-width: 300px;overflow: auto"
//            >${cruise.route.routeToString()}
//            </td>
//        </tr>
}