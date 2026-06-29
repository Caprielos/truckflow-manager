package it.gabriele.truckflow.application.port.in.economics;

import it.gabriele.truckflow.domain.economics.SupplierInvoice;

public interface RegisterSupplierInvoiceUseCase {

  SupplierInvoice handle(Command command);

  record Command(SupplierInvoice invoice) {}
}
