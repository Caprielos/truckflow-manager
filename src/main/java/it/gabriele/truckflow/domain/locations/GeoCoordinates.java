package it.gabriele.truckflow.domain.locations;

import java.math.BigDecimal;

public record GeoCoordinates(BigDecimal latitude, BigDecimal longitude) {

  private static final BigDecimal MIN_LATITUDE = new BigDecimal("-90");
  private static final BigDecimal MAX_LATITUDE = new BigDecimal("90");
  private static final BigDecimal MIN_LONGITUDE = new BigDecimal("-180");
  private static final BigDecimal MAX_LONGITUDE = new BigDecimal("180");

  public GeoCoordinates {
    latitude = LocationValidation.requireInRange(latitude, MIN_LATITUDE, MAX_LATITUDE, "latitude");
    longitude =
        LocationValidation.requireInRange(longitude, MIN_LONGITUDE, MAX_LONGITUDE, "longitude");
  }

  public static GeoCoordinates of(BigDecimal latitude, BigDecimal longitude) {
    return new GeoCoordinates(latitude, longitude);
  }
}
