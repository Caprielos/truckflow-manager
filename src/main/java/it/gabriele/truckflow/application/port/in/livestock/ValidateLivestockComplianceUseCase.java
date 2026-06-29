package it.gabriele.truckflow.application.port.in.livestock;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import java.time.LocalDate;

public interface ValidateLivestockComplianceUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(String vehicleCode, String tripCode, LocalDate date) {}
}
