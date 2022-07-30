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
            {"Username","Username"},
            {"emailInUse", "This email is already in use"},
            {"EnterWrongFormat","You entered data in wrong format"},
            {"MustBeEmailFormatAndPassword","Email must be correct format." +
                    " Password must contain capital, small letters and digits"},
            {"Cruises","Cruises"},
            {"DepartureDate", "Departure date"},
            {"Cost","Cost"},
            {"daysInJourney", "Days in journey"},
            {"Route","Route"},
            {"SignOut","Sign out"},
            {"goToFirstPage","Go to first page"},
            {"ChangeMoney","Change cash on your account"},
            {"PlanCruise", "Plan cruise"},
            {"EnterCapacity","Enter capacity"},
            {"seats","seats"},
            {"ChooseRoute","Choose route"},
            {"Id","Id"},
            {"ChooseStaff","Choose staff"},
            {"Name","Name"},
            {"Position","Position"},
            {"EnterCostFor","Enter cost for"},
            {"CruiseInfo","Information about cruise"},
            {"FreePlaces","Free places"}


    };
    @Override
    protected Object[][] getContents() {
        return contents;
    }
}