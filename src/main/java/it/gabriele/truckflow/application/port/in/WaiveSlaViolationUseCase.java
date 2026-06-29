package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.sla.SlaViolation;

public interface WaiveSlaViolationUseCase {

  SlaViolation handle(Command command);

  record Command(String violationCode, Notes waiverNotes) {}
}
