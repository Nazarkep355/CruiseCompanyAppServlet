package com.example.cruisecompanyappservlet;

import java.util.ListResourceBundle;

public class locale_en extends ListResourceBundle {
    private static final Object[][] contents = {
            {"cruiseCompany", "Cruise Company"},
            {"Remember me", "Remember me"},
            {"ByClickingSignUp", "By clicking Sign up, you agree to the terms of use."},
            {"SignUp", "Sign up"},
            {"EmailAddress", "Email address"},
            {"Password", "Password"},
            {"Home", "Home"},
            {"cruiseTime", "Cruise time"},
            {"IfYouDoNotHave", "If you do not have an account, you can"},
            {"Register", "Register"},
            {"Username", "Username"},
            {"emailInUse", "This email is already in use"},
            {"EnterWrongFormat", "You entered data in wrong format"},
            {"MustBeEmailFormatAndPassword", "Email must be correct format." +
                    " Password must contain capital, small letters and digits"},
            {"Cruises", "Cruises"},
            {"DepartureDate", "Departure date"},
            {"Cost", "Cost"},
            {"daysInJourney", "Days in journey"},
            {"Route", "Route"},
            {"SignOut", "Sign out"},
            {"goToFirstPage", "Go to first page"},
            {"ChangeMoney", "Change balance on your account"},
            {"PlanCruise", "Plan cruise"},
            {"EnterCapacity", "Enter capacity"},
            {"seats", "seats"},
            {"ChooseRoute", "Choose route"},
            {"Id", "Id"},
            {"ChooseStaff", "Choose staff"},
            {"Name", "Name"},
            {"Position", "Position"},
            {"EnterCostFor", "Enter cost for"},
            {"CruiseInfo", "Information about cruise"},
            {"FreePlaces", "Free places"},
            {"City", "City"},
            {"SendRequest", "Send request"},
            {"ChooseDoc", "Please choose your document"},
            {"Requests", "Requests"},
            {"Status", "Status"},
            {"Class", "Class"},
            {"Request", "Request"},
            {"UserBalance", "User's balance"},
            {"Document", "Document"},
            {"Accept", "Accept"},
            {"Cruise", "Cruise"},
            {"Decline", "Decline"},
            {"Tickets", "Tickets"},
            {"ReturningDate", "Returning date"},
            {"ActualOnly", "Actual only"},
            {"OnlyFreePlaces", "Only with free places"},
            {"Find","Find"},
            {"NoFreePlaces","No free places"},
            {"AddPort","Add port"},
            {"EnterCity","Enter city"},
            {"PortInDB","Port is already added"},
            {"CreateRoute","Create route"},
            {"PortNotFound","Port not found"},
            {"NumberOfPorts","Number of ports"},
            {"EnterDelay","Enter delay"},
            {"Ports","Ports"},
            {"AddStaff","Add staff"},
            {"EmployeeAlreadyAdded","This employee is already added"},
            {"Staff","Staff"},
            {"EnterAmount","Enter the amount added to the balance"}


    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}