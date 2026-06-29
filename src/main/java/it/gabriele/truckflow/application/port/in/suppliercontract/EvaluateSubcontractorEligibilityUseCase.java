package it.gabriele.truckflow.application.port.in.suppliercontract;

import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.suppliercontract.SubcontractorServiceType;
import java.time.LocalDate;

public interface EvaluateSubcontractorEligibilityUseCase {
  EnterpriseValidationResult handle(Command command);

  record Command(
      String contractCode, SubcontractorServiceType requiredService, LocalDate missionDate) {}
}
