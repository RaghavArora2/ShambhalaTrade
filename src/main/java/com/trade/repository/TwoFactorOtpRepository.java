package com.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trade.model.TwoFactorOTP;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {
	TwoFactorOTP findByUserId(long userId);

}
