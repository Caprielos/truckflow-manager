package it.gabriele.truckflow.domain.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Testa il Value Object Dimension. */
class DimensionTest {

  @Test
  void shouldCreateDimensionInMeters() {
    Dimension dimension = Dimension.ofMeters(2.5, 1.2, 1.8);

    assertEquals(2.5, dimension.getLengthMeters());
    assertEquals(1.2, dimension.getWidthMeters());
    assertEquals(1.8, dimension.getHeightMeters());
  }

  @Test
  void shouldConvertCentimetersToMeters() {
    Dimension dimension = Dimension.ofCentimeters(250, 120, 180);

    assertEquals(2.5, dimension.getLengthMeters());
    assertEquals(1.2, dimension.getWidthMeters());
    assertEquals(1.8, dimension.getHeightMeters());
  }

  @Test
  void shouldNotAllowZeroOrNegativeValues() {
    assertThrows(IllegalArgumentException.class, () -> Dimension.ofMeters(0, 1, 1));
    assertThrows(IllegalArgumentException.class, () -> Dimension.ofMeters(1, 0, 1));
    assertThrows(IllegalArgumentException.class, () -> Dimension.ofMeters(1, 1, 0));

    assertThrows(IllegalArgumentException.class, () -> Dimension.ofMeters(-1, 1, 1));
    assertThrows(IllegalArgumentException.class, () -> Dimension.ofMeters(1, -1, 1));
    assertThrows(IllegalArgumentException.class, () -> Dimension.ofMeters(1, 1, -1));
  }

  @Test
  void shouldNotAllowInvalidNumbers() {
    assertThrows(IllegalArgumentException.class, () -> Dimension.ofMeters(Double.NaN, 1, 1));
    assertThrows(IllegalArgumentException.class, () -> Dimension.ofMeters(1, Double.NaN, 1));
    assertThrows(IllegalArgumentException.class, () -> Dimension.ofMeters(1, 1, Double.NaN));

    assertThrows(
        IllegalArgumentException.class, () -> Dimension.ofMeters(Double.POSITIVE_INFINITY, 1, 1));
    assertThrows(
        IllegalArgumentException.class, () -> Dimension.ofMeters(1, Double.POSITIVE_INFINITY, 1));
    assertThrows(
        IllegalArgumentException.class, () -> Dimension.ofMeters(1, 1, Double.POSITIVE_INFINITY));
  }

  @Test
  void shouldCalculateVolume() {
    Dimension dimension = Dimension.ofMeters(2, 3, 4);

    assertEquals(Volume.ofCubicMeters(24), dimension.calculateVolume());
  }

  @Test
  void shouldFitInsideLargerDimension() {
    Dimension cargoDimension = Dimension.ofMeters(2, 1, 1);
    Dimension cargoSpaceDimension = Dimension.ofMeters(3, 2, 2);

    assertTrue(cargoDimension.fitsInside(cargoSpaceDimension));
  }

  @Test
  void shouldNotFitInsideSmallerDimension() {
    Dimension cargoDimension = Dimension.ofMeters(4, 1, 1);
    Dimension cargoSpaceDimension = Dimension.ofMeters(3, 2, 2);

    assertFalse(cargoDimension.fitsInside(cargoSpaceDimension));
  }

  @Test
  void shouldNotCheckFitWithNullDimension() {
    Dimension dimension = Dimension.ofMeters(1, 1, 1);

    assertThrows(IllegalArgumentException.class, () -> dimension.fitsInside(null));
  }

  @Test
  void shouldConsiderEquivalentDimensionsEqual() {
    Dimension meters = Dimension.ofMeters(2.5, 1.2, 1.8);
    Dimension centimeters = Dimension.ofCentimeters(250, 120, 180);

    assertEquals(meters, centimeters);
    assertEquals(meters.hashCode(), centimeters.hashCode());
  }
}
