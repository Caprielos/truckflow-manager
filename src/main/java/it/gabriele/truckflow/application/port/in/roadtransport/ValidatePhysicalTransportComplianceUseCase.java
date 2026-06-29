package it.gabriele.truckflow.application.port.in.roadtransport;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.roadtransport.PhysicalTransportCapability;
import it.gabriele.truckflow.domain.shared.Weight;

public interface ValidatePhysicalTransportComplianceUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(
      String unitCode, Weight payloadWeight, PhysicalTransportCapability requiredCapability) {}
}
