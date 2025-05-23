package com.kamsspace.financialproduct.controller;

import com.kamsspace.financialproduct.model.Transaction;
import com.kamsspace.financialproduct.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping("user/{userId}/transactions")
    public ResponseEntity<String> createTransaction(@RequestBody Transaction transaction, @PathVariable Long userId) {
        transactionService.createTransaction(transaction, userId);
        return new ResponseEntity<>("Transaction added successfully", HttpStatus.CREATED);
    }

    @GetMapping("by/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUser(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.getTransactionsByUser_UserId(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/by-date")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
            @PathVariable Long userId,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {
        List<Transaction> transactions = transactionService.getTransactionsByDateRange(userId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
