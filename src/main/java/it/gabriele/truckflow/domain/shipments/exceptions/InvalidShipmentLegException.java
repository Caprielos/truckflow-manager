package it.gabriele.truckflow.domain.shipments.exceptions;

import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

/** Exception thrown when a shipment leg violates domain validation rules. */
public class InvalidShipmentLegException extends DomainValidationException {

  public InvalidShipmentLegException(String message) {
    super(message);
  }
}
