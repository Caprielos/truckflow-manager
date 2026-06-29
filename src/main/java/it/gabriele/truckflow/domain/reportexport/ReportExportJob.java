package it.gabriele.truckflow.domain.reportexport;

import java.time.LocalDateTime;
import java.util.Set;

/** Job di generazione report PDF/Excel/CSV. */
public record ReportExportJob(
    String jobCode,
    ReportExportType reportType,
    ReportExportFormat format,
    ReportExportStatus status,
    LocalDateTime requestedAt,
    String requestedBy,
    Set<String> filters,
    String outputReference) {

  public ReportExportJob {
    jobCode = normalize(jobCode, "Il codice job report è obbligatorio.");
    if (reportType == null || format == null || status == null) {
      throw new IllegalArgumentException("Tipo, formato e stato report sono obbligatori.");
    }
    if (requestedAt == null) {
      throw new IllegalArgumentException("La data richiesta report è obbligatoria.");
    }
    requestedBy = normalize(requestedBy, "Il richiedente report è obbligatorio.");
    filters = filters == null ? Set.of() : Set.copyOf(filters);
    outputReference = outputReference == null ? "" : outputReference.trim();
  }

  public boolean hasOutput() {
    return !outputReference.isBlank();
  }

  private static String normalize(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase();
  }
}
