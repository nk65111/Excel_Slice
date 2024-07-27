package com.parser.excel.service;


import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.activation.*;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class EmailServiceImpl  implements EmailService{

    
  
    
    @Value("${mail.username}")
    private String mailUsername;
    
    @Value("${mail.app.password}")
    private String mailAppPassword;
    
    @Value("${mail.email.id}")
    private String from;
     
    
    @Override
    public void sendEmailWithAttachment(String  toEmail, String subject, String text, byte[] attachmentContent, String attachmentName) {

      
        boolean flag = false;
        
        // SMTP properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");

       
       

        // Create a Session object
    Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {
        @Override
        protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
            return new jakarta.mail.PasswordAuthentication(mailUsername, mailAppPassword);
        }
    });

        try {

            InternetAddress to = new InternetAddress(toEmail);
            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, to);
            message.setFrom(new InternetAddress(from, "Nitin"));
            message.setSubject(subject);

            // Create the message part 
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(text, "text/html");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Part two is the attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(attachmentContent, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attachmentName);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            Transport.send(message);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

     
    }



}