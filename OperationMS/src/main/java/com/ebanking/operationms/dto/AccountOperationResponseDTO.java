package com.ebanking.operationms.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountOperationResponseDTO {
    private Long operationId;
    private Long accountId;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private OperationStatus OperationStatus;
    private String comment;
}
