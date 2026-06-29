package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.RegisterTachographActivityUseCase;
import it.gabriele.truckflow.application.port.out.TachographActivityRepository;
import it.gabriele.truckflow.domain.tachograph.TachographActivity;
import java.util.Objects;

/** Implementazione default di RegisterTachographActivityUseCase. */
public final class DefaultRegisterTachographActivityUseCase
    implements RegisterTachographActivityUseCase {

  private final TachographActivityRepository repository;

  public DefaultRegisterTachographActivityUseCase(TachographActivityRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public TachographActivity handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    TachographActivity aggregate =
        Objects.requireNonNull(command.activity(), "L attività tachigrafica è obbligatoria.");
    repository.save(aggregate);
    return aggregate;
  }
}
