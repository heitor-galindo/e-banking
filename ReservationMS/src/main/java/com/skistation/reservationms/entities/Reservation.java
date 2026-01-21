package com.skistation.reservationms.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

/** The type Reservation. */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Reservation {

  /**
   * The Id reservation.
   */
@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long idReservation;

  /**
   * The Student id.
   */
@NonNull
  @Column(nullable = false)
  Long studentId;

  /**
   * The Creation date.
   */
@CreationTimestamp
  @Column(nullable = false, updatable = false)
  LocalDateTime creationDate;
}
