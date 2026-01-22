package com.ebanking.accountms.service.operation;

import com.ebanking.accountms.dto.request.AccountOperationRequestDTO;
import com.ebanking.accountms.dto.response.AccountOperationResponseDTO;
import com.ebanking.accountms.entities.Account;
import com.ebanking.accountms.enums.operation.TransactionStatus;
import com.ebanking.accountms.repository.account.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class AccountOperationService {

    private final AccountRepository accountRepository;

    @Transactional
    public AccountOperationResponseDTO process(AccountOperationRequestDTO req) {

        try {
            switch (req.getTransactionType()) {

                case DEPOSIT -> deposit(req);
                case WITHDRAWAL, CARD_PURCHASE -> withdraw(req);
                case INTERNAL_TRANSFER -> internalTransfer(req);
                case EXTERNAL_TRANSFER -> externalTransfer(req);

                default -> throw new IllegalArgumentException("Unsupported transaction type");
            }

            return AccountOperationResponseDTO.builder()
                    .transactionId(req.getTransactionId())
                    .transactionStatus(TransactionStatus.COMPLETED)
                    .userId(resolveUserId(req))
                    .sourceAccountId(req.getSourceAccount())
                    .destinationAccountId(req.getDestinationAccount())
                    .comment("Operation completed successfully | type=" + req.getTransactionType())
                    .build();

        } catch (Exception e) {
            return AccountOperationResponseDTO.builder()
                    .transactionId(req.getTransactionId())
                    .transactionStatus(TransactionStatus.REJECTED)
                    .userId(resolveUserId(req))
                    .sourceAccountId(req.getSourceAccount())
                    .destinationAccountId(req.getDestinationAccount())
                    .comment(e.getMessage())
                    .build();
        }
    }

    private void deposit(AccountOperationRequestDTO req) {
        Account dest = getAccount(req.getDestinationAccount());
        BigDecimal amount = BigDecimal.valueOf(req.getAmount());
        dest.setBalance(dest.getBalance().add(amount));
        accountRepository.save(dest);
    }

    private void withdraw(AccountOperationRequestDTO req) {
        Account src = getAccount(req.getSourceAccount());

        Double amount = req.getAmount();
        validateBalance(src, BigDecimal.valueOf(amount));

        src.setBalance(src.getBalance().subtract(BigDecimal.valueOf(amount)));
        accountRepository.save(src);
    }


    private void internalTransfer(AccountOperationRequestDTO req) {
        Account src = getAccount(req.getSourceAccount());
        Account dest = getAccount(req.getDestinationAccount());

        BigDecimal amount = BigDecimal.valueOf(req.getAmount());
        validateBalance(src, amount);

        src.setBalance(src.getBalance().subtract(amount));
        dest.setBalance(dest.getBalance().add(amount));

        accountRepository.save(src);
        accountRepository.save(dest);
    }

    private void externalTransfer(AccountOperationRequestDTO req) {
        Account src = getAccount(req.getSourceAccount());

        BigDecimal amount = BigDecimal.valueOf(req.getAmount());
        validateBalance(src, amount);

        src.setBalance(src.getBalance().subtract(amount));
        accountRepository.save(src);
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new RuntimeException("Account not found: " + accountId));
    }

    private void validateBalance(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException(
                    "Insufficient balance | userId=" + account.getUserId()
                            + " | accountId=" + account.getId()
            );
        }
    }

    private String resolveUserId(AccountOperationRequestDTO req) {

        if (req.getSourceAccount() != null) {
            return getAccount(req.getSourceAccount()).getUserId();
        }

        if (req.getDestinationAccount() != null) {
            return getAccount(req.getDestinationAccount()).getUserId();
        }

        return null;
    }

}