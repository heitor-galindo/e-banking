package com.ebanking.userms.service;

import com.ebanking.userms.entities.UserProfile;
import com.ebanking.userms.repository.UserProfileRepository;
import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakUserService {
  private final Keycloak keycloak;
  private final UserProfileRepository repository;

  @Value("${keycloak.target.realm}")
  private String realm;

  public KeycloakUserService(Keycloak keycloak, UserProfileRepository repository) {
    this.keycloak = keycloak;
    this.repository = repository;
  }

  public UserProfile createUser(String username, String email, String fullName, String phone) {
    UserRepresentation user = new UserRepresentation();
    user.setUsername(username);
    user.setEmail(email);
    user.setEnabled(true);
    user.setEmailVerified(true);
    if (fullName != null && !fullName.isBlank()) {
      String[] parts = fullName.trim().split(" ", 2);
      user.setFirstName(parts[0]);
      user.setLastName(parts.length > 1 ? parts[1] : parts[0]);
    } else {
      user.setFirstName(username);
      user.setLastName(username);
    }
    String tempPassword = java.util.UUID.randomUUID().toString();
    CredentialRepresentation password = new CredentialRepresentation();
    password.setType(CredentialRepresentation.PASSWORD);
    password.setValue(tempPassword);
    password.setTemporary(false);
    user.setCredentials(List.of(password));
    Response response = keycloak.realm(realm).users().create(user);

    if (response.getStatus() != 201) {
      String error = response.readEntity(String.class);
      throw new RuntimeException("Failed to create user in Keycloak: " + error);
    }

    String userId = CreatedResponseUtil.getCreatedId(response);
    RoleRepresentation userRole = keycloak.realm(realm).roles().get("USER").toRepresentation();

    keycloak
        .realm(realm)
        .users()
        .get(userId)
        .roles()
        .realmLevel()
        .add(Collections.singletonList(userRole));

    UserProfile userProfile =
        UserProfile.builder()
            .keycloakId(userId)
            .userName(username)
            .fullName(fullName)
            .email(email)
            .phone(phone)
            .password(tempPassword)
            .build();

    repository.save(userProfile);
    return userProfile;
  }

  public void deleteUser(String userId) {
    keycloak.realm(realm).users().get(userId).remove();
  }

  public void updatePassword(String userId, String newPassword) {
    CredentialRepresentation password = new CredentialRepresentation();
    password.setType(CredentialRepresentation.PASSWORD);
    password.setValue(newPassword);
    password.setTemporary(false);
    keycloak.realm(realm).users().get(userId).resetPassword(password);
  }
}
