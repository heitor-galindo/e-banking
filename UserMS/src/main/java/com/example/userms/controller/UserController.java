package com.skistation.studentms.controller;

import com.skistation.studentms.entities.Student;
import com.skistation.studentms.repository.StudentRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The type Student controller.
 */
@Slf4j
@RestController
@RequestMapping(path = "/students")
public class StudentController {

  @Autowired private StudentRepository studentRepository;

  /**
   * Create student response entity.
   *
   * @param student the student
   * @return  the response entity
   */
@PostMapping
  public ResponseEntity<Student> createStudent(@RequestBody Student student) {
    Student saved = studentRepository.save(student);
    return new ResponseEntity<>(saved, HttpStatus.CREATED);
  }

  /**
   * Gets all students.
   *
   * @return  the all students
   */
@GetMapping("/all")
  public ResponseEntity<List<Student>> getAllStudents() {
    List<Student> students = studentRepository.findAll();
    return new ResponseEntity<>(students, HttpStatus.OK);
  }

  /**
   * Gets students with reservations.
   *
   * @return  the students with reservations
   */
@GetMapping("/reservation")
  @PreAuthorize("hasRole('STUDENT.READ')")
  public ResponseEntity<List<Student>> getStudentsWithReservations() {
    List<Student> students = studentRepository.findByReservationIdIsNotNull();
    return new ResponseEntity<>(students, HttpStatus.OK);
  }

  /**
   * Gets student by id.
   *
   * @param id the id
   * @return  the student by id
   */
@GetMapping("/{id}")
  @PreAuthorize("hasRole('STUDENT.READ')")
  public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
    return studentRepository
        .findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
