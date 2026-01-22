package com.ebanking.operationms.service;


import com.ebanking.operationms.dto.OperationRequestDTO;

public interface OperationService {

    void createNewOperation(OperationRequestDTO student, String userId);
}
