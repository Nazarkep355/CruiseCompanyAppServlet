package com.example.cruisecompanyappservlet.filter;


import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.dao.UserDAO;
import com.example.cruisecompanyappservlet.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/controller")
public class UpdateUserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute("userDAO");
            try {
                user = userDAO.findByEmail(user.getEmail());
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
            request.getSession().setAttribute("user",user);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
