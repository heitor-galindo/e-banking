package com.skistation.reservationms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The type Reservation ms application.
 */
@SpringBootApplication
@EnableFeignClients
public class ReservationMsApplication {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(ReservationMsApplication.class, args);
  }
}
