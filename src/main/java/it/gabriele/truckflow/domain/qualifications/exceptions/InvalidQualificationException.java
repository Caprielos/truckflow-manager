package it.gabriele.truckflow.domain.qualifications.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a qualification violates domain validation rules. */
public class InvalidQualificationException extends DomainValidationException {

  public InvalidQualificationException(String message) {
    super(message);
  }
}
