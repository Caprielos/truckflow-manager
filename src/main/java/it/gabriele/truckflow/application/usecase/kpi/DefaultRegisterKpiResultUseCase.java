package it.gabriele.truckflow.application.usecase.kpi;

import it.gabriele.truckflow.application.port.in.kpi.RegisterKpiResultUseCase;
import it.gabriele.truckflow.application.port.out.KpiResultRepository;
import it.gabriele.truckflow.domain.kpi.KpiResult;
import java.util.Objects;

/** Caso d'uso: registrare un risultato KPI calcolato. */
public final class DefaultRegisterKpiResultUseCase implements RegisterKpiResultUseCase {

  private final KpiResultRepository kpiResultRepository;

  public DefaultRegisterKpiResultUseCase(KpiResultRepository kpiResultRepository) {
    this.kpiResultRepository =
        Objects.requireNonNull(kpiResultRepository, "Il repository KPI è obbligatorio.");
  }

  @Override
  public KpiResult handle(Command command) {
    Objects.requireNonNull(command, "Il comando registrazione KPI è obbligatorio.");
    KpiResult result = Objects.requireNonNull(command.result(), "Il risultato KPI è obbligatorio.");
    kpiResultRepository.save(result);
    return result;
  }
}
