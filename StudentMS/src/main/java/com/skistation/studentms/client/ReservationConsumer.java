package com.skistation.studentms.client;

import com.skistation.studentms.config.ReservationEvent;
import com.skistation.studentms.entities.Student;
import com.skistation.studentms.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * The type Reservation consumer.
 */
@Component
@Slf4j
public class ReservationConsumer {

  @Autowired private StudentRepository studentRepository;

  /**
   * Consume reservation.
   *
   * @param reservationEvent the reservation event
   */
@Transactional
  @KafkaListener(topics = "reservation-event", groupId = "reservation-group")
  public void consumeReservation(ReservationEvent reservationEvent) {
    log.info("Consumed reservation event: {}", reservationEvent);
    Student student = studentRepository.findById(reservationEvent.getStudentId()).orElse(null);
    if (student != null) {
      student.setReservationId(reservationEvent.getReservationId());
      studentRepository.save(student);
    }
  }
}
