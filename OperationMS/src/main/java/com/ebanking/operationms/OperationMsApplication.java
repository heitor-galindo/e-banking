package com.ebanking.operationms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OperationMsApplication {

  public static void main(String[] args) {
    SpringApplication.run(OperationMsApplication.class, args);
  }
}
