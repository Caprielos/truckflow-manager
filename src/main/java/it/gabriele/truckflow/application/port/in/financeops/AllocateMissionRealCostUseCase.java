package it.gabriele.truckflow.application.port.in.financeops;

import it.gabriele.truckflow.domain.financeops.CostAllocation;

public interface AllocateMissionRealCostUseCase {
  CostAllocation handle(Command command);

  record Command(CostAllocation allocation) {}
}
