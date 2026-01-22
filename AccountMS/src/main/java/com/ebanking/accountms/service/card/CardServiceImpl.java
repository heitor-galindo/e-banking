package com.ebanking.accountms.service.card;

import com.ebanking.accountms.entities.Account;
import com.ebanking.accountms.entities.Card;
import com.ebanking.accountms.enums.account.AccountType;
import com.ebanking.accountms.enums.card.CardStatus;
import com.ebanking.accountms.exception.BusinessException;
import com.ebanking.accountms.repository.card.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements ICardService {

    private final CardRepository cardRepository;

    @Override
    public Optional<Card> createCardForAccount(Account account) {

        if (account.getAccountType() == AccountType.LOAN) {
            return Optional.empty();
        }

        if (cardRepository.existsByAccountId(account.getId())) {
            return Optional.empty();
        }

        Card card = new Card();
        card.setAccountId(account.getId());
        card.setStatus(CardStatus.ACTIVE);

        return Optional.of(cardRepository.save(card));
    }

    @Override
    public Card activateCard(String accountId) {
        Card card = cardRepository.findByAccountId(String.valueOf(accountId))
                .orElseThrow(() ->
                        new BusinessException("Card not found for this account")
                );

        card.setStatus(CardStatus.ACTIVE);
        return cardRepository.save(card);
    }

    @Override
    public Card deactivateCard(String accountId) {
        Card card = cardRepository.findByAccountId(accountId)
                .orElseThrow(() ->
                        new BusinessException("Card not found for this account")
                );
        card.setStatus(CardStatus.BLOCKED);
        return cardRepository.save(card);
    }

    @Override
    public Optional<Card> getByAccountId(String accountId) {
        return cardRepository.findByAccountId(accountId);
    }
}