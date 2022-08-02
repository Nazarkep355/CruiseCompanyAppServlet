package com.example.cruisecompanyappservlet.frontcontroller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/images")
public class ImagesServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file = "C:\\Users\\Quant\\CruiseCompanyAppServlet\\src\\main\\WebContent\\images\\";
        Writer writer =resp.getWriter();
        file+= req.getParameter("file");
        try( FileInputStream stream = new FileInputStream(file)){

            byte data[] = new byte[1024];
            int count;

            while ((count = stream.read(data, 0, 1024)) != -1) {
                writer.write(String.valueOf(data), 0, 1024);
                writer.flush();
            }
        super.doGet(req, resp);
        }
    }
}
