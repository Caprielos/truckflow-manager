package it.gabriele.truckflow.application.port.out.economics;

import it.gabriele.truckflow.application.port.out.RepositoryPort;
import it.gabriele.truckflow.domain.economics.FleetFinancialStatement;

/** Repository port per FleetFinancialStatement. L'implementazione sarà in infrastructure. */
public interface FleetFinancialStatementRepository
    extends RepositoryPort<FleetFinancialStatement> {}
