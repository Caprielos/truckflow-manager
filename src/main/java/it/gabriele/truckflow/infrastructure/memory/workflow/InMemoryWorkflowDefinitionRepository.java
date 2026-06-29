package it.gabriele.truckflow.infrastructure.memory.workflow;

import it.gabriele.truckflow.application.port.out.workflow.WorkflowDefinitionRepository;
import it.gabriele.truckflow.domain.workflow.WorkflowDefinition;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per definizioni workflow. */
public final class InMemoryWorkflowDefinitionRepository
    extends InMemoryRepository<WorkflowDefinition> implements WorkflowDefinitionRepository {

  public InMemoryWorkflowDefinitionRepository() {
    super(WorkflowDefinition::getWorkflowCode);
  }
}
