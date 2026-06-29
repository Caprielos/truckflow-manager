package it.gabriele.truckflow.application.usecase.financeops;

import it.gabriele.truckflow.application.port.in.AllocateMissionRealCostUseCase;
import it.gabriele.truckflow.application.port.out.CostAllocationRepository;
import it.gabriele.truckflow.domain.financeops.CostAllocation;
import java.util.Objects;

/** Implementazione default di AllocateMissionRealCostUseCase. */
public final class DefaultAllocateMissionRealCostUseCase implements AllocateMissionRealCostUseCase {

  private final CostAllocationRepository repository;

  public DefaultAllocateMissionRealCostUseCase(CostAllocationRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public CostAllocation handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    CostAllocation aggregate =
        Objects.requireNonNull(command.allocation(), "L allocazione costo è obbligatoria.");
    repository.save(aggregate);
    return aggregate;
  }
}
