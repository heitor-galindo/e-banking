package com.example.userms.client;

import com.example.userms.config.AccountEvent;
import com.example.userms.entities.User;
import com.example.userms.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/** The type Account consumer. */
@Component
@Slf4j
public class AccountConsumer {

  @Autowired private UserRepository userRepository;

  /**
   * Consume account event.
   *
   * @param accountEvent the account event
   */
  @Transactional
  @KafkaListener(topics = "reservation-event", groupId = "reservation-group")
  public void consumeAccount(AccountEvent accountEvent) {
    log.info("Consumed account event: {}", accountEvent);
    User user = userRepository.findById(accountEvent.getUserId()).orElse(null);
    if (user != null) {
      user.setAccountID(accountEvent.getAccountId());
      userRepository.save(user);
    }
  }
}