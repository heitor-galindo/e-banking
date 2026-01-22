package com.ebanking.operationms.repository;

import com.ebanking.operationms.entities.Operation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Long> {}
