package com.kamsspace.financialproduct.service;

import com.kamsspace.financialproduct.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransactions();
    void createTransaction(Transaction transaction);
    List<Transaction> getTransactionsByUser(Long userId);
    List<Transaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

}
