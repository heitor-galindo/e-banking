package com.ebanking.operationms.entities;

import com.ebanking.operationms.dto.OperationType;
import com.ebanking.operationms.dto.OperationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private Long cardId;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    private BigDecimal amount;
    @CreationTimestamp
    private LocalDateTime transactionDate;
    @Enumerated(EnumType.STRING)
    private OperationStatus status;
    private String comment;
}

