package it.gabriele.truckflow.infrastructure.memory.economics;

import it.gabriele.truckflow.application.port.out.FleetFinancialStatementRepository;
import it.gabriele.truckflow.domain.economics.FleetFinancialStatement;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per FleetFinancialStatement. */
public final class InMemoryFleetFinancialStatementRepository
    extends InMemoryRepository<FleetFinancialStatement>
    implements FleetFinancialStatementRepository {

  public InMemoryFleetFinancialStatementRepository() {
    super(item -> item.getStatementNumber());
  }
}
