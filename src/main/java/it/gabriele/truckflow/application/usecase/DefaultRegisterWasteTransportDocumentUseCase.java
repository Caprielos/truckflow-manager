package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.RegisterWasteTransportDocumentUseCase;
import it.gabriele.truckflow.application.port.out.WasteTransportDocumentRepository;
import it.gabriele.truckflow.domain.waste.WasteTransportDocument;
import java.util.Objects;

/** Implementazione default di RegisterWasteTransportDocumentUseCase. */
public final class DefaultRegisterWasteTransportDocumentUseCase
    implements RegisterWasteTransportDocumentUseCase {

  private final WasteTransportDocumentRepository repository;

  public DefaultRegisterWasteTransportDocumentUseCase(WasteTransportDocumentRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public WasteTransportDocument handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    WasteTransportDocument aggregate =
        Objects.requireNonNull(command.document(), "Il documento rifiuti è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
