package com.ebanking.accountms.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCreatedEvent {

    private Long accountId;
    private String userId;
}
