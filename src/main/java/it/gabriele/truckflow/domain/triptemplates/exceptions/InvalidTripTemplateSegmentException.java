package it.gabriele.truckflow.domain.triptemplates.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a trip template segment violates domain validation rules. */
public class InvalidTripTemplateSegmentException extends DomainValidationException {

  public InvalidTripTemplateSegmentException(String message) {
    super(message);
  }
}
