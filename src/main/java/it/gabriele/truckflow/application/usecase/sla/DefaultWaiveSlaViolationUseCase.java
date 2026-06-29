package it.gabriele.truckflow.application.usecase.sla;

import it.gabriele.truckflow.application.port.in.WaiveSlaViolationUseCase;
import it.gabriele.truckflow.application.port.out.SlaViolationRepository;
import it.gabriele.truckflow.domain.sla.SlaViolation;
import java.util.Objects;

/** Caso d'uso: annullare motivatamente una penale SLA. */
public final class DefaultWaiveSlaViolationUseCase implements WaiveSlaViolationUseCase {

  private final SlaViolationRepository violationRepository;

  public DefaultWaiveSlaViolationUseCase(SlaViolationRepository violationRepository) {
    this.violationRepository =
        Objects.requireNonNull(violationRepository, "Il repository violazioni SLA è obbligatorio.");
  }

  @Override
  public SlaViolation handle(Command command) {
    Objects.requireNonNull(command, "Il comando deroga SLA è obbligatorio.");
    SlaViolation violation =
        violationRepository.getRequired(command.violationCode(), "Violazione SLA");
    SlaViolation waived = violation.waive(command.waiverNotes());
    violationRepository.save(waived);
    return waived;
  }
}
