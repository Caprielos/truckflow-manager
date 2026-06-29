package it.gabriele.truckflow.application.usecase.kpi;

import it.gabriele.truckflow.application.port.in.kpi.RegisterKpiThresholdUseCase;
import it.gabriele.truckflow.application.port.out.KpiThresholdRepository;
import it.gabriele.truckflow.domain.kpi.KpiThreshold;
import java.util.Objects;

/** Caso d'uso: registrare una soglia KPI di dashboard. */
public final class DefaultRegisterKpiThresholdUseCase implements RegisterKpiThresholdUseCase {

  private final KpiThresholdRepository thresholdRepository;

  public DefaultRegisterKpiThresholdUseCase(KpiThresholdRepository thresholdRepository) {
    this.thresholdRepository =
        Objects.requireNonNull(thresholdRepository, "Il repository soglie KPI è obbligatorio.");
  }

  @Override
  public KpiThreshold handle(Command command) {
    Objects.requireNonNull(command, "Il comando soglia KPI è obbligatorio.");
    KpiThreshold threshold =
        Objects.requireNonNull(command.threshold(), "La soglia KPI è obbligatoria.");
    thresholdRepository.save(threshold);
    return threshold;
  }
}
