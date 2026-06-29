package it.gabriele.truckflow.domain.workflow;

import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/** Definizione configurabile di un processo aziendale. */
public final class WorkflowDefinition {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_NAME_LENGTH = 120;

  private final String workflowCode;
  private final String name;
  private final List<WorkflowStep> steps;
  private final boolean active;
  private final Notes notes;

  private WorkflowDefinition(
      String workflowCode, String name, List<WorkflowStep> steps, boolean active, Notes notes) {
    this.workflowCode = validateCode(workflowCode, "Il codice workflow è obbligatorio.");
    this.name = validateName(name);

    if (steps == null || steps.isEmpty()) {
      throw new IllegalArgumentException("Il workflow deve avere almeno uno step.");
    }

    if (steps.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("Gli step workflow non possono contenere null.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note workflow sono obbligatorie.");
    }

    this.steps = steps.stream().sorted(Comparator.comparingInt(WorkflowStep::getSequence)).toList();
    this.active = active;
    this.notes = notes;
  }

  public static WorkflowDefinition draft(
      String workflowCode, String name, List<WorkflowStep> steps, Notes notes) {
    return new WorkflowDefinition(workflowCode, name, steps, false, notes);
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

  private static String validateName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Il nome workflow è obbligatorio.");
    }

    String normalizedName = name.trim();

    if (normalizedName.isEmpty()) {
      throw new IllegalArgumentException("Il nome workflow non può essere vuoto.");
    }

    if (normalizedName.length() > MAX_NAME_LENGTH) {
      throw new IllegalArgumentException(
          "Il nome workflow non può superare " + MAX_NAME_LENGTH + " caratteri.");
    }

    return normalizedName;
  }

  public WorkflowDefinition activate() {
    if (!WorkflowRules.canBeActivated(this)) {
      throw new IllegalStateException("Il workflow non può essere attivato.");
    }

    return new WorkflowDefinition(workflowCode, name, steps, true, notes);
  }

  public WorkflowDefinition deactivate() {
    return new WorkflowDefinition(workflowCode, name, steps, false, notes);
  }

  public String getWorkflowCode() {
    return workflowCode;
  }

  public String getName() {
    return name;
  }

  public List<WorkflowStep> getSteps() {
    return steps;
  }

  public boolean isActive() {
    return active;
  }

  public Notes getNotes() {
    return notes;
  }

  public WorkflowStep getFirstStep() {
    return steps.get(0);
  }

  public boolean containsStep(String stepCode) {
    if (stepCode == null) {
      throw new IllegalArgumentException("Il codice step da cercare è obbligatorio.");
    }

    String normalizedStepCode = stepCode.trim().toUpperCase();
    return steps.stream().anyMatch(step -> step.getStepCode().equals(normalizedStepCode));
  }
}
