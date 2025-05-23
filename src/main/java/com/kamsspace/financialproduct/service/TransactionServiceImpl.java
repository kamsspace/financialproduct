package com.kamsspace.financialproduct.service;

import com.kamsspace.financialproduct.model.Transaction;
import com.kamsspace.financialproduct.model.User;
import com.kamsspace.financialproduct.repositories.TransactionRepository;
import com.kamsspace.financialproduct.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public void createTransaction(Transaction transaction, Long userId) {

        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        double balanceAfterTransaction = savedUser.getBalance();

        if ("credit".equalsIgnoreCase(transaction.getType())) {
            balanceAfterTransaction += transaction.getAmount();
        } else if ("debit".equalsIgnoreCase(transaction.getType())) {
            if (transaction.getAmount() > balanceAfterTransaction) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance is not enough");
            }

            balanceAfterTransaction -= transaction.getAmount();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Such transaction type does not exist");
        }

        savedUser.setBalance(balanceAfterTransaction);
        userRepository.save(savedUser);
        transaction.setUser(savedUser);
        transaction.setTime(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByUser_UserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        return transactionRepository.findByUser_UserId(userId);
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByUserUserIdAndTimeBetween(userId, startDate, endDate);
    }
}
