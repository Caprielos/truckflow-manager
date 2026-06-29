package it.gabriele.truckflow.domain.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Testa il Value Object Weight. */
class WeightTest {

  @Test
  void shouldCreateWeightInKilograms() {
    Weight weight = Weight.ofKilograms(1500);

    assertEquals(1500, weight.getKilograms());
  }

  @Test
  void shouldConvertTonsToKilograms() {
    Weight weight = Weight.ofTons(2);

    assertEquals(2000, weight.getKilograms());
  }

  @Test
  void shouldNotAllowNegativeWeight() {
    assertThrows(IllegalArgumentException.class, () -> Weight.ofKilograms(-1));
  }

  @Test
  void shouldNotAllowInvalidNumber() {
    assertThrows(IllegalArgumentException.class, () -> Weight.ofKilograms(Double.NaN));
    assertThrows(
        IllegalArgumentException.class, () -> Weight.ofKilograms(Double.POSITIVE_INFINITY));
  }

  @Test
  void shouldCompareWeights() {
    Weight heavyWeight = Weight.ofKilograms(2000);
    Weight lightWeight = Weight.ofKilograms(1000);

    assertTrue(heavyWeight.isGreaterThan(lightWeight));
    assertTrue(lightWeight.isLessThanOrEqualTo(heavyWeight));
  }

  @Test
  void shouldConsiderOneTonEqualToOneThousandKilograms() {
    Weight oneTon = Weight.ofTons(1);
    Weight oneThousandKilograms = Weight.ofKilograms(1000);

    assertEquals(oneThousandKilograms, oneTon);
    assertEquals(oneThousandKilograms.hashCode(), oneTon.hashCode());
  }

  @Test
  void shouldNotCompareWithNullWeight() {
    Weight weight = Weight.ofKilograms(1000);

    assertThrows(IllegalArgumentException.class, () -> weight.isGreaterThan(null));
    assertThrows(IllegalArgumentException.class, () -> weight.isLessThanOrEqualTo(null));
  }
}
