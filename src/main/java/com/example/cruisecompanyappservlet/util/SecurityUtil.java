package com.example.cruisecompanyappservlet.util;

import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;

public class SecurityUtil {
    public static boolean isAccessGranted(User user, Controller controller) {
        AccessLevel accessLevel = controller.accessLevel();
        if (user == null) {
            int ordinal = accessLevel.ordinal();
            return ordinal == 0;
        }
        return user.getUserType().ordinal() + 1 < accessLevel.ordinal();
    }
}
