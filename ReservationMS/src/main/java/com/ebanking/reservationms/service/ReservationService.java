package com.ebanking.reservationms.service;

import com.ebanking.reservationms.configuration.ReservationEvent;
import com.ebanking.reservationms.dto.StudentDTO;
import com.ebanking.reservationms.entities.Reservation;
import com.ebanking.reservationms.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * The type Reservation service.
 */
@Slf4j
@Service
public class ReservationService implements IReservationService {

  private static final String TOPIC = "reservation-event";
  private final ReservationRepository reservationRepository;
  @Autowired private KafkaTemplate<String, ReservationEvent> kafkaTemplate;

  /**
   * Instantiates a new Reservation service.
   *
   * @param reservationRepository the reservation repository
   */
public ReservationService(ReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  @Override
  public Reservation addReservation(StudentDTO student) {
    Reservation reservation = reservationRepository.save(new Reservation(student.getId()));
    ReservationEvent event =
        new ReservationEvent(reservation.getIdReservation(), reservation.getStudentId());
    kafkaTemplate.send(TOPIC, event);

    return reservation;
  }
}
