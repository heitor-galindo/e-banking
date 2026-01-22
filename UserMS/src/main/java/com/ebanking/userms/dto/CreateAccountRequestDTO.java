package com.ebanking.userms.dto;

import com.ebanking.userms.enums.account.AccountType;
import com.ebanking.userms.enums.account.Currency;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequestDTO {
  @NotNull private String userId;
  @NotEmpty private AccountType accountType;
  @NotEmpty private Currency currency;
}
