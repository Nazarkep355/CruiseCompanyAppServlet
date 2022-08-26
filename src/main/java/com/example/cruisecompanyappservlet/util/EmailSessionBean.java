package com.example.cruisecompanyappservlet.util;

import com.example.cruisecompanyappservlet.entity.CruiseRequest;
import com.example.cruisecompanyappservlet.entity.Protocol;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Stateless
@LocalBean
public class EmailSessionBean {

    public EmailSessionBean() {
        try {
            setProps();
        }catch (IOException e){}
    }

    public EmailSessionBean(String from, String username, String password) {
        this.from = from;
        this.username = username;
        this.password = password;
    }

    private int port = 465;
    private String host = "smtp.gmail.com";
    private String from = "cruisemailer64";
    private boolean auth = true;
    private String username = "cruisemailer64";
    private String password = "dsada";
    private Protocol protocol = Protocol.SMTPS;
    private boolean debug = true;

    public void setProps() throws IOException {
        Properties properties = new Properties();
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Quant\\CruiseCompanyAppServlet\\mail.properties"));
        properties.load(reader);
        from = properties.getProperty("username");
        username = properties.getProperty("username");
        port = Integer.parseInt(properties.getProperty("port"));
        auth = Boolean.parseBoolean(properties.getProperty("auth"));
        protocol = Protocol.valueOf(properties.getProperty("protocol"));
        password = properties.getProperty("password").trim();
        debug = Boolean.parseBoolean(properties.getProperty("debug"));
    }

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        switch (protocol) {
            case SMTPS:
                props.put("mail.smtp.ssl.enable", true);
                break;
            case TLS:
                props.put("mail.smtp.starttls.enable", true);
                break;
        }
        Authenticator authenticator = null;
        if (auth) {
            props.put("mail.smtp.auth", true);
            authenticator = new Authenticator() {
                private PasswordAuthentication pa = new PasswordAuthentication(username, password);

                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return pa;
                }
            };
        }
        Session session = Session.getInstance(props, authenticator);
        session.setDebug(debug);
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));
        InternetAddress[] address = {new InternetAddress(to)};
        message.setRecipients(javax.mail.Message.RecipientType.TO, address);
        message.setSubject(subject);
        message.setSentDate(Date.from(Instant.now()));
        message.setText(body);
        Transport.send(message);

    }

    public void sendEmails(List<String> recipients, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        switch (protocol) {
            case SMTPS:
                props.put("mail.smtp.ssl.enable", true);
                break;
            case TLS:
                props.put("mail.smtp.starttls.enable", true);
                break;
        }
        Authenticator authenticator = null;
        if (auth) {
            props.put("mail.smtp.auth", true);
            authenticator = new Authenticator() {
                private PasswordAuthentication pa = new PasswordAuthentication(username, password);

                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return pa;
                }
            };
        }
        Session session = Session.getInstance(props, authenticator);
        session.setDebug(debug);
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));
        InternetAddress[] addresses = new InternetAddress[recipients.size()];
        for (int i = 0; i < recipients.size(); i++)
            addresses[i] = new InternetAddress(recipients.get(i));
        message.setRecipients(javax.mail.Message.RecipientType.TO, addresses);
        message.setSubject(subject);
        message.setSentDate(Date.from(Instant.now()));
        message.setText(body);
        Transport.send(message);

    }

    public void sendMessageAboutAccepting(CruiseRequest request) throws MessagingException {
        StringBuilder message = new StringBuilder();
        message.append("Hello, ").append(request.getSender().getName() + ". Your request on cruise ")
                .append(request.getCruise().getRoute().routeToString() + " on " +
                        request.getCruise().getSchedule().get(request.getCruise().getRoute().getPorts().get(0)))
                .append(" was just accepted. So your account balance was changed." +
                        " If you didn't have enough money on the balance. " +
                        "We recommend you to add some until your balance will equal or more then 0." +
                        " Ticket about cruise was added to your account. You can check in 'Tickets' section." +
                        "Have a nice day. ");
        String subject = "Your request about cruise was accepted";
        sendEmail(request.getSender().getEmail(), subject, message.toString());
    }

    public void sendMessageAboutRefusing(CruiseRequest request) throws MessagingException {
        StringBuilder message = new StringBuilder();
        message.append("Hello, ").append(request.getSender().getName() + ". Your request on cruise ")
                .append(request.getCruise().getRoute().routeToString() + " on " +
                        request.getCruise().getSchedule().get(request.getCruise().getRoute().getPorts().get(0)))
                .append(" was just refused. We are sorry, but we can't explain you details");
        String subject = "Your request about cruise was refused";
        sendEmail(request.getSender().getEmail(), subject, message.toString());
    }
}
