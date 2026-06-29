package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.workflow.WorkflowInstance;
import java.time.Instant;

public interface CompleteWorkflowInstanceUseCase {

  WorkflowInstance handle(Command command);

  record Command(String instanceCode, Instant completedAt) {}
}
