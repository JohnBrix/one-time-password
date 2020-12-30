package com.password.otp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

//ito lng need mo at otp controller at Otp service
@Service
public class MyEmailService  {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaMailSender javaMailSender;
	 
	public void sendOtpMessage(String to, String subject, int message) {
		 
		 SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		 simpleMailMessage.setTo(to); 
		 simpleMailMessage.setSubject(subject); 
		 simpleMailMessage.setText(String.valueOf("Good Day!,\n \n "+to+" Your OTP Number is: "+message));
		 
		 logger.info(subject);
		 logger.info(to);
		 logger.info(String.valueOf(message));
		 
		 //Uncomment to send mail
		 javaMailSender.send(simpleMailMessage);
	}
}
