package com.ebanking.accountms.dto.response;
import com.ebanking.accountms.enums.operation.TransactionStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AccountOperationResponseDTO {

    private Long transactionId;
    private TransactionStatus transactionStatus;

    private String userId;
    private Long sourceAccountId;
    private Long destinationAccountId;

    private String comment;
}

