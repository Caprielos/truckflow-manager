package it.gabriele.truckflow.application.usecase.workflow;

import it.gabriele.truckflow.application.port.in.StartWorkflowInstanceUseCase;
import it.gabriele.truckflow.application.port.out.WorkflowDefinitionRepository;
import it.gabriele.truckflow.application.port.out.WorkflowInstanceRepository;
import it.gabriele.truckflow.domain.workflow.WorkflowDefinition;
import it.gabriele.truckflow.domain.workflow.WorkflowInstance;
import java.util.Objects;

/** Caso d'uso: avviare una istanza workflow su un processo reale. */
public final class DefaultStartWorkflowInstanceUseCase implements StartWorkflowInstanceUseCase {

  private final WorkflowDefinitionRepository definitionRepository;
  private final WorkflowInstanceRepository instanceRepository;

  public DefaultStartWorkflowInstanceUseCase(
      WorkflowDefinitionRepository definitionRepository,
      WorkflowInstanceRepository instanceRepository) {
    this.definitionRepository =
        Objects.requireNonNull(definitionRepository, "Il repository workflow è obbligatorio.");
    this.instanceRepository =
        Objects.requireNonNull(
            instanceRepository, "Il repository istanze workflow è obbligatorio.");
  }

  @Override
  public WorkflowInstance handle(Command command) {
    Objects.requireNonNull(command, "Il comando avvio workflow è obbligatorio.");
    WorkflowDefinition definition =
        definitionRepository.getRequired(command.workflowCode(), "Definizione workflow");
    WorkflowInstance instance =
        WorkflowInstance.start(
            command.instanceCode(), definition, command.startedAt(), command.notes());
    instanceRepository.save(instance);
    return instance;
  }
}
