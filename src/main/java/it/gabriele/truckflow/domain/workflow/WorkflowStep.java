package it.gabriele.truckflow.domain.workflow;

import java.util.Objects;

/** Passo configurabile di un workflow enterprise. */
public final class WorkflowStep {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_NAME_LENGTH = 120;

  private final String stepCode;
  private final String name;
  private final int sequence;
  private final boolean approvalRequired;

  private WorkflowStep(String stepCode, String name, int sequence, boolean approvalRequired) {
    this.stepCode = validateCode(stepCode, "Il codice step workflow è obbligatorio.");
    this.name = validateText(name);

    if (sequence <= 0) {
      throw new IllegalArgumentException("La sequenza step workflow deve essere positiva.");
    }

    this.sequence = sequence;
    this.approvalRequired = approvalRequired;
  }

  public static WorkflowStep of(
      String stepCode, String name, int sequence, boolean approvalRequired) {
    return new WorkflowStep(stepCode, name, sequence, approvalRequired);
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
          "Il codice step non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice step può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  private static String validateText(String text) {
    if (text == null) {
      throw new IllegalArgumentException("Il nome step workflow è obbligatorio.");
    }

    String normalizedText = text.trim();

    if (normalizedText.isEmpty()) {
      throw new IllegalArgumentException("Il nome step workflow non può essere vuoto.");
    }

    if (normalizedText.length() > MAX_NAME_LENGTH) {
      throw new IllegalArgumentException(
          "Il nome step workflow non può superare " + MAX_NAME_LENGTH + " caratteri.");
    }

    return normalizedText;
  }

  public String getStepCode() {
    return stepCode;
  }

  public String getName() {
    return name;
  }

  public int getSequence() {
    return sequence;
  }

  public boolean isApprovalRequired() {
    return approvalRequired;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof WorkflowStep that)) return false;
    return sequence == that.sequence
        && approvalRequired == that.approvalRequired
        && stepCode.equals(that.stepCode)
        && name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stepCode, name, sequence, approvalRequired);
  }
}
