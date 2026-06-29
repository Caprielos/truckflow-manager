package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.foodsafety.FoodProductType;
import java.time.Instant;

public interface ValidateFoodSafetyComplianceUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(String vehicleCode, FoodProductType productType, Instant instant) {}
}
