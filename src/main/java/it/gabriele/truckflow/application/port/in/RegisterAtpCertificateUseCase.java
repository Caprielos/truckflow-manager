package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.atp.AtpCertificate;

public interface RegisterAtpCertificateUseCase {
  AtpCertificate handle(Command command);

  record Command(AtpCertificate certificate) {}
}
