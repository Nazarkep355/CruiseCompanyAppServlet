package com.example.cruisecompanyappservlet.util;

import com.example.cruisecompanyappservlet.entity.Protocol;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Stateless
@LocalBean
public class EmailSessionBean {
    //jbodebcuvfyfbicz

    private int port = 465;
    private String host = "smtp.gmail.com";
    private String from = "cruisemailer64";
    private boolean auth = true;
    private String username = "cruisemailer64";
    private String password1 = "Tr@inEmailer08";

    private String password = "jbodebcuvfyfbicz";
    private Protocol protocol = Protocol.SMTPS;
    private boolean debug = true;

    public void sendEmail(String to, String subject,String body) throws MessagingException {
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
    public void sendEmails(List<String> recipients, String subject, String body )throws MessagingException{
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
        for(int i =0;i<recipients.size();i++)
            addresses[i]=new InternetAddress(recipients.get(i));
        message.setRecipients(javax.mail.Message.RecipientType.TO, addresses);
        message.setSubject(subject);
        message.setSentDate(Date.from(Instant.now()));
        message.setText(body);
        Transport.send(message);

    }
}
