package it.gabriele.truckflow.domain.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/** Testa il Value Object Percentage. */
class PercentageTest {

  @Test
  void shouldCreatePercentage() {
    Percentage percentage = Percentage.of("25");

    assertEquals(new BigDecimal("25"), percentage.getValue());
  }

  @Test
  void shouldNotAllowNullPercentage() {
    assertThrows(IllegalArgumentException.class, () -> Percentage.of((BigDecimal) null));
  }

  @Test
  void shouldNotAllowNegativePercentage() {
    assertThrows(IllegalArgumentException.class, () -> Percentage.of("-1"));
  }

  @Test
  void shouldNotAllowPercentageGreaterThanOneHundred() {
    assertThrows(IllegalArgumentException.class, () -> Percentage.of("100.01"));
  }

  @Test
  void shouldAllowZeroPercentage() {
    Percentage percentage = Percentage.of("0");

    assertEquals(new BigDecimal("0"), percentage.getValue());
  }

  @Test
  void shouldAllowOneHundredPercentage() {
    Percentage percentage = Percentage.of("100");

    assertEquals(new BigDecimal("100"), percentage.getValue());
  }

  @Test
  void shouldConvertPercentageToMultiplier() {
    Percentage percentage = Percentage.of("25");

    assertEquals(new BigDecimal("0.2500000000"), percentage.toMultiplier());
  }

  @Test
  void shouldComparePercentages() {
    Percentage highPercentage = Percentage.of("30");
    Percentage lowPercentage = Percentage.of("10");

    assertTrue(highPercentage.isGreaterThan(lowPercentage));
    assertTrue(lowPercentage.isLessThanOrEqualTo(highPercentage));
  }

  @Test
  void shouldNotCompareWithNullPercentage() {
    Percentage percentage = Percentage.of("10");

    assertThrows(IllegalArgumentException.class, () -> percentage.isGreaterThan(null));
    assertThrows(IllegalArgumentException.class, () -> percentage.isLessThanOrEqualTo(null));
  }

  @Test
  void shouldConsiderEquivalentPercentagesEqual() {
    Percentage first = Percentage.of("10.0");
    Percentage second = Percentage.of("10.00");

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }
}
