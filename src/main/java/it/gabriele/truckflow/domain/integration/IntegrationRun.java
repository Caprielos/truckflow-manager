package it.gabriele.truckflow.domain.integration;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.Objects;

/** Singola esecuzione di import/export da sistema esterno. */
public final class IntegrationRun {

  private static final int MAX_CODE_LENGTH = 50;

  private final String runCode;
  private final String connectorCode;
  private final Instant startedAt;
  private final Instant finishedAt;
  private final int importedRecords;
  private final int failedRecords;
  private final IntegrationStatus status;
  private final Notes notes;

  private IntegrationRun(
      String runCode,
      String connectorCode,
      Instant startedAt,
      Instant finishedAt,
      int importedRecords,
      int failedRecords,
      IntegrationStatus status,
      Notes notes) {
    this.runCode = validateCode(runCode, "Il codice run integrazione è obbligatorio.");
    this.connectorCode = validateCode(connectorCode, "Il codice connettore run è obbligatorio.");

    if (startedAt == null) {
      throw new IllegalArgumentException("La data avvio run è obbligatoria.");
    }

    if (finishedAt != null && finishedAt.isBefore(startedAt)) {
      throw new IllegalArgumentException("La fine run non può precedere l'avvio.");
    }

    if (importedRecords < 0 || failedRecords < 0) {
      throw new IllegalArgumentException("I contatori import non possono essere negativi.");
    }

    if (status == null) {
      throw new IllegalArgumentException("Lo stato run è obbligatorio.");
    }

    if (status.isTerminal() && finishedAt == null) {
      throw new IllegalArgumentException("Un run chiuso richiede data fine.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note run integrazione sono obbligatorie.");
    }

    this.startedAt = startedAt;
    this.finishedAt = finishedAt;
    this.importedRecords = importedRecords;
    this.failedRecords = failedRecords;
    this.status = status;
    this.notes = notes;
  }

  public static IntegrationRun running(
      String runCode, String connectorCode, Instant startedAt, Notes notes) {
    return new IntegrationRun(
        runCode, connectorCode, startedAt, null, 0, 0, IntegrationStatus.RUNNING, notes);
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
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  public IntegrationRun complete(Instant finishedAt, int importedRecords, int failedRecords) {
    IntegrationStatus completionStatus =
        failedRecords > 0 ? IntegrationStatus.COMPLETED_WITH_ERRORS : IntegrationStatus.COMPLETED;

    return new IntegrationRun(
        runCode,
        connectorCode,
        startedAt,
        finishedAt,
        importedRecords,
        failedRecords,
        completionStatus,
        notes);
  }

  public IntegrationRun fail(Instant finishedAt, int failedRecords, Notes failureNotes) {
    if (failureNotes == null || failureNotes.isEmpty()) {
      throw new IllegalArgumentException("Le note errore integrazione sono obbligatorie.");
    }

    return new IntegrationRun(
        runCode,
        connectorCode,
        startedAt,
        finishedAt,
        importedRecords,
        failedRecords,
        IntegrationStatus.FAILED,
        failureNotes);
  }

  public String getRunCode() {
    return runCode;
  }

  public String getConnectorCode() {
    return connectorCode;
  }

  public Instant getStartedAt() {
    return startedAt;
  }

  public Instant getFinishedAt() {
    return finishedAt;
  }

  public int getImportedRecords() {
    return importedRecords;
  }

  public int getFailedRecords() {
    return failedRecords;
  }

  public IntegrationStatus getStatus() {
    return status;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean hasFailures() {
    return failedRecords > 0 || status == IntegrationStatus.FAILED;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IntegrationRun that)) return false;
    return importedRecords == that.importedRecords
        && failedRecords == that.failedRecords
        && runCode.equals(that.runCode)
        && connectorCode.equals(that.connectorCode)
        && startedAt.equals(that.startedAt)
        && Objects.equals(finishedAt, that.finishedAt)
        && status == that.status
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        runCode,
        connectorCode,
        startedAt,
        finishedAt,
        importedRecords,
        failedRecords,
        status,
        notes);
  }
}
