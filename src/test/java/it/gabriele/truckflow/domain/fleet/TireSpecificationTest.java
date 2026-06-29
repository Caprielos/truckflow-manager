package it.gabriele.truckflow.domain.fleet;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Testa TireSpecification. */
class TireSpecificationTest {

  @Test
  void shouldCreateTireSpecification() {
    TireSpecification tire = TireSpecification.of("Michelin", "X Multi", "315/70 R22.5", 154, "L");

    assertEquals("Michelin", tire.getBrand());
    assertEquals("X Multi", tire.getModel());
    assertEquals("315/70 R22.5", tire.getSize());
    assertEquals(154, tire.getLoadIndex());
    assertEquals("L", tire.getSpeedRating());
  }

  @Test
  void shouldNormalizeSpeedRating() {
    TireSpecification tire =
        TireSpecification.of("  Michelin  ", "  X Multi  ", "  315/70 R22.5  ", 154, "  l  ");

    assertEquals("Michelin", tire.getBrand());
    assertEquals("X Multi", tire.getModel());
    assertEquals("315/70 R22.5", tire.getSize());
    assertEquals("L", tire.getSpeedRating());
  }

  @Test
  void shouldNotAllowInvalidTextValues() {
    assertThrows(
        IllegalArgumentException.class,
        () -> TireSpecification.of(null, "X Multi", "315/70 R22.5", 154, "L"));

    assertThrows(
        IllegalArgumentException.class,
        () -> TireSpecification.of("Michelin", "   ", "315/70 R22.5", 154, "L"));

    assertThrows(
        IllegalArgumentException.class,
        () -> TireSpecification.of("Michelin", "X Multi", null, 154, "L"));
  }

  @Test
  void shouldNotAllowInvalidLoadIndex() {
    assertThrows(
        IllegalArgumentException.class,
        () -> TireSpecification.of("Michelin", "X Multi", "315/70 R22.5", 0, "L"));
  }

  @Test
  void shouldNotAllowInvalidSpeedRating() {
    assertThrows(
        IllegalArgumentException.class,
        () -> TireSpecification.of("Michelin", "X Multi", "315/70 R22.5", 154, null));

    assertThrows(
        IllegalArgumentException.class,
        () -> TireSpecification.of("Michelin", "X Multi", "315/70 R22.5", 154, "   "));

    assertThrows(
        IllegalArgumentException.class,
        () -> TireSpecification.of("Michelin", "X Multi", "315/70 R22.5", 154, "L@"));
  }

  @Test
  void shouldFormatSingleLine() {
    TireSpecification tire = TireSpecification.of("Michelin", "X Multi", "315/70 R22.5", 154, "L");

    assertEquals("Michelin X Multi - 315/70 R22.5 - 154L", tire.formatSingleLine());
  }

  @Test
  void shouldConsiderEquivalentTiresEqual() {
    TireSpecification first = TireSpecification.of("Michelin", "X Multi", "315/70 R22.5", 154, "L");

    TireSpecification second =
        TireSpecification.of("Michelin", "X Multi", "315/70 R22.5", 154, "L");

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }
}
