package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.ElectronicInvoiceEnvelopeRepository;
import it.gabriele.truckflow.domain.financeops.ElectronicInvoiceEnvelope;

/** Repository in memoria per ElectronicInvoiceEnvelope. */
public final class InMemoryElectronicInvoiceEnvelopeRepository
    extends InMemoryRepository<ElectronicInvoiceEnvelope>
    implements ElectronicInvoiceEnvelopeRepository {

  public InMemoryElectronicInvoiceEnvelopeRepository() {
    super(invoice -> invoice.invoiceCode());
  }
}
