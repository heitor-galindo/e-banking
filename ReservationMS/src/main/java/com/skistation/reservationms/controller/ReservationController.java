package com.skistation.reservationms.controller;

import com.skistation.reservationms.clients.StudentClient;
import com.skistation.reservationms.dto.StudentDTO;
import com.skistation.reservationms.entities.Reservation;
import com.skistation.reservationms.service.IReservationService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Reservation controller.
 */
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {

  @Autowired private StudentClient studentClient;

  @Autowired private IReservationService reservationService;

  /**
   * Gets reservations.
   *
   * @return  the reservations
   */
@GetMapping
  public ResponseEntity<List<StudentDTO>> getReservations() {
    List<StudentDTO> studentsWithReservations = studentClient.getStudentsWithReservations();
    return new ResponseEntity<>(studentsWithReservations, HttpStatus.OK);
  }

  /**
   * Add reservation response entity.
   *
   * @param studentId the student id
   * @return  the response entity
   */
@PostMapping
  public ResponseEntity<Reservation> addReservation(@RequestParam Long studentId) {
    StudentDTO student = studentClient.getStudentById(studentId);
    if (student == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Student not found with ID " + studentId);
    }
    log.info("Creating reservation for student: {}", student.getFirstName());
    Reservation reservation = reservationService.addReservation(student);

    return new ResponseEntity<>(reservation, HttpStatus.CREATED);
  }
}
