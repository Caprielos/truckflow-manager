package it.gabriele.truckflow.domain.company;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.Objects;

public final class CompanyLicense {

  private final CompanyLicenseType type;
  private final LocalDate expiresAt;
  private final Notes notes;

  private CompanyLicense(CompanyLicenseType type, LocalDate expiresAt, Notes notes) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo licenza azienda è obbligatorio.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note licenza sono obbligatorie.");
    }
    this.type = type;
    this.expiresAt = expiresAt;
    this.notes = notes;
  }

  public static CompanyLicense of(CompanyLicenseType type, LocalDate expiresAt, Notes notes) {
    return new CompanyLicense(type, expiresAt, notes);
  }

  public CompanyLicenseType getType() {
    return type;
  }

  public LocalDate getExpiresAt() {
    return expiresAt;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isValidOn(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("La data di verifica è obbligatoria.");
    }
    return expiresAt == null || !date.isAfter(expiresAt);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CompanyLicense that)) return false;
    return type == that.type
        && Objects.equals(expiresAt, that.expiresAt)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, expiresAt, notes);
  }
}
