package com.ebanking.userms.events;

import com.ebanking.userms.enums.OperationType;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OperationEvent implements Serializable {
  private Long userId;
  private OperationType operationType;
  private Long destinationAccount;
  private BigDecimal amount;
}
