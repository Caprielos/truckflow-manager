package it.gabriele.truckflow.application.port.in.oversized;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import java.time.LocalDate;

public interface ValidateOversizedComplianceUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(String permitCode, String loadCode, LocalDate date, String countryCode) {}
}
