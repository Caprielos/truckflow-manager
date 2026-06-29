package it.gabriele.truckflow.domain.dispatch;

import java.util.Objects;

/** Risultato di un controllo dispatch. */
public final class DispatchCheckResult {

  private static final int MAX_MESSAGE_LENGTH = 250;

  private final DispatchCheckType type;
  private final DispatchReadinessStatus status;
  private final String message;

  private DispatchCheckResult(
      DispatchCheckType type, DispatchReadinessStatus status, String message) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo controllo dispatch è obbligatorio.");
    }
    if (status == null) {
      throw new IllegalArgumentException("Lo stato controllo dispatch è obbligatorio.");
    }
    this.message = validateMessage(message);
    this.type = type;
    this.status = status;
  }

  public static DispatchCheckResult ready(DispatchCheckType type, String message) {
    return new DispatchCheckResult(type, DispatchReadinessStatus.READY, message);
  }

  public static DispatchCheckResult warning(DispatchCheckType type, String message) {
    return new DispatchCheckResult(type, DispatchReadinessStatus.WARNING, message);
  }

  public static DispatchCheckResult blocked(DispatchCheckType type, String message) {
    return new DispatchCheckResult(type, DispatchReadinessStatus.BLOCKED, message);
  }

  private static String validateMessage(String message) {
    if (message == null) {
      throw new IllegalArgumentException("Il messaggio controllo dispatch è obbligatorio.");
    }
    String normalized = message.trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("Il messaggio controllo dispatch non può essere vuoto.");
    }
    if (normalized.length() > MAX_MESSAGE_LENGTH) {
      throw new IllegalArgumentException(
          "Il messaggio controllo dispatch non può superare " + MAX_MESSAGE_LENGTH + " caratteri.");
    }
    return normalized;
  }

  public DispatchCheckType getType() {
    return type;
  }

  public DispatchReadinessStatus getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public boolean blocksAssignment() {
    return status.blocksAssignment();
  }

  public boolean requiresManualReview() {
    return status.requiresManualReview();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DispatchCheckResult that)) return false;
    return type == that.type && status == that.status && message.equals(that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, status, message);
  }
}
