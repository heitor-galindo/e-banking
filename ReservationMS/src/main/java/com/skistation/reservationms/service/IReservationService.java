package com.skistation.reservationms.service;

import com.skistation.reservationms.dto.StudentDTO;
import com.skistation.reservationms.entities.Reservation;

/**
 * The interface Reservation service.
 */
public interface IReservationService {
  /**
   * Add reservation reservation.
   *
   * @param student the student
   * @return  the reservation
   */
Reservation addReservation(StudentDTO student);
}
