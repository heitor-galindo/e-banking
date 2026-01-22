package com.ebanking.userms.controller;

import com.ebanking.userms.entities.User;
import com.ebanking.userms.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {

  @Autowired private UserRepository userRepository;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    User saved = userRepository.save(user);
    return new ResponseEntity<>(saved, HttpStatus.CREATED);
  }

  @GetMapping("/all")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userRepository.findAll();
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @GetMapping("/account")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<User>> getUsersWithAccounts() {
    List<User> users = userRepository.findByAccountIDIsNotNull();
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    return userRepository
        .findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
