package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.RegisterDocumentVersionUseCase;
import it.gabriele.truckflow.application.port.out.DocumentVersionRepository;
import it.gabriele.truckflow.domain.document.DocumentVersion;
import java.util.Objects;

/** Caso d'uso: registrare una versione documentale. */
public final class DefaultRegisterDocumentVersionUseCase implements RegisterDocumentVersionUseCase {

  private final DocumentVersionRepository versionRepository;

  public DefaultRegisterDocumentVersionUseCase(DocumentVersionRepository versionRepository) {
    this.versionRepository =
        Objects.requireNonNull(
            versionRepository, "Il repository versioni documento è obbligatorio.");
  }

  @Override
  public DocumentVersion handle(Command command) {
    Objects.requireNonNull(command, "Il comando versione documento è obbligatorio.");
    DocumentVersion version =
        Objects.requireNonNull(command.version(), "La versione documento è obbligatoria.");
    versionRepository.save(version);
    return version;
  }
}
