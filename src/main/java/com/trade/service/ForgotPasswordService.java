package com.trade.service;

import com.trade.domain.VerificationType;
import com.trade.model.ForgotPasswordToken;
import com.trade.model.User;

public interface ForgotPasswordService {
	
	ForgotPasswordToken createToken(User user, 
			                        String id, String otp,
			                        VerificationType verificationType,
			                        String sendTo);
	
	ForgotPasswordToken findById(String id);
	
	ForgotPasswordToken findByUser(Long userId);
	
	void deleteToken(ForgotPasswordToken token);

}
