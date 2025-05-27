package com.kamsspace.financialproduct.service;

import com.kamsspace.financialproduct.exception.APIException;
import com.kamsspace.financialproduct.exception.ResourceNotFoundException;
import com.kamsspace.financialproduct.model.Transaction;
import com.kamsspace.financialproduct.model.User;
import com.kamsspace.financialproduct.payload.TransactionDTO;
import com.kamsspace.financialproduct.payload.TransactionResponse;
import com.kamsspace.financialproduct.repositories.TransactionRepository;
import com.kamsspace.financialproduct.repositories.UserRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TransactionResponse getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        if (transactions.isEmpty()) {
            throw new APIException("No transaction created till now");
        }

        List<TransactionDTO> transactionDTOS = transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .toList();

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setContent(transactionDTOS);
        return transactionResponse;
    }

    @Override
    public void createTransaction(Transaction transaction, Long userId) {

        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

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
            throw new ResourceNotFoundException("User", "userId", userId);
        }
        return transactionRepository.findByUser_UserId(userId);
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "userId", userId);
        }
        return transactionRepository.findByUserUserIdAndTimeBetween(userId, startDate, endDate);
    }
}
