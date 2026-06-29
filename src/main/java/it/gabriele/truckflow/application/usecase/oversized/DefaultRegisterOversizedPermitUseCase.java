package it.gabriele.truckflow.application.usecase.oversized;

import it.gabriele.truckflow.application.port.in.oversized.RegisterOversizedPermitUseCase;
import it.gabriele.truckflow.application.port.out.oversized.OversizedPermitRepository;
import it.gabriele.truckflow.domain.oversized.OversizedPermit;
import java.util.Objects;

/** Implementazione default di RegisterOversizedPermitUseCase. */
public final class DefaultRegisterOversizedPermitUseCase implements RegisterOversizedPermitUseCase {

  private final OversizedPermitRepository repository;

  public DefaultRegisterOversizedPermitUseCase(OversizedPermitRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public OversizedPermit handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    OversizedPermit aggregate =
        Objects.requireNonNull(command.permit(), "Il permesso eccezionale è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
