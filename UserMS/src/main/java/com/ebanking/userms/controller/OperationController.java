package com.ebanking.userms.controller;

import com.ebanking.userms.client.OperationProducer;
import com.ebanking.userms.events.OperationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/operations")
@RequiredArgsConstructor
public class OperationController {

  private final OperationProducer operationProducer;

  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Void> createOperation(@RequestBody OperationEvent operationEvent) {
    operationProducer.sendOperation(operationEvent);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }
}
