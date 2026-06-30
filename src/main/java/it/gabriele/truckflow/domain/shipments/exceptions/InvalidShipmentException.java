package it.gabriele.truckflow.domain.shipments.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a shipment violates domain validation rules. */
public class InvalidShipmentException extends DomainValidationException {

  public InvalidShipmentException(String message) {
    super(message);
  }
}
