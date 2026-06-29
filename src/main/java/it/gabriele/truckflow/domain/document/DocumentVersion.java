package it.gabriele.truckflow.domain.document;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.Objects;

/** Versione immutabile di un documento aziendale. */
public final class DocumentVersion {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_USER_LENGTH = 120;
  private static final int MAX_CHECKSUM_LENGTH = 128;

  private final String documentNumber;
  private final int versionNumber;
  private final Instant createdAt;
  private final String createdBy;
  private final String checksum;
  private final DocumentVersionStatus status;
  private final Notes notes;

  private DocumentVersion(
      String documentNumber,
      int versionNumber,
      Instant createdAt,
      String createdBy,
      String checksum,
      DocumentVersionStatus status,
      Notes notes) {
    this.documentNumber =
        validateCode(documentNumber, "Il numero documento versione è obbligatorio.");

    if (versionNumber <= 0) {
      throw new IllegalArgumentException("La versione documento deve essere positiva.");
    }

    if (createdAt == null) {
      throw new IllegalArgumentException("La data versione documento è obbligatoria.");
    }

    this.createdBy = validateUser(createdBy);
    this.checksum = validateChecksum(checksum);

    if (status == null) {
      throw new IllegalArgumentException("Lo stato versione documento è obbligatorio.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note versione documento sono obbligatorie.");
    }

    this.versionNumber = versionNumber;
    this.createdAt = createdAt;
    this.status = status;
    this.notes = notes;
  }

  public static DocumentVersion current(
      String documentNumber,
      int versionNumber,
      Instant createdAt,
      String createdBy,
      String checksum,
      Notes notes) {
    return new DocumentVersion(
        documentNumber,
        versionNumber,
        createdAt,
        createdBy,
        checksum,
        DocumentVersionStatus.CURRENT,
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
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  private static String validateUser(String createdBy) {
    if (createdBy == null) {
      throw new IllegalArgumentException("L'utente versione documento è obbligatorio.");
    }

    String normalizedUser = createdBy.trim();

    if (normalizedUser.isEmpty()) {
      throw new IllegalArgumentException("L'utente versione documento non può essere vuoto.");
    }

    if (normalizedUser.length() > MAX_USER_LENGTH) {
      throw new IllegalArgumentException(
          "L'utente versione documento non può superare " + MAX_USER_LENGTH + " caratteri.");
    }

    return normalizedUser;
  }

  private static String validateChecksum(String checksum) {
    if (checksum == null) {
      throw new IllegalArgumentException("Il checksum documento è obbligatorio.");
    }

    String normalizedChecksum = checksum.trim().toLowerCase();

    if (normalizedChecksum.isEmpty()) {
      throw new IllegalArgumentException("Il checksum documento non può essere vuoto.");
    }

    if (normalizedChecksum.length() > MAX_CHECKSUM_LENGTH) {
      throw new IllegalArgumentException(
          "Il checksum documento non può superare " + MAX_CHECKSUM_LENGTH + " caratteri.");
    }

    return normalizedChecksum;
  }

  public DocumentVersion supersede() {
    if (!DocumentVersionRules.canBeSuperseded(this)) {
      throw new IllegalStateException("La versione documento non può essere superata.");
    }

    return new DocumentVersion(
        documentNumber,
        versionNumber,
        createdAt,
        createdBy,
        checksum,
        DocumentVersionStatus.SUPERSEDED,
        notes);
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public int getVersionNumber() {
    return versionNumber;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public String getChecksum() {
    return checksum;
  }

  public DocumentVersionStatus getStatus() {
    return status;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isCurrent() {
    return status.isCurrent();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DocumentVersion that)) return false;
    return versionNumber == that.versionNumber
        && documentNumber.equals(that.documentNumber)
        && createdAt.equals(that.createdAt)
        && createdBy.equals(that.createdBy)
        && checksum.equals(that.checksum)
        && status == that.status
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        documentNumber, versionNumber, createdAt, createdBy, checksum, status, notes);
  }
}
