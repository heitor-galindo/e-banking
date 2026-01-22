package com.ebanking.userms.config;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.*;

/** The type Account event. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEvent implements Serializable {
  @NotNull private Long accountId;
  @NotNull private Long userId;
}