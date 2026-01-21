package com.skistation.studentms.config;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.*;

/** The type Reservation event. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationEvent implements Serializable {
  @NotNull private Long reservationId;
  @NotNull private Long studentId;
}
