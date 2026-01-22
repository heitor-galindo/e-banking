package com.ebanking.operationms.configuration;

import java.io.Serializable;
import java.time.LocalTime;
import lombok.*;

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
