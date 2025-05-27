package com.kamsspace.financialproduct.service;

import com.kamsspace.financialproduct.model.Transaction;
import com.kamsspace.financialproduct.payload.TransactionDTO;
import com.kamsspace.financialproduct.payload.TransactionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    TransactionResponse getAllTransactions();
    TransactionDTO createTransaction(TransactionDTO transactionDTO, Long userId);
    List<Transaction> getTransactionsByUser_UserId(Long userId);
    List<Transaction> getTransactionsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

}
