package it.gabriele.truckflow.domain.vehicles.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a vehicle combination violates domain validation rules. */
public class InvalidVehicleCombinationException extends DomainValidationException {

  public InvalidVehicleCombinationException(String message) {
    super(message);
  }
}
