package com.ebanking.operationms.exception;

import com.ebanking.operationms.entities.Operation;

public class OperationProcessingException extends RuntimeException {

    private final Operation operation;

    public OperationProcessingException(
            String message,
            Throwable cause,
            Operation operation
    ) {
        super(message, cause);
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }
}
