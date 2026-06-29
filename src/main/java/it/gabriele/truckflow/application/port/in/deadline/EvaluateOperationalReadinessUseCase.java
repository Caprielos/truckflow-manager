package it.gabriele.truckflow.application.port.in.deadline;

import it.gabriele.truckflow.domain.deadline.DeadlineOwnerType;
import java.time.LocalDate;
import java.util.List;

public interface EvaluateOperationalReadinessUseCase {

  Report handle(Command command);

  record Command(DeadlineOwnerType ownerType, String ownerCode, LocalDate today) {}

  record Report(boolean ready, List<String> blockers, List<String> warnings) {}
}
