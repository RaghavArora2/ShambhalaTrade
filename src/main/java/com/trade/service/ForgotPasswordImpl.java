package com.trade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.domain.VerificationType;
import com.trade.model.ForgotPasswordToken;
import com.trade.model.User;
import com.trade.repository.ForgotPasswordRepository;

@Service
public class ForgotPasswordImpl implements ForgotPasswordService {
	
	@Autowired
	private ForgotPasswordRepository passwordRepository;

	@Override
	public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType,
			String sendTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ForgotPasswordToken findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ForgotPasswordToken findByUser(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteToken(ForgotPasswordToken token) {
		// TODO Auto-generated method stub
		
	}

}
