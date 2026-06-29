package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.AtpCertificateRepository;
import it.gabriele.truckflow.domain.atp.AtpCertificate;

/** Repository in memoria per AtpCertificate. */
public final class InMemoryAtpCertificateRepository extends InMemoryRepository<AtpCertificate>
    implements AtpCertificateRepository {

  public InMemoryAtpCertificateRepository() {
    super(certificate -> certificate.certificateCode());
  }
}
