package com.trade.service;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.domain.VerificationType;
import com.trade.model.User;
import com.trade.model.VerificationCode;
import com.trade.repository.VerificationCodeRepository;
import com.trade.utils.OtpUtils;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
	
	@Autowired
	private VerificationCodeRepository verificationCodeRepository;
	
	
	@Override
	public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
		VerificationCode verificationCode1=new VerificationCode();
		verificationCode1.setOtp(OtpUtils.generateOTP());
		verificationCode1.setVerificationType(verificationType);
		verificationCode1.setUser(user);
		
		return verificationCodeRepository.save(verificationCode1);
	}

	@Override
	public VerificationCode getVerificationCodeById(Long id) throws Exception {
		Optional<VerificationCode> verificationCode=
				verificationCodeRepository.findById(id);
		
		if(verificationCode.isPresent()) {
			return verificationCode.get();
		}
		throw new Exception("Verification Code Not Found");
	}

	@Override
	public VerificationCode getVerificationCodeByUser(Long userID) {
		return verificationCodeRepository.findByUserId(userID);
	}

	@Override
	public void deleteVerificationCodeById(VerificationCode verificationCode) {
		
		verificationCodeRepository.delete(verificationCode);
		
		
	}
	
	

}
