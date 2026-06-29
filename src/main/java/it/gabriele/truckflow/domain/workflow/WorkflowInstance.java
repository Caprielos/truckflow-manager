package it.gabriele.truckflow.domain.workflow;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.Objects;

/** Esecuzione concreta di un workflow su claim, contratto, documento o missione. */
public final class WorkflowInstance {

  private static final int MAX_CODE_LENGTH = 50;

  private final String instanceCode;
  private final String workflowCode;
  private final String currentStepCode;
  private final WorkflowStatus status;
  private final Instant startedAt;
  private final Instant completedAt;
  private final Notes notes;

  private WorkflowInstance(
      String instanceCode,
      String workflowCode,
      String currentStepCode,
      WorkflowStatus status,
      Instant startedAt,
      Instant completedAt,
      Notes notes) {
    this.instanceCode = validateCode(instanceCode, "Il codice istanza workflow è obbligatorio.");
    this.workflowCode = validateCode(workflowCode, "Il codice workflow è obbligatorio.");
    this.currentStepCode = currentStepCode == null ? null : validateCode(currentStepCode, "");

    if (status == null) {
      throw new IllegalArgumentException("Lo stato istanza workflow è obbligatorio.");
    }

    if (startedAt == null) {
      throw new IllegalArgumentException("La data avvio workflow è obbligatoria.");
    }

    if (status.isTerminal() && completedAt == null) {
      throw new IllegalArgumentException("Un workflow chiuso richiede una data completamento.");
    }

    if (completedAt != null && completedAt.isBefore(startedAt)) {
      throw new IllegalArgumentException("La chiusura workflow non può precedere l'avvio.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note istanza workflow sono obbligatorie.");
    }

    this.status = status;
    this.startedAt = startedAt;
    this.completedAt = completedAt;
    this.notes = notes;
  }

  public static WorkflowInstance start(
      String instanceCode, WorkflowDefinition definition, Instant startedAt, Notes notes) {
    if (definition == null) {
      throw new IllegalArgumentException("La definizione workflow è obbligatoria.");
    }

    if (!WorkflowRules.canStart(definition)) {
      throw new IllegalStateException("Il workflow non può essere avviato.");
    }

    return new WorkflowInstance(
        instanceCode,
        definition.getWorkflowCode(),
        definition.getFirstStep().getStepCode(),
        WorkflowStatus.IN_PROGRESS,
        startedAt,
        null,
        notes);
  }

  private static String validateCode(String code, String nullMessage) {
    if (code == null) {
      throw new IllegalArgumentException(nullMessage);
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice workflow non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice workflow può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  public WorkflowInstance moveTo(String nextStepCode) {
    if (!WorkflowRules.canMoveToNextStep(this)) {
      throw new IllegalStateException("Il workflow non può avanzare.");
    }

    return new WorkflowInstance(
        instanceCode,
        workflowCode,
        nextStepCode,
        WorkflowStatus.IN_PROGRESS,
        startedAt,
        null,
        notes);
  }

  public WorkflowInstance waitApproval() {
    if (!WorkflowRules.canMoveToNextStep(this)) {
      throw new IllegalStateException("Il workflow non può andare in approvazione.");
    }

    return new WorkflowInstance(
        instanceCode,
        workflowCode,
        currentStepCode,
        WorkflowStatus.WAITING_APPROVAL,
        startedAt,
        null,
        notes);
  }

  public WorkflowInstance complete(Instant completedAt) {
    if (!WorkflowRules.canBeCompleted(this)) {
      throw new IllegalStateException("Il workflow non può essere completato.");
    }

    return new WorkflowInstance(
        instanceCode,
        workflowCode,
        currentStepCode,
        WorkflowStatus.COMPLETED,
        startedAt,
        completedAt,
        notes);
  }

  public String getInstanceCode() {
    return instanceCode;
  }

  public String getWorkflowCode() {
    return workflowCode;
  }

  public String getCurrentStepCode() {
    return currentStepCode;
  }

  public WorkflowStatus getStatus() {
    return status;
  }

  public Instant getStartedAt() {
    return startedAt;
  }

  public Instant getCompletedAt() {
    return completedAt;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isActive() {
    return status.isActive();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof WorkflowInstance that)) return false;
    return instanceCode.equals(that.instanceCode)
        && workflowCode.equals(that.workflowCode)
        && Objects.equals(currentStepCode, that.currentStepCode)
        && status == that.status
        && startedAt.equals(that.startedAt)
        && Objects.equals(completedAt, that.completedAt)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        instanceCode, workflowCode, currentStepCode, status, startedAt, completedAt, notes);
  }
}
