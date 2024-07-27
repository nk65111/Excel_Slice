package com.parser.excel.service;

public interface EmailService {
    
    void sendEmailWithAttachment(String toEmail, String subject, String text, byte[] attachmentData, String attachmentName);
}
