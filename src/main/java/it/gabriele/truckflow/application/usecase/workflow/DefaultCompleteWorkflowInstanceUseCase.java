package it.gabriele.truckflow.application.usecase.workflow;

import it.gabriele.truckflow.application.port.in.workflow.CompleteWorkflowInstanceUseCase;
import it.gabriele.truckflow.application.port.out.workflow.WorkflowInstanceRepository;
import it.gabriele.truckflow.domain.workflow.WorkflowInstance;
import java.util.Objects;

/** Caso d'uso: completare una istanza workflow. */
public final class DefaultCompleteWorkflowInstanceUseCase
    implements CompleteWorkflowInstanceUseCase {

  private final WorkflowInstanceRepository instanceRepository;

  public DefaultCompleteWorkflowInstanceUseCase(WorkflowInstanceRepository instanceRepository) {
    this.instanceRepository =
        Objects.requireNonNull(
            instanceRepository, "Il repository istanze workflow è obbligatorio.");
  }

  @Override
  public WorkflowInstance handle(Command command) {
    Objects.requireNonNull(command, "Il comando completamento workflow è obbligatorio.");
    WorkflowInstance instance =
        instanceRepository.getRequired(command.instanceCode(), "Istanza workflow");
    WorkflowInstance completed = instance.complete(command.completedAt());
    instanceRepository.save(completed);
    return completed;
  }
}
