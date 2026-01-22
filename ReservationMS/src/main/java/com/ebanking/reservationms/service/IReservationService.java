package com.ebanking.reservationms.service;

import com.ebanking.reservationms.dto.StudentDTO;
import com.ebanking.reservationms.entities.Reservation;

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
