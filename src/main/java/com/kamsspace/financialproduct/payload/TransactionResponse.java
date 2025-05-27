package com.kamsspace.financialproduct.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private List<TransactionDTO> content;
}
