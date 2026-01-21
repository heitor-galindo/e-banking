package com.example.userms.config;

import com.example.userms.entities.User;
import com.example.userms.repository.UserRepository;
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
   * Init user data command line runner.
   *
   * @param userRepository the user repository
   * @return  the command line runner
   */
@Bean
  CommandLineRunner initUserData(UserRepository userRepository) {
    return args -> {
      List<User> users =
          List.of(
              new User("Alice", "Johnson", "alice@example.com", 16),
              new User("Bob", "Miller", "bob@example.com", 17),
              new User("Charlie", "Smith", "charlie@example.com", 15));
      userRepository.saveAll(users);
      log.info("============= User data initialized =============");
    };
  }
}
