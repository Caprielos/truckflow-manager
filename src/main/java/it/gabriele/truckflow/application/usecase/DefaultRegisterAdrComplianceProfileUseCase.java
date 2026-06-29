package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.RegisterAdrComplianceProfileUseCase;
import it.gabriele.truckflow.application.port.out.AdrComplianceProfileRepository;
import it.gabriele.truckflow.domain.adr.AdrComplianceProfile;
import java.util.Objects;

/** Implementazione default di RegisterAdrComplianceProfileUseCase. */
public final class DefaultRegisterAdrComplianceProfileUseCase
    implements RegisterAdrComplianceProfileUseCase {

  private final AdrComplianceProfileRepository repository;

  public DefaultRegisterAdrComplianceProfileUseCase(AdrComplianceProfileRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public AdrComplianceProfile handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    AdrComplianceProfile aggregate =
        Objects.requireNonNull(command.profile(), "Il profilo ADR è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
