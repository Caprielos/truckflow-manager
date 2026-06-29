package it.gabriele.truckflow.domain.driver;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.Objects;

/** Certificato/abilitazione dell'autista con periodo di validità. */
public final class DriverCertificate {

  private final DriverCertificateType type;
  private final LocalDate issuedAt;
  private final LocalDate expiresAt;
  private final String documentNumber;
  private final Notes notes;

  private DriverCertificate(
      DriverCertificateType type,
      LocalDate issuedAt,
      LocalDate expiresAt,
      String documentNumber,
      Notes notes) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo certificato autista è obbligatorio.");
    }
    if (expiresAt == null) {
      throw new IllegalArgumentException("La scadenza certificato autista è obbligatoria.");
    }
    if (issuedAt != null && issuedAt.isAfter(expiresAt)) {
      throw new IllegalArgumentException(
          "La data rilascio non può essere successiva alla scadenza.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note certificato autista sono obbligatorie.");
    }
    this.type = type;
    this.issuedAt = issuedAt;
    this.expiresAt = expiresAt;
    this.documentNumber = documentNumber == null ? "" : documentNumber.trim().toUpperCase();
    this.notes = notes;
  }

  public static DriverCertificate of(
      DriverCertificateType type,
      LocalDate issuedAt,
      LocalDate expiresAt,
      String documentNumber,
      Notes notes) {
    return new DriverCertificate(type, issuedAt, expiresAt, documentNumber, notes);
  }

  public DriverCertificateType getType() {
    return type;
  }

  public LocalDate getIssuedAt() {
    return issuedAt;
  }

  public LocalDate getExpiresAt() {
    return expiresAt;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isValidOn(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("La data di verifica è obbligatoria.");
    }
    return (issuedAt == null || !date.isBefore(issuedAt)) && !date.isAfter(expiresAt);
  }

  public boolean expiresWithin(LocalDate date, int days) {
    if (date == null) {
      throw new IllegalArgumentException("La data di verifica è obbligatoria.");
    }
    if (days < 0) {
      throw new IllegalArgumentException("I giorni di preavviso non possono essere negativi.");
    }
    return isValidOn(date) && !date.plusDays(days).isBefore(expiresAt);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DriverCertificate that)) return false;
    return type == that.type
        && Objects.equals(issuedAt, that.issuedAt)
        && expiresAt.equals(that.expiresAt)
        && documentNumber.equals(that.documentNumber)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, issuedAt, expiresAt, documentNumber, notes);
  }
}
