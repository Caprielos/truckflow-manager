package it.gabriele.truckflow.application.port.in.financeops;

import it.gabriele.truckflow.domain.financeops.ElectronicInvoiceEnvelope;

public interface RegisterElectronicInvoiceUseCase {
  ElectronicInvoiceEnvelope handle(Command command);

  record Command(ElectronicInvoiceEnvelope invoice) {}
}
