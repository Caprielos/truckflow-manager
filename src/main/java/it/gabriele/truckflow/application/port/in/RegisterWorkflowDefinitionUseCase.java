package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.workflow.WorkflowDefinition;

public interface RegisterWorkflowDefinitionUseCase {

  WorkflowDefinition handle(Command command);

  record Command(WorkflowDefinition definition) {}
}
