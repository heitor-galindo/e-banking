package com.ebanking.accountms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The type Reservation ms application.
 */
@SpringBootApplication
@EnableFeignClients
public class AccountMsApplication {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(AccountMsApplication.class, args);
  }
}
