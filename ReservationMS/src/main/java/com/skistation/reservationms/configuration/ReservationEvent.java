package com.skistation.reservationms.configuration;

import java.io.Serializable;
import lombok.*;

/** The type Reservation event. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationEvent implements Serializable {
  private Long reservationId;
  private Long studentId;
}
