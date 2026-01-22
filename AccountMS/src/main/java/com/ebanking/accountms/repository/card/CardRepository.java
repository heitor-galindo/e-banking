package com.ebanking.accountms.repository.card;

import com.ebanking.accountms.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByAccountId(String accountId);

    boolean existsByAccountId(Long accountId);
}