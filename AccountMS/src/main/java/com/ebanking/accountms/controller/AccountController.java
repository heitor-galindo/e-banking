package com.ebanking.accountms.controller;

import com.ebanking.accountms.entities.Account;
import com.ebanking.accountms.repository.AccountRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/account")
public class AccountController {

  @Autowired private AccountRepository accountRepository;

  @GetMapping("/all")
  public ResponseEntity<List<Account>> getAllAccounts() {
    List<Account> accounts = accountRepository.findAll();
    return new ResponseEntity<>(accounts, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Account> getAccount(@PathVariable Long id) {
    Account account = accountRepository.findById(id).orElse(null);
    return new ResponseEntity<>(account, HttpStatus.OK);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Long> createAccount(
      @RequestParam String userId, @RequestParam String userName) {
    Account saved = accountRepository.save(new Account(userId, userName));
    return new ResponseEntity<>(saved.getId(), HttpStatus.CREATED);
  }
}
