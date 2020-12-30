package com.password.otp.controller;

import com.password.otp.service.MyEmailService;
import com.password.otp.service.OtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
public class OtpController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public OtpService otpService;
	
	@Autowired
	public MyEmailService myEmailService;

	//pag nag oopen sya ng otp page.html dito maglologin muna gmail account
	@GetMapping("/generateOtp")
	public String generateOtp(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		int otp = otpService.generateOTP(username);
		 
		logger.info("OTP : "+otp);
		
		//Generate The Template to send OTP
		Map<String,String> replacements = new HashMap<String,String>();
		replacements.put("user", username);
		replacements.put("otpnum", String.valueOf(otp));

			//put your gmail here
		myEmailService.sendOtpMessage("put your gmail here", "OTP -SpringBoot", otp);
		
		return "otppage";
	}
	
	@RequestMapping(value ="/validateOtp", method = RequestMethod.GET)
	public @ResponseBody
    String validateOtp(@RequestParam("otpnum") int otpnum){

		final String SUCCESS = "The Otp is valid";
		final String FAIL = "The Otp is invalid. Please Retry!";

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		System.out.println("Otp Number : "+otpnum);

		/*Validate the Otp */
			int serverOtp = otpService.getOtp(username);

				if(otpnum == serverOtp){
					System.out.println("your otpnmum and serverOtp is equal: "+otpnum+" == "+serverOtp);
					otpService.clearOTP(username);
					return SUCCESS;
				}else{
					return FAIL;
				}



	}
}
