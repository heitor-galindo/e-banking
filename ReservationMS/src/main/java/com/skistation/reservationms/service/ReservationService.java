package com.skistation.reservationms.service;

import com.skistation.reservationms.configuration.ReservationEvent;
import com.skistation.reservationms.dto.StudentDTO;
import com.skistation.reservationms.entities.Reservation;
import com.skistation.reservationms.repository.ReservationRepository;
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
