package com.ebanking.accountms.dto.request;

import com.ebanking.accountms.enums.account.AccountType;
import com.ebanking.accountms.enums.account.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequest {

    private String userId;
    private AccountType accountType;
    private Currency currency;
}