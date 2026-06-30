package it.gabriele.truckflow.domain.shipments.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a shipment item violates domain validation rules. */
public class InvalidShipmentItemException extends DomainValidationException {

  public InvalidShipmentItemException(String message) {
    super(message);
  }
}
