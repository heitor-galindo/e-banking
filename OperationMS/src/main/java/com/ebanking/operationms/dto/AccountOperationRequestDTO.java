package com.ebanking.operationms.dto;

import java.math.BigDecimal;
import lombok.*;

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

