package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.SlaViolationRepository;
import it.gabriele.truckflow.domain.sla.SlaViolation;

/** Repository in memoria per violazioni SLA. */
public final class InMemorySlaViolationRepository extends InMemoryRepository<SlaViolation>
    implements SlaViolationRepository {

  public InMemorySlaViolationRepository() {
    super(SlaViolation::getViolationCode);
  }
}
