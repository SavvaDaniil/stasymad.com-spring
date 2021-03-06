package com.nastyabagdasarova.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class EmailService  {
	
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.stasymad.com");
        mailSender.setPort(465);
        
        mailSender.setUsername("XXXXXXXXX");
        mailSender.setPassword("XXXXXXXXX");
        mailSender.setDefaultEncoding("UTF-8");
        
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");//игнорирует сертификат на стороне приложения
        
        		
        return mailSender;
    }
    
    public void sendSimpleMessage(String to, String subject, String text) throws MessagingException, IOException {
    	
    	
    	JavaMailSender javaMailSender = getJavaMailSender();
    	
    	final MimeMessage mail = javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper( mail, true );
        helper.setFrom("<info@13danceonline.com>");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText("text/html", text);
        javaMailSender.send(mail);
    	
        
    }

    
    public void sendMessageWithAttechment(String to, String subject, String text, MultipartFile userfile, String randomName) throws MessagingException, IOException {
    	
    	File userFile = new File("uploads/mail/" + randomName +"." + userfile.getContentType().replace("video/", ""));
		
		try (OutputStream os = new FileOutputStream(userFile)) {
		    os.write(userfile.getBytes());
		}
    	
    	
    	JavaMailSender javaMailSender = getJavaMailSender();
    	final MimeMessage mail = javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper( mail, true );
        helper.setFrom("<info@13danceonline.com>");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText("text/html", text);
        
        helper.addAttachment(userfile.getName(), userFile);
        
        javaMailSender.send(mail);
        
        userFile.delete();
    }
}
