package com.skistation.studentms.config;

import com.skistation.studentms.entities.Student;
import com.skistation.studentms.repository.StudentRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** The type Data initializer. */
@Configuration
@Slf4j
public class DataInitializer {

  /**
   * Init student data command line runner.
   *
   * @param studentRepository the student repository
   * @return  the command line runner
   */
@Bean
  CommandLineRunner initStudentData(StudentRepository studentRepository) {
    return args -> {
      List<Student> students =
          List.of(
              new Student("Alice", "Springfield High", 16),
              new Student("Bob", "Riverdale High", 17),
              new Student("Charlie", "Sunnydale High", 15));
      studentRepository.saveAll(students);
      log.info("============= Student data initialized =============");
    };
  }
}
