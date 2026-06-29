package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.RegisterElectronicInvoiceUseCase;
import it.gabriele.truckflow.application.port.out.ElectronicInvoiceEnvelopeRepository;
import it.gabriele.truckflow.domain.financeops.ElectronicInvoiceEnvelope;
import java.util.Objects;

/** Implementazione default di RegisterElectronicInvoiceUseCase. */
public final class DefaultRegisterElectronicInvoiceUseCase
    implements RegisterElectronicInvoiceUseCase {

  private final ElectronicInvoiceEnvelopeRepository repository;

  public DefaultRegisterElectronicInvoiceUseCase(ElectronicInvoiceEnvelopeRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public ElectronicInvoiceEnvelope handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    ElectronicInvoiceEnvelope aggregate =
        Objects.requireNonNull(command.invoice(), "La fattura elettronica è obbligatoria.");
    repository.save(aggregate);
    return aggregate;
  }
}
