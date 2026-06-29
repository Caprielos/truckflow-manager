package it.gabriele.truckflow.infrastructure.memory.financeops;

import it.gabriele.truckflow.application.port.out.financeops.ElectronicInvoiceEnvelopeRepository;
import it.gabriele.truckflow.domain.financeops.ElectronicInvoiceEnvelope;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per ElectronicInvoiceEnvelope. */
public final class InMemoryElectronicInvoiceEnvelopeRepository
    extends InMemoryRepository<ElectronicInvoiceEnvelope>
    implements ElectronicInvoiceEnvelopeRepository {

  public InMemoryElectronicInvoiceEnvelopeRepository() {
    super(invoice -> invoice.invoiceCode());
  }
}
