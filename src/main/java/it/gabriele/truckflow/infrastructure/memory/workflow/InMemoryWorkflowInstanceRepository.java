package it.gabriele.truckflow.infrastructure.memory.workflow;

import it.gabriele.truckflow.application.port.out.workflow.WorkflowInstanceRepository;
import it.gabriele.truckflow.domain.workflow.WorkflowInstance;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per istanze workflow. */
public final class InMemoryWorkflowInstanceRepository extends InMemoryRepository<WorkflowInstance>
    implements WorkflowInstanceRepository {

  public InMemoryWorkflowInstanceRepository() {
    super(WorkflowInstance::getInstanceCode);
  }
}
