package it.gabriele.truckflow.domain.operational.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a warehouse operator violates domain validation rules. */
public class InvalidWarehouseOperatorException extends DomainValidationException {

  public InvalidWarehouseOperatorException(String message) {
    super(message);
  }
}
