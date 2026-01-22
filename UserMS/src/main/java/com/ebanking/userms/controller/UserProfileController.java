package com.ebanking.userms.controller;

import com.ebanking.userms.clients.AccountClient;
import com.ebanking.userms.dto.CreateAccountRequestDTO;
import com.ebanking.userms.dto.UserDTO;
import com.ebanking.userms.entities.UserProfile;
import com.ebanking.userms.enums.account.AccountType;
import com.ebanking.userms.enums.account.Currency;
import com.ebanking.userms.repository.UserProfileRepository;
import com.ebanking.userms.service.KeycloakUserService;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/users")
@RestController
@Slf4j
public class UserProfileController {
  private final KeycloakUserService keycloakService;
  private final UserProfileRepository repository;
  private final AccountClient accountClient;

  public UserProfileController(
      KeycloakUserService keycloakService,
      UserProfileRepository repository,
      AccountClient accountClient) {
    this.keycloakService = keycloakService;
    this.repository = repository;
    this.accountClient = accountClient;
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<UserProfile> createUser(@RequestBody UserDTO request) {

    UserProfile userProfile =
        keycloakService.createUser(
            request.getUserName(), request.getEmail(), request.getFullName(), request.getPhone());

    accountClient.createAccount(
            new CreateAccountRequestDTO(
                userProfile.getKeycloakId(), AccountType.SAVINGS, Currency.EUR));

    return ResponseEntity.ok(userProfile);
  }

  @GetMapping("/me")
  public ResponseEntity<UserProfile> me(@AuthenticationPrincipal Jwt jwt) {
    System.out.println("JWT subject: " + jwt.getSubject());
    return repository
        .findById(jwt.getSubject())
        .map(ResponseEntity::ok)
        .orElseGet(
            () -> {
              System.out.println("User not found in DB!");
              return ResponseEntity.notFound().build();
            });
  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/users/change-password")
  public ResponseEntity<String> changePassword(
      @AuthenticationPrincipal Jwt jwt, @RequestBody Map<String, String> passwordRequest) {
    String userId = jwt.getSubject();

    String newPassword = passwordRequest.get("newPassword");
    if (newPassword == null || newPassword.isBlank()) {
      return ResponseEntity.badRequest().body("Password cannot be empty");
    }
    keycloakService.updatePassword(userId, newPassword);
    return ResponseEntity.ok("Password updated successfully");
  }

  @DeleteMapping("/admin/users/{userId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> deleteUser(@PathVariable String userId) {
    keycloakService.deleteUser(userId);
    return ResponseEntity.ok("User deleted successfully");
  }

  @GetMapping("/getAllUsers")
  @PreAuthorize("hasRole('ADMIN')")
  public List<UserProfile> getUsers() {
    return repository.findAll();
  }

  @GetMapping("/users/{id}")
  public UserProfile getUserById(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
    log.info("JWT roles: {}", jwt.getClaimAsStringList("realm_access.roles"));
    log.info("-----------Get user by KeycloackID: " + id);
    return repository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }
}
