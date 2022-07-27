package com.example.cruisecompanyappservlet.util;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.dao.UserDAO;
import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;

import javax.servlet.http.HttpServletRequest;

public class SecurityUtil {
    public static boolean isAccessGranted(User user, Controller controller) {
        AccessLevel accessLevel = controller.accessLevel();
        if (user == null) {
            int ordinal = accessLevel.ordinal();
            return ordinal == 0;
        }
        return user.getUserType().ordinal() >= accessLevel.ordinal();
    }

    public static void insertUserInformation(HttpServletRequest request) throws DAOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            user = new UserDAO().findById(user.getId());
            request.setAttribute("isAdmin", user.isAdmin());
            request.setAttribute("userMoney", user.getMoney());
            request.setAttribute("userName", user.getName());
            request.setAttribute("isLogged",true);
        } else {
            request.setAttribute("isLogged",false);
            request.setAttribute("isAdmin", false);
            request.setAttribute("userMoney", "");
            request.setAttribute("userName", "");
        }
    }
}
