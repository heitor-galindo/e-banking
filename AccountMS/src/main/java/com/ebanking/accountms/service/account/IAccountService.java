package com.ebanking.accountms.service.account;

import com.ebanking.accountms.entities.Account;
import com.ebanking.accountms.enums.account.AccountType;
import com.ebanking.accountms.enums.account.Currency;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountService {

    Account createAccount(
            String userId,
            AccountType accountType,
            Currency currency
    );

    Account getAccountById(Long accountId);

    List<Account> getAccountsByUser(String userId);

    Account updateBalance(Long accountId, BigDecimal newBalance);

    Account closeAccount(Long accountId);
}
