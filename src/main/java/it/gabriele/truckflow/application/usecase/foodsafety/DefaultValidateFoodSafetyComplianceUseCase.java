package it.gabriele.truckflow.application.usecase.foodsafety;

import it.gabriele.truckflow.application.port.in.foodsafety.ValidateFoodSafetyComplianceUseCase;
import it.gabriele.truckflow.application.port.out.alerting.AlertEventRepository;
import it.gabriele.truckflow.application.port.out.foodsafety.FoodSafetyProfileRepository;
import it.gabriele.truckflow.application.usecase.EnterpriseAlertFactory;
import it.gabriele.truckflow.application.usecase.EnterpriseValidationResult;
import it.gabriele.truckflow.domain.alerting.*;
import it.gabriele.truckflow.domain.foodsafety.*;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Implementazione default di ValidateFoodSafetyComplianceUseCase. */
public final class DefaultValidateFoodSafetyComplianceUseCase
    implements ValidateFoodSafetyComplianceUseCase {

  private final FoodSafetyProfileRepository profileRepository;
  private final AlertEventRepository alertRepository;

  public DefaultValidateFoodSafetyComplianceUseCase(
      FoodSafetyProfileRepository profileRepository, AlertEventRepository alertRepository) {
    this.profileRepository =
        Objects.requireNonNull(profileRepository, "Il repository HACCP è obbligatorio.");
    this.alertRepository =
        Objects.requireNonNull(alertRepository, "Il repository alert è obbligatorio.");
  }

  @Override
  public EnterpriseValidationResult handle(Command command) {
    FoodSafetyProfile profile =
        profileRepository.getRequired(command.vehicleCode(), "Profilo alimentare");
    if (FoodSafetyRules.canLoadFood(profile, command.productType(), command.instant())) {
      return EnterpriseValidationResult.passed("Compliance alimentare/HACCP verificata.");
    }
    AlertEvent alert =
        EnterpriseAlertFactory.open(
            "FOOD",
            command.vehicleCode(),
            "BLOCK",
            AlertType.COMPLIANCE_BLOCKED,
            AlertSeverity.CRITICAL,
            AlertSourceType.COMPLIANCE,
            "HACCP non conforme",
            "Pulizia, sanificazione o separazione non sufficienti.",
            Instant.now(),
            Notes.empty());
    alertRepository.save(alert);
    return EnterpriseValidationResult.failed(
        true, List.of("Veicolo non idoneo al carico alimentare."), Optional.of(alert));
  }
}
