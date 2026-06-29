package it.gabriele.truckflow.infrastructure.memory.billing;

import it.gabriele.truckflow.application.port.out.billing.CustomerRevenueInvoiceRepository;
import it.gabriele.truckflow.domain.economics.CustomerRevenueInvoice;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per CustomerRevenueInvoice. */
public final class InMemoryCustomerRevenueInvoiceRepository
    extends InMemoryRepository<CustomerRevenueInvoice> implements CustomerRevenueInvoiceRepository {

  public InMemoryCustomerRevenueInvoiceRepository() {
    super(item -> item.getInvoiceNumber());
  }
}
