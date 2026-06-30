package it.gabriele.truckflow.domain.shared.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import it.gabriele.truckflow.domain.cargo.exceptions.InvalidCargoException;
import it.gabriele.truckflow.domain.compliance.exceptions.InvalidComplianceRequirementException;
import it.gabriele.truckflow.domain.documents.exceptions.InvalidDocumentException;
import it.gabriele.truckflow.domain.locations.exceptions.InvalidLocationException;
import it.gabriele.truckflow.domain.operational.exceptions.InvalidDispatcherException;
import it.gabriele.truckflow.domain.operational.exceptions.InvalidDriverException;
import it.gabriele.truckflow.domain.operational.exceptions.InvalidManagerException;
import it.gabriele.truckflow.domain.operational.exceptions.InvalidMechanicException;
import it.gabriele.truckflow.domain.operational.exceptions.InvalidWarehouseOperatorException;
import it.gabriele.truckflow.domain.qualifications.exceptions.InvalidQualificationException;
import it.gabriele.truckflow.domain.shipments.exceptions.InvalidShipmentException;
import it.gabriele.truckflow.domain.shipments.exceptions.InvalidShipmentItemException;
import it.gabriele.truckflow.domain.shipments.exceptions.InvalidShipmentLegException;
import it.gabriele.truckflow.domain.triptemplates.exceptions.InvalidTripTemplateException;
import it.gabriele.truckflow.domain.triptemplates.exceptions.InvalidTripTemplateSegmentException;
import it.gabriele.truckflow.domain.users.exceptions.InvalidUserException;
import it.gabriele.truckflow.domain.vehicles.exceptions.InvalidVehicleCombinationException;
import it.gabriele.truckflow.domain.vehicles.exceptions.InvalidVehicleException;
import org.junit.jupiter.api.Test;

class DomainExceptionTest {

  @Test
  void domainValidationExceptionIsADomainException() {
    var exception = new DomainValidationException("Invalid domain value.");

    assertInstanceOf(DomainException.class, exception);
    assertEquals("Invalid domain value.", exception.getMessage());
  }

  @Test
  void invariantViolationExceptionIsADomainException() {
    var exception = new InvariantViolationException("Invariant violated.");

    assertInstanceOf(DomainException.class, exception);
    assertEquals("Invariant violated.", exception.getMessage());
  }

  @Test
  void domainSpecificExceptionsExtendDomainValidationException() {
    assertDomainValidation(new InvalidUserException("Invalid user."));
    assertDomainValidation(new InvalidQualificationException("Invalid qualification."));
    assertDomainValidation(new InvalidDriverException("Invalid driver."));
    assertDomainValidation(new InvalidMechanicException("Invalid mechanic."));
    assertDomainValidation(new InvalidWarehouseOperatorException("Invalid warehouse operator."));
    assertDomainValidation(new InvalidDispatcherException("Invalid dispatcher."));
    assertDomainValidation(new InvalidManagerException("Invalid manager."));
    assertDomainValidation(new InvalidVehicleException("Invalid vehicle."));
    assertDomainValidation(new InvalidVehicleCombinationException("Invalid vehicle combination."));
    assertDomainValidation(new InvalidCargoException("Invalid cargo."));
    assertDomainValidation(new InvalidLocationException("Invalid location."));
    assertDomainValidation(new InvalidTripTemplateException("Invalid trip template."));
    assertDomainValidation(
        new InvalidTripTemplateSegmentException("Invalid trip template segment."));
    assertDomainValidation(new InvalidShipmentException("Invalid shipment."));
    assertDomainValidation(new InvalidShipmentItemException("Invalid shipment item."));
    assertDomainValidation(new InvalidShipmentLegException("Invalid shipment leg."));
    assertDomainValidation(new InvalidDocumentException("Invalid document."));
    assertDomainValidation(
        new InvalidComplianceRequirementException("Invalid compliance requirement."));
  }

  private static void assertDomainValidation(DomainException exception) {
    assertInstanceOf(DomainValidationException.class, exception);
    assertInstanceOf(DomainException.class, exception);
  }
}
