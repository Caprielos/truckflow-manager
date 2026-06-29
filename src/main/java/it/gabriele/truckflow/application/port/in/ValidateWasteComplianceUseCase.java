package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import java.time.LocalDate;

public interface ValidateWasteComplianceUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(
      String documentCode, String registrationCode, String vehicleCode, LocalDate date) {}
}
