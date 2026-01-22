package com.ebanking.userms.client;

import com.ebanking.userms.configuration.FeignClientConfigAccount;
import com.ebanking.userms.dto.AccountDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ACCOUNTMS", configuration = FeignClientConfigAccount.class)
public interface AccountClient {

    @PostMapping()
    AccountDTO createAccount(@RequestBody AccountDTO accountDTO);
}