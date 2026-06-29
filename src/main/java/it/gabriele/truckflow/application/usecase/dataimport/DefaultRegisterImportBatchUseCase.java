package it.gabriele.truckflow.application.usecase.dataimport;

import it.gabriele.truckflow.application.port.in.dataimport.RegisterImportBatchUseCase;
import it.gabriele.truckflow.application.port.out.ImportBatchRepository;
import it.gabriele.truckflow.domain.dataimport.ImportBatch;
import java.util.Objects;

/** Caso d'uso: registrare un batch importato da fonte esterna. */
public final class DefaultRegisterImportBatchUseCase implements RegisterImportBatchUseCase {

  private final ImportBatchRepository repository;

  public DefaultRegisterImportBatchUseCase(ImportBatchRepository repository) {
    this.repository =
        Objects.requireNonNull(repository, "Il repository batch import è obbligatorio.");
  }

  @Override
  public ImportBatch handle(Command command) {
    Objects.requireNonNull(command, "Il comando batch import è obbligatorio.");
    repository.save(command.batch());
    return command.batch();
  }
}
