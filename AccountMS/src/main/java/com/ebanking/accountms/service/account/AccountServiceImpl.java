package com.ebanking.accountms.service.account;

import com.ebanking.accountms.entities.Account;
import com.ebanking.accountms.enums.account.AccountStatus;
import com.ebanking.accountms.enums.account.AccountType;
import com.ebanking.accountms.enums.account.Currency;
import com.ebanking.accountms.event.AccountCreatedEvent;
import com.ebanking.accountms.repository.account.AccountRepository;
import com.ebanking.accountms.service.card.ICardService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

  private final AccountRepository accountRepository;
  private static final String TOPIC = "account-created";
  private final KafkaTemplate<String, AccountCreatedEvent> kafkaTemplate;
  private final ICardService cardService;

  @Override
  @Transactional
  public Account createAccount(String userId, AccountType accountType, Currency currency) {
    try {

      Account account =
          Account.builder()
              .userId(userId)
              .accountType(accountType)
              .currency(currency)
              .balance(BigDecimal.ZERO)
              .status(AccountStatus.ACTIVE)
              .build();

      Account saved = accountRepository.save(account);

      cardService.createCardForAccount(saved);

      AccountCreatedEvent event = new AccountCreatedEvent(saved.getId(), saved.getUserId());

      kafkaTemplate.send(TOPIC, event);

      return saved;
    } catch (Exception e) {
      String msg = "Encountered error in createAccount: " + e.getMessage();
      log.error(msg, e);
      throw new RuntimeException(msg);
    }
  }

  @Override
  public Account getAccountById(Long accountId) {
    return accountRepository
        .findById(accountId)
        .orElseThrow(() -> new RuntimeException("Account not found"));
  }

  @Override
  public List<Account> getAccountsByUser(String userId) {
    return accountRepository.findByUserId(userId);
  }

  @Override
  public Account updateBalance(Long accountId, BigDecimal newBalance) {
    Account account = getAccountById(accountId);
    account.setBalance(newBalance);
    return accountRepository.save(account);
  }

  @Override
  public Account closeAccount(Long accountId) {
    Account account = getAccountById(accountId);
    account.setStatus(AccountStatus.CLOSED);
    return accountRepository.save(account);
  }
}
