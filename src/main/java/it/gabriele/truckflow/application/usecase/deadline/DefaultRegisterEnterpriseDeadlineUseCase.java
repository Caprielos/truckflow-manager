package it.gabriele.truckflow.application.usecase.deadline;

import it.gabriele.truckflow.application.port.in.deadline.RegisterEnterpriseDeadlineUseCase;
import it.gabriele.truckflow.application.port.out.EnterpriseDeadlineRepository;
import it.gabriele.truckflow.domain.deadline.EnterpriseDeadline;
import java.util.Objects;

/** Caso d'uso: registrare una scadenza enterprise nello scadenziario operativo. */
public final class DefaultRegisterEnterpriseDeadlineUseCase
    implements RegisterEnterpriseDeadlineUseCase {

  private final EnterpriseDeadlineRepository deadlineRepository;

  public DefaultRegisterEnterpriseDeadlineUseCase(EnterpriseDeadlineRepository deadlineRepository) {
    this.deadlineRepository =
        Objects.requireNonNull(deadlineRepository, "Il repository scadenze è obbligatorio.");
  }

  @Override
  public EnterpriseDeadline handle(Command command) {
    Objects.requireNonNull(command, "Il comando registrazione scadenza è obbligatorio.");
    EnterpriseDeadline deadline =
        Objects.requireNonNull(command.deadline(), "La scadenza da registrare è obbligatoria.");
    deadlineRepository.save(deadline);
    return deadline;
  }
}
