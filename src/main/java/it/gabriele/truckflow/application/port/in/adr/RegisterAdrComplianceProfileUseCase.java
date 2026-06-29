package it.gabriele.truckflow.application.port.in.adr;

import it.gabriele.truckflow.domain.adr.AdrComplianceProfile;

public interface RegisterAdrComplianceProfileUseCase {
  AdrComplianceProfile handle(Command command);

  record Command(AdrComplianceProfile profile) {}
}
