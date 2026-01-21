package com.example.userms.controller;

import com.example.userms.entities.User;
import com.example.userms.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The type User controller.
 */
@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {

  @Autowired private UserRepository userRepository;

  /**
   * Create User response entity.
   *
   * @param User the User
   * @return  the response entity
   */
@PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    User saved = userRepository.save(user);
    return new ResponseEntity<>(saved, HttpStatus.CREATED);
  }

  /**
   * Gets all Users.
   *
   * @return  the all Users
   */
@GetMapping("/all")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userRepository.findAll();
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  /**
   * Gets Users with reservations.
   *
   * @return  the Users with reservations
   */
  @GetMapping("/account")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<User>> getUsersWithAccounts() {
    List<User> users = userRepository.findByAccountIDIsNotNull();
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  /**
   * Gets User by id.
   *
   * @param id the id
   * @return  the User by id
   */
@GetMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    return userRepository
        .findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
