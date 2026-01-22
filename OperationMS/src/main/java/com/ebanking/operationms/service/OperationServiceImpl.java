package com.ebanking.operationms.service;

import com.ebanking.operationms.clients.AccountClient;
import com.ebanking.operationms.configuration.OperationReportEvent;
import com.ebanking.operationms.dto.*;
import com.ebanking.operationms.entities.Operation;
import com.ebanking.operationms.exception.OperationProcessingException;
import com.ebanking.operationms.repository.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Slf4j
@Service
@Transactional
public class OperationServiceImpl implements OperationService {

    private static final String TOPIC = "operation-notification-event";

    private final OperationRepository operationRepository;
    private final AccountClient accountClient;
    private final KafkaTemplate<String, OperationReportEvent> kafkaTemplate;

    public OperationServiceImpl(
            OperationRepository operationRepository,
            AccountClient accountClient,
            KafkaTemplate<String, OperationReportEvent> kafkaTemplate
    ) {
        this.operationRepository = operationRepository;
        this.accountClient = accountClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void createNewOperation(OperationRequestDTO request, String userId) {

        Operation operation = null;

        try {
            validateRequest(request);

            operation = Operation.builder()
                    .cardId(resolveCardId(request))
                    .operationType(request.getOperationType())
                    .destinationAccountId(request.getDestinationAccount())
                    .amount(request.getOperationAmount())
                    .status(OperationStatus.PENDING)
                    .build();

            operationRepository.save(operation);

            AccountOperationResponseDTO response =
                    sendOperationRequest(operation, request.getUserId());

            updateOperationFromResponse(operation, response);
            operationRepository.save(operation);

            sendNotification(operation, userId);

        } catch (Exception ex) {

            log.error("Error processing operation for user {}", userId, ex);

            sendErrorNotification(operation, userId, ex);

            throw new OperationProcessingException(
                    "Failed to process operation",
                    ex,
                    operation
            );
        }
    }

    private AccountOperationResponseDTO sendOperationRequest(
            Operation operation, String userId) {

        AccountOperationRequestDTO request = AccountOperationRequestDTO.builder()
                .operationId(operation.getId())
                .userId(userId)
                .operationType(operation.getOperationType())
                .destinationAccount(operation.getDestinationAccountId())
                .amount(operation.getAmount())
                .build();

        return accountClient.executeTransactionRequest(request);
    }

    private void updateOperationFromResponse(
            Operation operation,
            AccountOperationResponseDTO response) {

        operation.setSourceAccountId(response.getSourceAccountId());
        operation.setDestinationAccountId(response.getDestinationAccountId());
        operation.setStatus(response.getOperationStatus());
        operation.setComment(response.getComment());
    }

    private void sendNotification(Operation operation, String userId) {
        OperationReportEvent event = new OperationReportEvent(
                userId,
                buildNotificationMessage(operation, userId),
                LocalTime.now()
        );

        kafkaTemplate.send(TOPIC, event);
    }

    private void sendErrorNotification(
            Operation operation,
            String userId,
            Exception ex
    ) {
        OperationReportEvent event = new OperationReportEvent(
                userId,
                buildErrorNotificationMessage(operation, userId, ex),
                LocalTime.now()
        );

        kafkaTemplate.send(TOPIC, event);
    }


    private Long resolveCardId(OperationRequestDTO dto) {
        if (OperationType.CARD_PURCHASE.equals(dto.getOperationType())) {
            if (dto.getCardId() == null) {
                throw new IllegalArgumentException(
                        "cardId is required for CARD_PURCHASE"
                );
            }
            return dto.getCardId();
        }
        return null;
    }

    private void validateRequest(OperationRequestDTO dto) {
        if (dto.getOperationType() == null) {
            throw new IllegalArgumentException("operationType is required");
        }
    }

    private String buildNotificationMessage(Operation operation, String userId) {
        return String.format(
                "The %s operation made by the user with id %s has status %s",
                operation.getOperationType(),
                userId,
                operation.getStatus()
        );
    }

    private String buildErrorNotificationMessage(
            Operation operation,
            String userId,
            Exception ex
    ) {
        return String.format(
                "The %s operation made by the user with id %s failed with error: %s",
                operation != null ? operation.getOperationType() : "UNKNOWN",
                userId,
                ex.getMessage()
        );
    }

}

