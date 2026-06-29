package it.gabriele.truckflow.application.port.in.deadline;

import it.gabriele.truckflow.domain.deadline.EnterpriseDeadline;

public interface RegisterEnterpriseDeadlineUseCase {

  EnterpriseDeadline handle(Command command);

  record Command(EnterpriseDeadline deadline) {}
}
