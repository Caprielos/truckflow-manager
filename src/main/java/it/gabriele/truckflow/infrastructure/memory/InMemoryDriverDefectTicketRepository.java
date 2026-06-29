package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DriverDefectTicketRepository;
import it.gabriele.truckflow.domain.maintenance.DriverDefectTicket;

/** Repository in memoria per DriverDefectTicket. */
public final class InMemoryDriverDefectTicketRepository
    extends InMemoryRepository<DriverDefectTicket> implements DriverDefectTicketRepository {

  public InMemoryDriverDefectTicketRepository() {
    super(item -> item.getTicketNumber());
  }
}
