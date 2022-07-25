package com.example.cruisecompanyappservlet;

import java.util.ListResourceBundle;

public class locale_en extends ListResourceBundle {
    private static final Object[][] contents ={
            {"cruiseCompany","Cruise Company"},
            {"Remember me","Remember me"},
            {"ByClickingSignUp","By clicking Sign up, you agree to the terms of use."},
            {"SignUp","Sign up"},
            {"EmailAddress","Email address"},
            {"Password","Password"},
            {"Home","Home"},
            {"cruiseTime","Cruise time"},
            {"IfYouDoNotHave","If you do not have an account, you can"},
            {"Register","Register"},
            {"Username","Username"}
    };
    @Override
    protected Object[][] getContents() {
        return contents;
    }
}