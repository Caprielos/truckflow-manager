package it.gabriele.truckflow.domain.shared.exceptions;

/** Exception thrown when a domain invariant is violated. */
public class InvariantViolationException extends DomainException {

  public InvariantViolationException(String message) {
    super(message);
  }
}
