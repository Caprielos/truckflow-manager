package it.gabriele.truckflow.application.usecase.oversized;

import it.gabriele.truckflow.application.port.in.oversized.RegisterOversizedLoadProfileUseCase;
import it.gabriele.truckflow.application.port.out.oversized.OversizedLoadProfileRepository;
import it.gabriele.truckflow.domain.oversized.OversizedLoadProfile;
import java.util.Objects;

/** Implementazione default di RegisterOversizedLoadProfileUseCase. */
public final class DefaultRegisterOversizedLoadProfileUseCase
    implements RegisterOversizedLoadProfileUseCase {

  private final OversizedLoadProfileRepository repository;

  public DefaultRegisterOversizedLoadProfileUseCase(OversizedLoadProfileRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public OversizedLoadProfile handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    OversizedLoadProfile aggregate =
        Objects.requireNonNull(command.load(), "Il carico eccezionale è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
