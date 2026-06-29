package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.CustomerRevenueInvoiceRepository;
import it.gabriele.truckflow.domain.economics.CustomerRevenueInvoice;

/** Repository in memoria per CustomerRevenueInvoice. */
public final class InMemoryCustomerRevenueInvoiceRepository extends InMemoryRepository<CustomerRevenueInvoice> implements CustomerRevenueInvoiceRepository {

    public InMemoryCustomerRevenueInvoiceRepository() {
        super(item -> item.getInvoiceNumber());
    }
}
