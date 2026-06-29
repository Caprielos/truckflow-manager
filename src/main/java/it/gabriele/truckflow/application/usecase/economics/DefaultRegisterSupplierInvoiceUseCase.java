package it.gabriele.truckflow.application.usecase.economics;

import it.gabriele.truckflow.application.port.in.RegisterSupplierInvoiceUseCase;
import it.gabriele.truckflow.application.port.out.SupplierInvoiceRepository;
import it.gabriele.truckflow.domain.economics.SupplierInvoice;
import java.util.Objects;

/** Caso d'uso: registrare una fattura fornitore. */
public final class DefaultRegisterSupplierInvoiceUseCase implements RegisterSupplierInvoiceUseCase {

  private final SupplierInvoiceRepository repository;

  public DefaultRegisterSupplierInvoiceUseCase(SupplierInvoiceRepository repository) {
    this.repository =
        Objects.requireNonNull(repository, "Il repository fatture fornitore è obbligatorio.");
  }

  @Override
  public SupplierInvoice handle(Command command) {
    Objects.requireNonNull(command, "Il comando fattura fornitore è obbligatorio.");
    repository.save(command.invoice());
    return command.invoice();
  }
}
