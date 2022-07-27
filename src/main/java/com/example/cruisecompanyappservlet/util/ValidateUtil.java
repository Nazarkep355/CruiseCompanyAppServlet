package com.example.cruisecompanyappservlet.util;

import java.util.regex.Pattern;

public class ValidateUtil {
    public static boolean isEmailValid(String email){
        Pattern pattern = Pattern.compile("[\\w._]{2,25}@[\\w]{2,10}.[\\w.]{2,15}");
        return pattern.matcher(email).matches();
    }
    public static boolean isPasswordValid(String password){
        Pattern pattern = Pattern.compile("[\\w_@/.]{6,15}");
        if(!pattern.matcher(password).matches())
            return false;
        if(password.toUpperCase().equals(password)||password.toLowerCase().equals(password))
            return false;
        return !Pattern.compile("[A-Za-z]").matcher(password).matches();
    }

}
