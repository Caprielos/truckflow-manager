package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.WorkflowInstanceRepository;
import it.gabriele.truckflow.domain.workflow.WorkflowInstance;

/** Repository in memoria per istanze workflow. */
public final class InMemoryWorkflowInstanceRepository extends InMemoryRepository<WorkflowInstance>
    implements WorkflowInstanceRepository {

  public InMemoryWorkflowInstanceRepository() {
    super(WorkflowInstance::getInstanceCode);
  }
}
