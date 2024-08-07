package com.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trade.model.ForgotPasswordToken;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, Long>{
	
	ForgotPasswordToken findByUserId(long userId);

}
