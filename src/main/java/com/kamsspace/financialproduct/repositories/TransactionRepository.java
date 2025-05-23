package com.kamsspace.financialproduct.repositories;

import com.kamsspace.financialproduct.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
