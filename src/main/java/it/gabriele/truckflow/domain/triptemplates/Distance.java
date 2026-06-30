package it.gabriele.truckflow.domain.triptemplates;

import java.math.BigDecimal;

public record Distance(BigDecimal value, DistanceUnit unit) {

  public Distance {
    value = TripTemplateValidation.requireNonNegative(value, "value");
    unit = TripTemplateValidation.requireNonNull(unit, "unit");
  }

  public static Distance of(BigDecimal value, DistanceUnit unit) {
    return new Distance(value, unit);
  }

  public static Distance km(BigDecimal value) {
    return new Distance(value, DistanceUnit.KM);
  }

  public static Distance mi(BigDecimal value) {
    return new Distance(value, DistanceUnit.MI);
  }

  public static Distance zeroKm() {
    return new Distance(BigDecimal.ZERO, DistanceUnit.KM);
  }
}
