package com.ebanking.operationms.clients;

import com.ebanking.operationms.configuration.FeignClientConfigAccount;
import com.ebanking.operationms.dto.AccountOperationRequestDTO;
import com.ebanking.operationms.dto.AccountOperationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "ACCOUNTMS", configuration = FeignClientConfigAccount.class)
public interface AccountClient {

  @PostMapping("/account-operation")
  AccountOperationResponseDTO executeTransactionRequest(@RequestParam AccountOperationRequestDTO accountOperation);

}
