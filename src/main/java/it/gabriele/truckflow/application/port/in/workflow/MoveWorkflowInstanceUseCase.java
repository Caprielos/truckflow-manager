package it.gabriele.truckflow.application.port.in.workflow;

import it.gabriele.truckflow.domain.workflow.WorkflowInstance;

public interface MoveWorkflowInstanceUseCase {

  WorkflowInstance handle(Command command);

  record Command(String instanceCode, String nextStepCode) {}
}
