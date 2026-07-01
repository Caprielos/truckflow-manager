package it.gabriele.truckflow.domain.compliance;

import it.gabriele.truckflow.domain.compliance.exceptions.InvalidComplianceRequirementException;
import java.util.Locale;
import java.util.Optional;

public record CountryCode(String value) {

  public CountryCode {
    value = normalize(value);

    if (!value.matches("[A-Z]{2}")) {
      throw new InvalidComplianceRequirementException(
          "Country code must use two uppercase letters.");
    }
  }

  public static CountryCode of(String value) {
    return new CountryCode(value);
  }

  static Optional<CountryCode> optional(String value) {
    String normalized = value == null ? "" : value.trim();
    return normalized.isBlank() ? Optional.empty() : Optional.of(new CountryCode(normalized));
  }

  private static String normalize(String value) {
    if (value == null) {
      throw new InvalidComplianceRequirementException("Country code is required.");
    }

    return value.trim().toUpperCase(Locale.ROOT);
  }
}
