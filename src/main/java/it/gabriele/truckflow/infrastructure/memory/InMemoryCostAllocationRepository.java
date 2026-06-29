package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.CostAllocationRepository;
import it.gabriele.truckflow.domain.financeops.CostAllocation;

/** Repository in memoria per CostAllocation. */
public final class InMemoryCostAllocationRepository extends InMemoryRepository<CostAllocation>
    implements CostAllocationRepository {

  public InMemoryCostAllocationRepository() {
    super(allocation -> allocation.allocationCode());
  }
}
