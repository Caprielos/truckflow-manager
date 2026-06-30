package it.gabriele.truckflow.domain.compliance.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a compliance requirement violates domain validation rules. */
public class InvalidComplianceRequirementException extends DomainValidationException {

  public InvalidComplianceRequirementException(String message) {
    super(message);
  }
}
