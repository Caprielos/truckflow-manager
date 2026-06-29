package it.gabriele.truckflow.infrastructure.memory.economics;

import it.gabriele.truckflow.application.port.out.SupplierInvoiceRepository;
import it.gabriele.truckflow.domain.economics.SupplierInvoice;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per SupplierInvoice. */
public final class InMemorySupplierInvoiceRepository extends InMemoryRepository<SupplierInvoice>
    implements SupplierInvoiceRepository {

  public InMemorySupplierInvoiceRepository() {
    super(item -> item.getInvoiceNumber());
  }
}
