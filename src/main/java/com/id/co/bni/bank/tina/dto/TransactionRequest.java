package com.id.co.bni.bank.tina.dto;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class TransactionRequest {
    private String noRekSource;
    private String noRekDestination;
    private double amount;
}
