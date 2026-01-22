package com.ebanking.accountms.repository.account;

import com.ebanking.accountms.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/** The interface Reservation repository. */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByUserId(String userId);
}
