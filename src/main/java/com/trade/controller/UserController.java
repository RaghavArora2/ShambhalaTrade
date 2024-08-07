package com.trade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.trade.domain.VerificationType;
import com.trade.model.User;
import com.trade.model.VerificationCode;
import com.trade.service.EmailService;
import com.trade.service.UserService;
import com.trade.service.VerificationCodeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VerificationCodeService verificationCodeService;
	
	@Autowired
	private EmailService emailService;
	private String jwt;
	
	@GetMapping("/api/users/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception{
		User user=userService.findUserProfileByJwt(jwt);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("/api/users/verification/{verificationType}/send-otp")
	public ResponseEntity<String> sendVerificationOtp(
			@RequestHeader("Authorization") String jwt,
			@PathVariable VerificationType verificationType) throws Exception{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		VerificationCode verificationCode=verificationCodeService.
				getVerificationCodeByUser(user.getId());
		
		if(verificationCode==null) {
			verificationCode=verificationCodeService.
					sendVerificationCode(user, verificationType);
		}
		if(verificationType.equals(VerificationType.EMAIL)) {
			emailService.sendVerificationOtpEmail(user.getEmail(),verificationCode.getOtp());
			
		}
		
		
		return new ResponseEntity<>("Verification Otp Sent Successfully", HttpStatus.OK);
	}
	
	@PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
	public ResponseEntity<User> enableTwoFactorAuthentication(
			@PathVariable String otp,
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user=userService.findUserProfileByJwt(jwt);
		
		VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUser(user.getId());
		
		String sendTo=verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
				verificationCode.getEmail():verificationCode.getMobile();
		
		boolean isVerified=verificationCode.getOtp().equals(otp);
		
		if(isVerified) {
			User updatedUser=userService.enableTwoFactorAuthentication(
					verificationCode.getVerificationType(), sendTo, user);
			
			verificationCodeService.deleteVerificationCodeById(verificationCode);
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		}
		throw new Exception("Wrong Otp");
	}
	

}
