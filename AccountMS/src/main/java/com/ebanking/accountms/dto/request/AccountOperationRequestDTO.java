package com.ebanking.accountms.dto.request;

import com.ebanking.accountms.enums.operation.TransactionType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AccountOperationRequestDTO {
    private Long transactionId;
    private TransactionType transactionType;
    private Long sourceAccount;
    private Long destinationAccount;
    private Double amount;
}
