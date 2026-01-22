package com.ebanking.userms.client;

import com.ebanking.userms.events.OperationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OperationProducer {

  private static final String TOPIC = "operation-event";

  @Autowired private KafkaTemplate<String, OperationEvent> kafkaTemplate;

  public void sendOperation(OperationEvent operationEvent) {
    log.info("Sending operation event: {}", operationEvent);
    kafkaTemplate.send(TOPIC, operationEvent);
  }
}
