package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import java.time.LocalDate;

public interface ValidateAtpComplianceUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(String certificateCode, TemperatureRange requiredRange, LocalDate date) {}
}
