package it.gabriele.truckflow.infrastructure.memory.atp;

import it.gabriele.truckflow.application.port.out.AtpCertificateRepository;
import it.gabriele.truckflow.domain.atp.AtpCertificate;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per AtpCertificate. */
public final class InMemoryAtpCertificateRepository extends InMemoryRepository<AtpCertificate>
    implements AtpCertificateRepository {

  public InMemoryAtpCertificateRepository() {
    super(certificate -> certificate.certificateCode());
  }
}
