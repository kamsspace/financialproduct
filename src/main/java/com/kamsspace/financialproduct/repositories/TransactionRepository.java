package com.kamsspace.financialproduct.repositories;

import com.kamsspace.financialproduct.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByUser_UserId(Long userId);

    List<Transaction> findByUserUserIdAndTimeBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

}
