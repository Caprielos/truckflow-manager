package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.FleetFinancialStatementRepository;
import it.gabriele.truckflow.domain.economics.FleetFinancialStatement;

/** Repository in memoria per FleetFinancialStatement. */
public final class InMemoryFleetFinancialStatementRepository
    extends InMemoryRepository<FleetFinancialStatement>
    implements FleetFinancialStatementRepository {

  public InMemoryFleetFinancialStatementRepository() {
    super(item -> item.getStatementNumber());
  }
}
