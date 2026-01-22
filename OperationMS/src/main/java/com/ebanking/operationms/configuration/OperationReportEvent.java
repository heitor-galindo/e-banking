package com.ebanking.operationms.configuration;

import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

/** The type Operation event. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationReportEvent implements Serializable {
  private String userId;
  private String notificationMessage;
  private LocalTime notificationDate;


}
