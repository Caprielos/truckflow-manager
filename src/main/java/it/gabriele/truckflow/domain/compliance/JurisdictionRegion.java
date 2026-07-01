package it.gabriele.truckflow.domain.compliance;

import it.gabriele.truckflow.domain.compliance.exceptions.InvalidComplianceRequirementException;
import java.util.Locale;
import java.util.Optional;

public record JurisdictionRegion(String value) {

  private static final int MAX_LENGTH = 60;

  public JurisdictionRegion {
    value = normalize(value);

    if (value.isBlank()) {
      throw new InvalidComplianceRequirementException("Jurisdiction region is required.");
    }

    if (value.length() > MAX_LENGTH) {
      throw new InvalidComplianceRequirementException(
          "Jurisdiction region cannot exceed 60 characters.");
    }

    if (!value.matches("[A-Z0-9][A-Z0-9 _-]*")) {
      throw new InvalidComplianceRequirementException(
          "Jurisdiction region can contain only uppercase letters, numbers, spaces, "
              + "dashes and underscores.");
    }
  }

  public static JurisdictionRegion of(String value) {
    return new JurisdictionRegion(value);
  }

  static Optional<JurisdictionRegion> optional(String value) {
    String normalized = value == null ? "" : value.trim();
    return normalized.isBlank()
        ? Optional.empty()
        : Optional.of(new JurisdictionRegion(normalized));
  }

  private static String normalize(String value) {
    if (value == null) {
      throw new InvalidComplianceRequirementException("Jurisdiction region is required.");
    }

    return value.trim().toUpperCase(Locale.ROOT);
  }
}
