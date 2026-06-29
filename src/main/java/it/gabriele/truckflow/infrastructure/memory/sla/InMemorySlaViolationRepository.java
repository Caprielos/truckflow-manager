package it.gabriele.truckflow.infrastructure.memory.sla;

import it.gabriele.truckflow.application.port.out.sla.SlaViolationRepository;
import it.gabriele.truckflow.domain.sla.SlaViolation;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per violazioni SLA. */
public final class InMemorySlaViolationRepository extends InMemoryRepository<SlaViolation>
    implements SlaViolationRepository {

  public InMemorySlaViolationRepository() {
    super(SlaViolation::getViolationCode);
  }
}
