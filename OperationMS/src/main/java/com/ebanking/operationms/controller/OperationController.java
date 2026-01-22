package com.ebanking.operationms.controller;

import com.ebanking.operationms.dto.OperationRequestDTO;
import com.ebanking.operationms.service.OperationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/operations")
public class OperationController {

  @Autowired
  private OperationService operationService;

@PostMapping()
@PreAuthorize("hasRole('USER')")
  public ResponseEntity<Void> createOperation(@AuthenticationPrincipal Jwt jwt, @RequestBody OperationRequestDTO request) {
    String userId = jwt.getSubject();
    operationService.createNewOperation(request, userId);
    return ResponseEntity.ok().build();
  }

}
