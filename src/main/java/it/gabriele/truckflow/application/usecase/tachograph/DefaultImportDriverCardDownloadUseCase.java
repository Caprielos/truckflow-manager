package it.gabriele.truckflow.application.usecase.tachograph;

import it.gabriele.truckflow.application.port.in.tachograph.ImportDriverCardDownloadUseCase;
import it.gabriele.truckflow.application.port.out.tachograph.DriverCardDownloadRepository;
import it.gabriele.truckflow.domain.tachograph.DriverCardDownload;
import java.util.Objects;

/** Implementazione default di ImportDriverCardDownloadUseCase. */
public final class DefaultImportDriverCardDownloadUseCase
    implements ImportDriverCardDownloadUseCase {

  private final DriverCardDownloadRepository repository;

  public DefaultImportDriverCardDownloadUseCase(DriverCardDownloadRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public DriverCardDownload handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    DriverCardDownload aggregate =
        Objects.requireNonNull(command.download(), "Lo scarico carta conducente è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
