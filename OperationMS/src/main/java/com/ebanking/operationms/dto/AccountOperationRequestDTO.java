package com.ebanking.operationms.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * The type Student dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AccountOperationRequestDTO {
    private Long operationId;
    private String userId;
    private OperationType operationType;
    private Long destinationAccount;
    private BigDecimal amount;
}

