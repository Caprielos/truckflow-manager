package it.gabriele.truckflow.domain.vehicles.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a vehicle violates domain validation rules. */
public class InvalidVehicleException extends DomainValidationException {

  public InvalidVehicleException(String message) {
    super(message);
  }
}
