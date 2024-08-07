package com.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trade.model.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
	
	public VerificationCode findByUserId(Long userId);
}
