package com.skistation.reservationms.clients;

import com.skistation.reservationms.configuration.FeignClientConfigStudent;
import com.skistation.reservationms.dto.StudentDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** The interface Student client. */
@FeignClient(name = "STUDENTSMS", configuration = FeignClientConfigStudent.class)
//    url = "${student.ms.url}"
public interface StudentClient {
  /**
   * Gets student by id.
   *
   * @param id the id
   * @return the student by id
   */
  @GetMapping("/students/{id}")
  StudentDTO getStudentById(@PathVariable Long id);

  /**
   * Gets students with reservations.
   *
   * @return the students with reservations
   */
  @GetMapping("/students/reservation")
  List<StudentDTO> getStudentsWithReservations();
}
