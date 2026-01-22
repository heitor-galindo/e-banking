package com.ebanking.operationms.service;


import com.ebanking.operationms.dto.OperationRequestDTO;
import com.ebanking.operationms.entities.Operation;

/**
 * The interface Reservation service.
 */
public interface OperationService {

    void createNewOperation(OperationRequestDTO student, String userId);
}
