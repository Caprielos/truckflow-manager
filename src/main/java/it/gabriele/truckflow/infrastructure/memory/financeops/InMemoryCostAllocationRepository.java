package it.gabriele.truckflow.infrastructure.memory.financeops;

import it.gabriele.truckflow.application.port.out.CostAllocationRepository;
import it.gabriele.truckflow.domain.financeops.CostAllocation;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per CostAllocation. */
public final class InMemoryCostAllocationRepository extends InMemoryRepository<CostAllocation>
    implements CostAllocationRepository {

  public InMemoryCostAllocationRepository() {
    super(allocation -> allocation.allocationCode());
  }
}
