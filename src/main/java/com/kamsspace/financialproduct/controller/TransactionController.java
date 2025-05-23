package com.kamsspace.financialproduct.controller;

import com.kamsspace.financialproduct.model.Transaction;
import com.kamsspace.financialproduct.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/public/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/public/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping("/public/transactions")
    public ResponseEntity<String> createTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(transaction);
        return new ResponseEntity<>("Transaction added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUser(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.getTransactionsByUser(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("date-range")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(@RequestParam("startDate")LocalDateTime startDate,
                                                                        @RequestParam("endDate") LocalDateTime endDate) {
        List<Transaction> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
