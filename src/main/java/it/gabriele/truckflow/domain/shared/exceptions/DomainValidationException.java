package it.gabriele.truckflow.domain.shared.exceptions;

/** Exception thrown when a domain value is invalid. */
public class DomainValidationException extends DomainException {

  public DomainValidationException(String message) {
    super(message);
  }
}
