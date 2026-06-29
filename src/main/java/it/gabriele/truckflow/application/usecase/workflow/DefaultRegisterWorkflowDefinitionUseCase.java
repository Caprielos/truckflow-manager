package it.gabriele.truckflow.application.usecase.workflow;

import it.gabriele.truckflow.application.port.in.workflow.RegisterWorkflowDefinitionUseCase;
import it.gabriele.truckflow.application.port.out.workflow.WorkflowDefinitionRepository;
import it.gabriele.truckflow.domain.workflow.WorkflowDefinition;
import java.util.Objects;

/** Caso d'uso: registrare una definizione workflow configurabile. */
public final class DefaultRegisterWorkflowDefinitionUseCase
    implements RegisterWorkflowDefinitionUseCase {

  private final WorkflowDefinitionRepository definitionRepository;

  public DefaultRegisterWorkflowDefinitionUseCase(
      WorkflowDefinitionRepository definitionRepository) {
    this.definitionRepository =
        Objects.requireNonNull(definitionRepository, "Il repository workflow è obbligatorio.");
  }

  @Override
  public WorkflowDefinition handle(Command command) {
    Objects.requireNonNull(command, "Il comando registrazione workflow è obbligatorio.");
    WorkflowDefinition definition =
        Objects.requireNonNull(command.definition(), "La definizione workflow è obbligatoria.");
    definitionRepository.save(definition);
    return definition;
  }
}
