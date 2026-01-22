package com.ebanking.accountms.controller;

import com.ebanking.accountms.dto.request.AccountOperationRequestDTO;
import com.ebanking.accountms.dto.request.CreateAccountRequest;
import com.ebanking.accountms.dto.response.AccountOperationResponseDTO;
import com.ebanking.accountms.entities.Account;
import com.ebanking.accountms.entities.Card;
import com.ebanking.accountms.exception.BusinessException;
import com.ebanking.accountms.service.account.IAccountService;
import com.ebanking.accountms.service.card.ICardService;
import com.ebanking.accountms.service.operation.AccountOperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final IAccountService accountService;
    private final ICardService cardService;
    private final AccountOperationService accountOperationService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Account createAccount(@RequestBody CreateAccountRequest request) {
        log.info(">> ENTER createAccount endpoint");
        log.info("Request received: userId={}, accountType={}, currency={}",
                request.getUserId(),
                request.getAccountType(),
                request.getCurrency()
        );
        return accountService.createAccount(
                request.getUserId(),
                request.getAccountType(),
                request.getCurrency()
        );
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    public List<Account> getUserAccounts(@PathVariable String userId) {
        return accountService.getAccountsByUser(userId);
    }

    @PostMapping("/{accountId}/card/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Card> activateCard(
            @PathVariable Long accountId
    ) {
        Card card = cardService.activateCard(String.valueOf(Long.valueOf(String.valueOf(accountId))));
        return ResponseEntity.ok(card);
    }

    @PostMapping("/{accountId}/card/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Card> deactivateCard(
            @PathVariable Long accountId
    ) {
        Card card = cardService.deactivateCard(String.valueOf(accountId));
        return  ResponseEntity.ok(card);
    }

    @GetMapping("/{accountId}/card")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Card> getCardByAccount(
            @PathVariable Long accountId
    ) {
        return cardService.getByAccountId(String.valueOf(accountId))
                .map(ResponseEntity::ok)
                .orElseThrow(() ->
                        new BusinessException("Card not found for this account")
                );
    }

    // OPERATIONS

    @PostMapping("/operation")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public AccountOperationResponseDTO operate(
            @RequestBody AccountOperationRequestDTO request
    ) {
        return accountOperationService.process(request);
    }
}
