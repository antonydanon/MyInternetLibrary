package com.ita.u1.internetLibrary.service;

import com.ita.u1.internetLibrary.Constants;

import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MessageSender {
    public static void sendEmail(String toEmail, String nameOfReader, List<String> titlesOfBooks, int price) {
        String fromEmail = Constants.fromEmail;
        String password = Constants.password;

        try {
            Properties property = new Properties();
            property.setProperty("mail.smtp.host", "smtp.gmail.com");
            property.setProperty("mail.smtp.port", "465");
            property.setProperty("mail.smtp.auth", "true");
            property.setProperty("mail.smtp.starttls.enable", "true");
            property.put("mail.smtp.socketFactory.port", "465");
            property.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(property, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            Message message = new MimeMessage(session);

            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(toEmail)});
            message.setSubject("Reminder");
            String messageForReader = getMessageForReader(nameOfReader, titlesOfBooks, price);
            message.setText(messageForReader);

            Transport.send(message);

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getMessageForReader(String nameOfReader, List<String> titlesOfBooks, int price){
        if(price == -1) {
            String messageForReader = "Hello, " + nameOfReader + ", we remind you that today is the last day of the return of books (";
            for (var title : titlesOfBooks) {
                messageForReader += title + ", ";
            }
            messageForReader = messageForReader.substring(0, messageForReader.length() - 2);
            messageForReader += ") to the library. Remember that a fine is charged for every next day of non-return. Have a nice day!";
            return messageForReader;
        } else {
            String messageForReader = "Hello, " + nameOfReader + ", you have overdue the return of books(";
            for (var title : titlesOfBooks) {
                messageForReader += title + ", ";
            }
            messageForReader = messageForReader.substring(0, messageForReader.length() - 2);
            messageForReader += ") to the library. The fine increases every day, the full price for books with a fine at the moment is ";
            messageForReader += price + "$ . Please return the books as soon as possible. Have a nice day!";
            return  messageForReader;
        }
    }
}
