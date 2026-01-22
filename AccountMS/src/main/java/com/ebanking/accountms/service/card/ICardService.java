package com.ebanking.accountms.service.card;

import com.ebanking.accountms.entities.Account;
import com.ebanking.accountms.entities.Card;

import java.util.Optional;

public interface ICardService {

    Optional<Card> createCardForAccount(Account account);

    Card activateCard(String accountId);

    Card deactivateCard(String accountId);

    Optional<Card> getByAccountId(String accountId);
}