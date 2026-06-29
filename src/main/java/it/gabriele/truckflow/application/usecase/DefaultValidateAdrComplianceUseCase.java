package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.ValidateAdrComplianceUseCase;
import it.gabriele.truckflow.application.port.out.AdrComplianceProfileRepository;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.domain.adr.AdrComplianceProfile;
import it.gabriele.truckflow.domain.adr.AdrEquipmentType;
import it.gabriele.truckflow.domain.adr.AdrRules;
import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.alerting.AlertSeverity;
import it.gabriele.truckflow.domain.alerting.AlertSourceType;
import it.gabriele.truckflow.domain.alerting.AlertType;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Implementazione default di ValidateAdrComplianceUseCase. */
public final class DefaultValidateAdrComplianceUseCase implements ValidateAdrComplianceUseCase {

  private final AdrComplianceProfileRepository profileRepository;
  private final AlertEventRepository alertRepository;

  public DefaultValidateAdrComplianceUseCase(
      AdrComplianceProfileRepository profileRepository, AlertEventRepository alertRepository) {
    this.profileRepository =
        Objects.requireNonNull(profileRepository, "Il repository ADR è obbligatorio.");
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    Objects.requireNonNull(command, "Il comando ADR è obbligatorio.");
    AdrComplianceProfile profile =
        profileRepository.getRequired(command.profileCode(), "Profilo ADR");
    List<String> messages = new ArrayList<>();
    Set<AdrEquipmentType> missing = AdrRules.missingCoreEquipment(profile);
    if (!missing.isEmpty()) {
      messages.add("Dotazioni ADR mancanti: " + missing);
    }
    if (!AdrRules.canCarryAdrClass(profile, command.adrClass())) {
      messages.add("Classe ADR non trasportabile dal profilo selezionato.");
    }
    if (messages.isEmpty()) {
      return EnterpriseValidationResult.passed("Compliance ADR verificata.");
    }
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "ADR",
            command.profileCode(),
            "BLOCK",
            AlertType.COMPLIANCE_BLOCKED,
            AlertSeverity.CRITICAL,
            AlertSourceType.COMPLIANCE,
            "ADR non conforme",
            String.join(" ", messages),
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(true, messages, Optional.of(alert));
  }
}
