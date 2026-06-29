package it.gabriele.truckflow.application.usecase.workflow;

import it.gabriele.truckflow.application.port.in.workflow.MoveWorkflowInstanceUseCase;
import it.gabriele.truckflow.application.port.out.workflow.WorkflowInstanceRepository;
import it.gabriele.truckflow.domain.workflow.WorkflowInstance;
import java.util.Objects;

/** Caso d'uso: avanzare una istanza workflow allo step successivo. */
public final class DefaultMoveWorkflowInstanceUseCase implements MoveWorkflowInstanceUseCase {

  private final WorkflowInstanceRepository instanceRepository;

  public DefaultMoveWorkflowInstanceUseCase(WorkflowInstanceRepository instanceRepository) {
    this.instanceRepository =
        Objects.requireNonNull(
            instanceRepository, "Il repository istanze workflow è obbligatorio.");
  }

  @Override
  public WorkflowInstance handle(Command command) {
    Objects.requireNonNull(command, "Il comando avanzamento workflow è obbligatorio.");
    WorkflowInstance instance =
        instanceRepository.getRequired(command.instanceCode(), "Istanza workflow");
    WorkflowInstance moved = instance.moveTo(command.nextStepCode());
    instanceRepository.save(moved);
    return moved;
  }
}
