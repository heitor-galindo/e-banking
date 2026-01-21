package com.skistation.studentms.repository;

import com.skistation.studentms.entities.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface Student repository. */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  /**
   * Find by reservation id is not null list.
   *
   * @return  the list
   */
List<Student> findByReservationIdIsNotNull();
}
