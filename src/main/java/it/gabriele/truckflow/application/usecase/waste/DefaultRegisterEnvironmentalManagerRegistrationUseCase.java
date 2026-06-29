package it.gabriele.truckflow.application.usecase.waste;

import it.gabriele.truckflow.application.port.in.waste.RegisterEnvironmentalManagerRegistrationUseCase;
import it.gabriele.truckflow.application.port.out.waste.EnvironmentalManagerRegistrationRepository;
import it.gabriele.truckflow.domain.waste.EnvironmentalManagerRegistration;
import java.util.Objects;

/** Implementazione default di RegisterEnvironmentalManagerRegistrationUseCase. */
public final class DefaultRegisterEnvironmentalManagerRegistrationUseCase
    implements RegisterEnvironmentalManagerRegistrationUseCase {

  private final EnvironmentalManagerRegistrationRepository repository;

  public DefaultRegisterEnvironmentalManagerRegistrationUseCase(
      EnvironmentalManagerRegistrationRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public EnvironmentalManagerRegistration handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    EnvironmentalManagerRegistration aggregate =
        Objects.requireNonNull(command.registration(), "L iscrizione ambientale è obbligatoria.");
    repository.save(aggregate);
    return aggregate;
  }
}
