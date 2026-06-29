package it.gabriele.truckflow.application.usecase.deadlinepolicy;

import it.gabriele.truckflow.application.port.in.deadlinepolicy.CalculateManagedDeadlinePlanUseCase;
import it.gabriele.truckflow.domain.deadlinepolicy.CombinedDeadlinePlan;
import it.gabriele.truckflow.domain.deadlinepolicy.CountryLegalDeadlineCatalog;
import it.gabriele.truckflow.domain.deadlinepolicy.DeadlinePolicyRules;
import it.gabriele.truckflow.domain.deadlinepolicy.ManufacturerTechnicalDeadlineCatalog;
import java.util.Objects;

/** Caso d'uso: calcolare le scadenze legali e tecniche di un elemento gestito. */
public final class DefaultCalculateManagedDeadlinePlanUseCase
    implements CalculateManagedDeadlinePlanUseCase {

  @Override
  public CombinedDeadlinePlan handle(Command command) {
    Objects.requireNonNull(command, "Il comando calcolo scadenze è obbligatorio.");
    Objects.requireNonNull(command.country(), "Il paese configurato è obbligatorio.");
    Objects.requireNonNull(command.elementType(), "Il tipo elemento è obbligatorio.");
    Objects.requireNonNull(command.snapshot(), "Le misure scadenza sono obbligatorie.");

    return DeadlinePolicyRules.combine(
        command.ownerCode(),
        command.elementType(),
        CountryLegalDeadlineCatalog.rulesFor(command.country()),
        ManufacturerTechnicalDeadlineCatalog.rulesFor(
            command.manufacturer(), command.modelFamily()),
        command.snapshot());
  }
}
