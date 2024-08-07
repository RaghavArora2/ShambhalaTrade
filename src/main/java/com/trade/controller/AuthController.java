package com.trade.controller;

import com.trade.config.JwtProvider;
import com.trade.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.trade.repository.UserRepository;
import com.trade.response.AuthResponse;
import com.trade.service.CustomUserDetailsService;
import com.trade.service.EmailService;
import com.trade.service.TwoFactorOtpService;
import com.trade.utils.OtpUtils;
import com.trade.model.TwoFactorOTP;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private TwoFactorOtpService twoFactorOtpService;
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception{
		
		User isEmailExist=userRepository.findByEmail(user.getEmail());
		
		if(isEmailExist!=null) {
			throw new Exception("Email Is Already Used With Another Account");
		}
		
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		newUser.setEmail(user.getEmail());
		newUser.setFullName(user.getFullName());
	
		User savedUser = userRepository.save(newUser);
		
		Authentication auth=new UsernamePasswordAuthenticationToken(
				user.getEmail(),
				user.getPassword()
		);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String jwt=JwtProvider.generateToken(auth);
		
		AuthResponse res=new AuthResponse();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("registered successfully!");
		
		return new ResponseEntity<>(res,HttpStatus.CREATED);
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception{
		
		String userName=user.getEmail();
		String password=user.getPassword();
		
		Authentication auth=authenticate(userName,password);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String jwt=JwtProvider.generateToken(auth);
		
		User authUser=userRepository.findByEmail(userName);
		
		if(user.getTwoFactorAuth().isEnabled()) {
			AuthResponse res=new AuthResponse();
			res.setMessage("Two Factor Auth is Enabled");
			res.setTwoFactorAuthEnabled(true);
			String otp= OtpUtils.generateOTP();
			
			TwoFactorOTP oldTwoFactorOTP=twoFactorOtpService.findByUser(authUser.getId());
			if(oldTwoFactorOTP!=null) {
				twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
			}
			
			TwoFactorOTP newTwoFactorOTP=twoFactorOtpService.createTwoFactorOtp(
					authUser, otp, jwt);
			
			emailService.sendVerificationOtpEmail(userName, otp);
			
			
			
			res.setSession(newTwoFactorOTP.getId());
			return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
		}
		
		AuthResponse res=new AuthResponse();
		res.setJwt(jwt);
		res.setStatus(true);
		res.setMessage("Login Successful!");
		
		return new ResponseEntity<>(res, HttpStatus.CREATED);
		
	}  

	private Authentication authenticate(String userName, String password) {
		UserDetails userDetails=customUserDetailsService.loadUserByUsername(userName);
		
		if(userDetails == null) {
			throw new BadCredentialsException("Invalid Username");
			
		}
		if(!password.equals(userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
	}
	
	@PostMapping("/two-factor/otp/{otp}")
	public ResponseEntity<AuthResponse> verifySignInOtp(
			@PathVariable String otp,
            @RequestParam String id) throws Exception{
		
		TwoFactorOTP twoFactorOTP=twoFactorOtpService.findById(id);
		
		if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP,otp)) {
			AuthResponse res=new AuthResponse();
			res.setMessage("Two Factor Authetication Verified");
			res.setTwoFactorAuthEnabled(true);
			res.setJwt(twoFactorOTP.getJwt());
			return new ResponseEntity<>(res, HttpStatus.OK);
			
		}
		throw new Exception("Invalid OTP");
	}
}
