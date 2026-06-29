package it.gabriele.truckflow.application.usecase.atp;

import it.gabriele.truckflow.application.port.in.atp.ValidateAtpComplianceUseCase;
import it.gabriele.truckflow.application.port.out.alerting.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.atp.AtpCertificateRepository;
import it.gabriele.truckflow.application.usecase.EnterpriseAlertFactory;
import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.alerting.AlertSeverity;
import it.gabriele.truckflow.domain.alerting.AlertSourceType;
import it.gabriele.truckflow.domain.alerting.AlertType;
import it.gabriele.truckflow.domain.atp.AtpCertificate;
import it.gabriele.truckflow.domain.atp.AtpRules;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Implementazione default di ValidateAtpComplianceUseCase. */
public final class DefaultValidateAtpComplianceUseCase implements ValidateAtpComplianceUseCase {

  private final AtpCertificateRepository certificateRepository;
  private final AlertEventRepository alertRepository;

  public DefaultValidateAtpComplianceUseCase(
      AtpCertificateRepository certificateRepository, AlertEventRepository alertRepository) {
    this.certificateRepository =
        Objects.requireNonNull(certificateRepository, "Il repository ATP è obbligatorio.");
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    Objects.requireNonNull(command, "Il comando ATP è obbligatorio.");
    AtpCertificate certificate =
        certificateRepository.getRequired(command.certificateCode(), "Certificato ATP");
    if (AtpRules.isReadyForFoodOrPharmaTransport(
        certificate, command.requiredRange(), command.date())) {
      return EnterpriseValidationResult.passed("Compliance ATP verificata.");
    }
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "ATP",
            command.certificateCode(),
            "BLOCK",
            AlertType.COMPLIANCE_BLOCKED,
            AlertSeverity.CRITICAL,
            AlertSourceType.VEHICLE,
            "ATP non conforme",
            "Certificato ATP, manutenzione frigo o termografo non validi.",
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(
        true, List.of("ATP non idoneo al range richiesto."), Optional.of(alert));
  }
}
