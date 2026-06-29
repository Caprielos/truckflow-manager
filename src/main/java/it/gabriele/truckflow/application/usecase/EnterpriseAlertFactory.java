package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.domain.alerting.AlertEvent;
import it.gabriele.truckflow.domain.alerting.AlertSeverity;
import it.gabriele.truckflow.domain.alerting.AlertSourceType;
import it.gabriele.truckflow.domain.alerting.AlertType;
import it.gabriele.truckflow.domain.deadline.DeadlineSeverity;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.Locale;

/** Factory applicativa per creare alert standardizzati da controlli enterprise. */
public final class EnterpriseAlertFactory {

  private static final int MAX_CODE_LENGTH = 50;

  private EnterpriseAlertFactory() {}

  public static AlertEvent open(
      String prefix,
      String sourceCode,
      String suffix,
      AlertType type,
      AlertSeverity severity,
      AlertSourceType sourceType,
      String title,
      String message,
      Instant raisedAt,
      Notes notes) {
    return AlertEvent.open(
        buildCode(prefix, sourceCode, suffix),
        type,
        severity,
        sourceType,
        sourceCode,
        title,
        message,
        raisedAt,
        notes);
  }

  public static AlertSeverity fromDeadlineSeverity(DeadlineSeverity severity) {
    return switch (severity) {
      case CRITICAL -> AlertSeverity.CRITICAL;
      case HIGH -> AlertSeverity.HIGH;
      case MEDIUM -> AlertSeverity.WARNING;
      case LOW -> AlertSeverity.INFO;
    };
  }

  public static String buildCode(String prefix, String sourceCode, String suffix) {
    String normalizedPrefix = normalize(prefix);
    String normalizedSource = normalize(sourceCode);
    String normalizedSuffix = normalize(suffix);
    String candidate = normalizedPrefix + "_" + normalizedSource + "_" + normalizedSuffix;

    if (candidate.length() <= MAX_CODE_LENGTH) {
      return candidate;
    }

    return normalizedPrefix
        + "_"
        + Integer.toHexString(candidate.hashCode()).toUpperCase(Locale.ROOT);
  }

  private static String normalize(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Il pezzo codice alert è obbligatorio.");
    }

    return value.trim().toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9_-]", "_");
  }
}
