package it.gabriele.truckflow.domain.dataimport;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/** Lotto di importazione da una fonte esterna. */
public final class ImportBatch {

  private static final int MAX_CODE_LENGTH = 50;

  private final String batchCode;
  private final ExternalDataSourceType sourceType;
  private final LocalDateTime importedAt;
  private final List<ImportRecord> records;
  private final Notes notes;

  private ImportBatch(
      String batchCode,
      ExternalDataSourceType sourceType,
      LocalDateTime importedAt,
      List<ImportRecord> records,
      Notes notes) {
    this.batchCode = validateCode(batchCode);
    if (sourceType == null) {
      throw new IllegalArgumentException("Il tipo fonte lotto import è obbligatorio.");
    }
    if (importedAt == null) {
      throw new IllegalArgumentException("La data/ora lotto import è obbligatoria.");
    }
    this.records = validateRecords(records, sourceType);
    if (notes == null) {
      throw new IllegalArgumentException("Le note lotto import sono obbligatorie.");
    }
    this.sourceType = sourceType;
    this.importedAt = importedAt;
    this.notes = notes;
  }

  public static ImportBatch of(
      String batchCode,
      ExternalDataSourceType sourceType,
      LocalDateTime importedAt,
      List<ImportRecord> records,
      Notes notes) {
    return new ImportBatch(batchCode, sourceType, importedAt, records, notes);
  }

  private static String validateCode(String code) {
    if (code == null) {
      throw new IllegalArgumentException("Il codice lotto import è obbligatorio.");
    }
    String normalized = code.trim().toUpperCase();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("Il codice lotto import non può essere vuoto.");
    }
    if (normalized.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice lotto import non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }
    if (!normalized.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice lotto import può contenere solo lettere, numeri, trattini e underscore.");
    }
    return normalized;
  }

  private static List<ImportRecord> validateRecords(
      List<ImportRecord> records, ExternalDataSourceType sourceType) {
    if (records == null) {
      throw new IllegalArgumentException("I record lotto import sono obbligatori.");
    }
    if (records.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("I record lotto import non possono contenere null.");
    }
    long uniqueExternalRows =
        records.stream().map(ImportRecord::getExternalRowId).distinct().count();
    if (uniqueExternalRows != records.size()) {
      throw new IllegalArgumentException("Il lotto import contiene righe esterne duplicate.");
    }
    boolean wrongSource = records.stream().anyMatch(record -> record.getSourceType() != sourceType);
    if (wrongSource) {
      throw new IllegalArgumentException(
          "Tutti i record import devono appartenere alla stessa fonte del lotto.");
    }
    return List.copyOf(records);
  }

  public String getBatchCode() {
    return batchCode;
  }

  public ExternalDataSourceType getSourceType() {
    return sourceType;
  }

  public LocalDateTime getImportedAt() {
    return importedAt;
  }

  public List<ImportRecord> getRecords() {
    return records;
  }

  public Notes getNotes() {
    return notes;
  }

  public long countValidatedRecords() {
    return records.stream()
        .filter(record -> record.getStatus() == ImportRecordStatus.VALIDATED)
        .count();
  }

  public long countRejectedRecords() {
    return records.stream()
        .filter(record -> record.getStatus() == ImportRecordStatus.REJECTED)
        .count();
  }

  public boolean hasRejectedRecords() {
    return countRejectedRecords() > 0;
  }

  public Money calculateAmountTotal(Currency currency) {
    if (currency == null) {
      throw new IllegalArgumentException("La valuta totale import è obbligatoria.");
    }
    BigDecimal total = BigDecimal.ZERO;
    for (ImportRecord record : records) {
      if (record.hasAmount()) {
        Money amount = record.getAmount().orElseThrow();
        if (!amount.getCurrency().equals(currency)) {
          throw new IllegalArgumentException(
              "Tutti gli importi del lotto devono avere la valuta richiesta.");
        }
        total = total.add(amount.getAmount());
      }
    }
    return Money.of(total, currency);
  }
}
