package it.gabriele.truckflow.application.usecase.workflow;

import it.gabriele.truckflow.application.port.in.ActivateWorkflowDefinitionUseCase;
import it.gabriele.truckflow.application.port.out.WorkflowDefinitionRepository;
import it.gabriele.truckflow.domain.workflow.WorkflowDefinition;
import java.util.Objects;

/** Caso d'uso: attivare una definizione workflow. */
public final class DefaultActivateWorkflowDefinitionUseCase
    implements ActivateWorkflowDefinitionUseCase {

  private final WorkflowDefinitionRepository definitionRepository;

  public DefaultActivateWorkflowDefinitionUseCase(
      WorkflowDefinitionRepository definitionRepository) {
    this.definitionRepository =
        Objects.requireNonNull(definitionRepository, "Il repository workflow è obbligatorio.");
  }

  @Override
  public WorkflowDefinition handle(Command command) {
    Objects.requireNonNull(command, "Il comando attivazione workflow è obbligatorio.");
    WorkflowDefinition definition =
        definitionRepository.getRequired(command.workflowCode(), "Definizione workflow");
    WorkflowDefinition activated = definition.activate();
    definitionRepository.save(activated);
    return activated;
  }
}
