package it.gabriele.truckflow.domain.workflow;

/** Regole per workflow configurabili. */
public final class WorkflowRules {

  private WorkflowRules() {}

  public static boolean canBeActivated(WorkflowDefinition definition) {
    validateDefinition(definition);

    return !definition.isActive() && !definition.getSteps().isEmpty();
  }

  public static boolean canStart(WorkflowDefinition definition) {
    validateDefinition(definition);

    return definition.isActive() && !definition.getSteps().isEmpty();
  }

  public static boolean canMoveToNextStep(WorkflowInstance instance) {
    validateInstance(instance);

    return instance.getStatus() == WorkflowStatus.IN_PROGRESS
        || instance.getStatus() == WorkflowStatus.WAITING_APPROVAL;
  }

  public static boolean canBeCompleted(WorkflowInstance instance) {
    validateInstance(instance);

    return instance.getStatus() == WorkflowStatus.IN_PROGRESS
        || instance.getStatus() == WorkflowStatus.WAITING_APPROVAL;
  }

  private static void validateDefinition(WorkflowDefinition definition) {
    if (definition == null) {
      throw new IllegalArgumentException("La definizione workflow è obbligatoria.");
    }
  }

  private static void validateInstance(WorkflowInstance instance) {
    if (instance == null) {
      throw new IllegalArgumentException("L'istanza workflow è obbligatoria.");
    }
  }
}
