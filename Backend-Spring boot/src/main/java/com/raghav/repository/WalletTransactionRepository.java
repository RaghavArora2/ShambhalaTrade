package com.raghav.repository;

import com.raghav.domain.WalletTransactionType;
import com.raghav.model.Wallet;
import com.raghav.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction,Long> {

    List<WalletTransaction> findByWalletOrderByDateDesc(Wallet wallet);

}
