package com.ebanking.userms.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {
  @Id private String keycloakId;
  private String userName;
  private String fullName;
  private String email;
  private String phone;
  @Transient private String password;
}
