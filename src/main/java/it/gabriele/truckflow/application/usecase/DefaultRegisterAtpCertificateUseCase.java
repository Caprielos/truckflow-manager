package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.RegisterAtpCertificateUseCase;
import it.gabriele.truckflow.application.port.out.AtpCertificateRepository;
import it.gabriele.truckflow.domain.atp.AtpCertificate;
import java.util.Objects;

/** Implementazione default di RegisterAtpCertificateUseCase. */
public final class DefaultRegisterAtpCertificateUseCase implements RegisterAtpCertificateUseCase {

  private final AtpCertificateRepository repository;

  public DefaultRegisterAtpCertificateUseCase(AtpCertificateRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public AtpCertificate handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    AtpCertificate aggregate =
        Objects.requireNonNull(command.certificate(), "Il certificato ATP è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
