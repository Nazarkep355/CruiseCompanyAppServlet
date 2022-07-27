package com.example.cruisecompanyappservlet;

import java.util.ListResourceBundle;

public class locale_ua extends ListResourceBundle {
    private static final Object[][] contents = {
            {"cruiseCompany", "Круїзна компанія"},
            {"Remember me", "Запам'ятати мене"},
            {"ByClickingSignUp", "Натискаючи на Увійти, ви погоджуєтесь з умовами використання."},
            {"SignUp", "Увійти"},
            {"EmailAddress", "Адреса електронної пошти"},
            {"Password", "Пароль"},
            {"Home", "Домашня сторінка"},
            {"cruiseTime", "Час для круїзу"},
            {"IfYouDoNotHave", "Якщо у вас немає опобліковго запису, ви можете"},
            {"Register", "Зареєструватися"},
            {"Username", "Ім'я користувача"},
            {"emailInUse", "Дана електрона пошта використовуэться іншим користувачем"},
            {"EnterWrongFormat", "Ви ввели в невірному форматі"},
            {"MustBeEmailFormatAndPassword", "Електронна пошта має у " +
                    "вірному форматі, а пароль має містити великі," +
                    " маленькі, букви і цифри"},
            {"Cruises", "Круїзи"},
            {"DepartureDate", "Дата відплиття"},
            {"Cost", "Ціна"},
            {"daysInJourney", "Днів у подорожі"},
            {"Route", "Маршрут"},
            {"SignOut", "Вийти"},
            {"goToFirstPage", "Перейти до першої сторінки"},
            {"ChangeMoney", "Змінити кіслькість коштів"}

};
@Override
protected Object[][]getContents(){
        return contents;
        }
        }
