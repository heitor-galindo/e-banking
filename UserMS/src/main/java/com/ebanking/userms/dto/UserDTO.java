package com.ebanking.userms.dto;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {
  private String userName;
  private String email;
  private String fullName;
  private String phone;
}
