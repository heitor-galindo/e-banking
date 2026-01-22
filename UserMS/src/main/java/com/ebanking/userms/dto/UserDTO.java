package com.ebanking.userms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDTO {
  private Long id;
  private String firstName;
  private String surName;
  private String email;
  private Integer age;
  private Long accountId;
}
