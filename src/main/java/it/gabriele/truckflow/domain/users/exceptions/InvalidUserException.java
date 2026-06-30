package it.gabriele.truckflow.domain.users.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a user violates domain validation rules. */
public class InvalidUserException extends DomainValidationException {

  public InvalidUserException(String message) {
    super(message);
  }
}
