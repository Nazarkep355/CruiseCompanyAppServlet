package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.CruiseDAO;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.entity.Port;
import com.example.cruisecompanyappservlet.entity.RoomClass;
import com.example.cruisecompanyappservlet.entity.builders.PortBuilder;
import com.example.cruisecompanyappservlet.util.CruiseUtil;
import com.example.cruisecompanyappservlet.util.EmailSessionBean;

import javax.mail.MessagingException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws MessagingException {
        EmailSessionBean emailSessionBean = new EmailSessionBean();
        emailSessionBean.sendEmail("nazikforall@gmail.com","here","Take");
    }
}
