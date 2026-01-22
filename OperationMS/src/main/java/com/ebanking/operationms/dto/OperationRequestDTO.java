package com.ebanking.operationms.dto;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OperationRequestDTO {
    private String userId;
    private Long cardId;
    private Long destinationAccount;
    private OperationType operationType;
    private BigDecimal operationAmount;
}
