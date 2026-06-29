package it.gabriele.truckflow.domain.location;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Testa GeoCoordinates. */
class GeoCoordinatesTest {

  @Test
  void shouldCreateGeoCoordinates() {
    GeoCoordinates coordinates = GeoCoordinates.of(45.4642, 9.1900);

    assertEquals(45.4642, coordinates.getLatitude());
    assertEquals(9.1900, coordinates.getLongitude());
  }

  @Test
  void shouldAllowBoundaryValues() {
    assertEquals(90, GeoCoordinates.of(90, 180).getLatitude());
    assertEquals(-90, GeoCoordinates.of(-90, -180).getLatitude());
  }

  @Test
  void shouldNotAllowInvalidLatitude() {
    assertThrows(IllegalArgumentException.class, () -> GeoCoordinates.of(91, 9));
    assertThrows(IllegalArgumentException.class, () -> GeoCoordinates.of(-91, 9));
  }

  @Test
  void shouldNotAllowInvalidLongitude() {
    assertThrows(IllegalArgumentException.class, () -> GeoCoordinates.of(45, 181));
    assertThrows(IllegalArgumentException.class, () -> GeoCoordinates.of(45, -181));
  }

  @Test
  void shouldNotAllowInvalidNumbers() {
    assertThrows(IllegalArgumentException.class, () -> GeoCoordinates.of(Double.NaN, 9));
    assertThrows(IllegalArgumentException.class, () -> GeoCoordinates.of(45, Double.NaN));

    assertThrows(
        IllegalArgumentException.class, () -> GeoCoordinates.of(Double.POSITIVE_INFINITY, 9));
    assertThrows(
        IllegalArgumentException.class, () -> GeoCoordinates.of(45, Double.POSITIVE_INFINITY));
  }

  @Test
  void shouldDetectHemispheres() {
    GeoCoordinates milan = GeoCoordinates.of(45.4642, 9.1900);
    GeoCoordinates buenosAires = GeoCoordinates.of(-34.6037, -58.3816);

    assertTrue(milan.isNorthernHemisphere());
    assertTrue(milan.isEasternHemisphere());

    assertTrue(buenosAires.isSouthernHemisphere());
    assertTrue(buenosAires.isWesternHemisphere());
  }

  @Test
  void shouldConsiderEquivalentCoordinatesEqual() {
    GeoCoordinates first = GeoCoordinates.of(45.4642, 9.1900);
    GeoCoordinates second = GeoCoordinates.of(45.4642, 9.1900);

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }
}
