package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.ValidateWasteComplianceUseCase;
import it.gabriele.truckflow.application.port.out.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.EnvironmentalManagerRegistrationRepository;
import it.gabriele.truckflow.application.port.out.WasteTransportDocumentRepository;
import it.gabriele.truckflow.domain.alerting.*;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.waste.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Implementazione default di ValidateWasteComplianceUseCase. */
public final class DefaultValidateWasteComplianceUseCase implements ValidateWasteComplianceUseCase {

  private final WasteTransportDocumentRepository documentRepository;
  private final EnvironmentalManagerRegistrationRepository registrationRepository;
  private final AlertEventRepository alertRepository;

  public DefaultValidateWasteComplianceUseCase(
      WasteTransportDocumentRepository documentRepository,
      EnvironmentalManagerRegistrationRepository registrationRepository,
      AlertEventRepository alertRepository) {
    this.documentRepository =
        Objects.requireNonNull(documentRepository, "Il repository FIR è obbligatorio.");
    this.registrationRepository =
        Objects.requireNonNull(registrationRepository, "Il repository Albo è obbligatorio.");
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    WasteTransportDocument document =
        documentRepository.getRequired(command.documentCode(), "Documento rifiuti");
    EnvironmentalManagerRegistration registration =
        registrationRepository.getRequired(command.registrationCode(), "Iscrizione ambientale");
    if (WasteTransportRules.canDepart(
        document, registration, command.vehicleCode(), command.date())) {
      return EnterpriseValidationResult.passed("Compliance rifiuti verificata.");
    }
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "WASTE",
            command.documentCode(),
            "BLOCK",
            AlertType.COMPLIANCE_BLOCKED,
            AlertSeverity.CRITICAL,
            AlertSourceType.COMPLIANCE,
            "Trasporto rifiuti non conforme",
            "FIR, firme, tracciabilità o autorizzazione Albo non validi.",
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(
        true, List.of("Trasporto rifiuti non autorizzabile."), Optional.of(alert));
  }
}
