package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.workflow.WorkflowInstance;
import java.time.Instant;

public interface StartWorkflowInstanceUseCase {

  WorkflowInstance handle(Command command);

  record Command(String instanceCode, String workflowCode, Instant startedAt, Notes notes) {}
}
