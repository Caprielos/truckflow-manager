package it.gabriele.truckflow.infrastructure.memory.maintenance;

import it.gabriele.truckflow.application.port.out.maintenance.DriverDefectTicketRepository;
import it.gabriele.truckflow.domain.maintenance.DriverDefectTicket;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DriverDefectTicket. */
public final class InMemoryDriverDefectTicketRepository
    extends InMemoryRepository<DriverDefectTicket> implements DriverDefectTicketRepository {

  public InMemoryDriverDefectTicketRepository() {
    super(item -> item.getTicketNumber());
  }
}
