package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.WorkflowDefinitionRepository;
import it.gabriele.truckflow.domain.workflow.WorkflowDefinition;

/** Repository in memoria per definizioni workflow. */
public final class InMemoryWorkflowDefinitionRepository
    extends InMemoryRepository<WorkflowDefinition> implements WorkflowDefinitionRepository {

  public InMemoryWorkflowDefinitionRepository() {
    super(WorkflowDefinition::getWorkflowCode);
  }
}
