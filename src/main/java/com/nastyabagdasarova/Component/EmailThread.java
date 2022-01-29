package com.nastyabagdasarova.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import com.nastyabagdasarova.Service.EmailService;


public class EmailThread extends Thread {
	
	private Integer id_of_user;
	private String to; 
	private String subject;
	private String text;
	private MultipartFile userfile;
	private String randomName;
	
	public EmailThread(Integer id_of_user, String to, String subject, String text, MultipartFile userfile, String randomName){
		this.id_of_user = id_of_user;
		this.userfile = userfile;
		this.to = to;
		this.subject = subject;
		this.text = text;
		this.userfile = userfile;
		this.randomName = randomName;
	}
	
	@Override
	public void run() {
		if(this.userfile == null) {
			try {
				sendSimpleMessageWithoutAttachment();
			} catch(MessagingException mex) {
				mex.getMessage();
			} catch(IOException ioex) {
				ioex.getMessage();
			}
		} else {
			try {
				sendMessageWithAttechment();
			} catch(MessagingException mex) {
				mex.getMessage();
			} catch(IOException ioex) {
				ioex.getMessage();
			}
		}
	}

	public void sendMessageWithAttechment()
			throws MessagingException, IOException {
		
		
    	EmailService emailService = new EmailService();
    	
    	File userFile = new File("public/mail/" + randomName +"." + userfile.getContentType().replace("video/", ""));
		
		try (OutputStream os = new FileOutputStream(userFile)) {
		    os.write(userfile.getBytes());
		}
		
		
		
    	JavaMailSender javaMailSender = emailService.getJavaMailSender();
    	MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper( mail, true );
        helper.setFrom("<info@13danceonline.com>");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText("text/html", text);
        
        helper.addAttachment(userFile.getName(), userFile);
        
        javaMailSender.send(mail);

		userFile.delete();
        
        javaMailSender = null;
        userFile = null;
        emailService = null;
        mail = null;
        helper = null;
        
        System.gc();
    }
	
	public void sendSimpleMessageWithoutAttachment() throws MessagingException, IOException {
		
    	EmailService emailService = new EmailService();
		
    	JavaMailSender javaMailSender = emailService.getJavaMailSender();
    	MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper( mail, true );
        helper.setFrom("<info@stasymad.com>");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText("text/html", text);
        
        javaMailSender.send(mail);
        
        javaMailSender = null;
        emailService = null;
        mail = null;
        helper = null;
        
        System.gc();
	
	}
}
