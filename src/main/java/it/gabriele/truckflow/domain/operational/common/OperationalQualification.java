package it.gabriele.truckflow.domain.operational.common;

import it.gabriele.truckflow.domain.qualifications.Qualification;
import it.gabriele.truckflow.domain.shared.exceptions.DomainValidationException;

public record OperationalQualification(
    Qualification qualification,
    String referenceNumber,
    String issuingCountry,
    String level,
    String notes) {

  public OperationalQualification {
    qualification = requireNonNull(qualification, "qualification");
    referenceNumber = normalize(referenceNumber);
    issuingCountry = normalize(issuingCountry).toUpperCase();
    level = normalize(level);
    notes = normalize(notes);
  }

  public static OperationalQualification of(Qualification qualification) {
    return new OperationalQualification(qualification, "", "", "", "");
  }

  private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new DomainValidationException(fieldName + " is required.");
    }

    return value;
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
