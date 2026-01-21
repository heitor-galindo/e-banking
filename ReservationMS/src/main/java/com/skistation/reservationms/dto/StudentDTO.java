package com.skistation.reservationms.dto;

import lombok.*;

/** The type Student dto. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentDTO {
  private Long id;
  private String firstName;
  private String school;
  private Integer age;
  private Long reservationId;
}
