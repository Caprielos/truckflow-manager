package it.gabriele.truckflow.domain.pricing;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Testa PriceBreakdown. */
class PriceBreakdownTest {

  @Test
  void shouldCreatePriceBreakdown() {
    PriceBreakdown breakdown = standardBreakdown();

    assertEquals("QUOTE-001", breakdown.getQuoteNumber());
    assertEquals(6, breakdown.getLineCount());
    assertEquals(Money.of("1350.00", "EUR"), breakdown.calculateTotal());
    assertTrue(breakdown.hasSurcharges());
    assertTrue(breakdown.hasDiscounts());
    assertTrue(breakdown.hasLineType(PricingLineType.BASE_FREIGHT));
    assertTrue(breakdown.hasLineType(PricingLineType.VEHICLE_WEAR_CHARGE));
  }

  @Test
  void shouldNormalizeQuoteNumber() {
    PriceBreakdown breakdown = PriceBreakdown.of("  quote_001  ", baseFreightLine());

    assertEquals("QUOTE_001", breakdown.getQuoteNumber());
  }

  @Test
  void shouldReturnChargeAndDiscountLines() {
    PriceBreakdown breakdown = standardBreakdown();

    assertEquals(5, breakdown.getChargeLines().size());
    assertEquals(1, breakdown.getDiscountLines().size());
  }

  @Test
  void shouldNotAllowInvalidQuoteNumber() {
    assertThrows(IllegalArgumentException.class, () -> PriceBreakdown.of(null, baseFreightLine()));

    assertThrows(
        IllegalArgumentException.class, () -> PriceBreakdown.of("QUOTE 001", baseFreightLine()));
  }

  @Test
  void shouldNotAllowInvalidLineList() {
    assertThrows(
        IllegalArgumentException.class,
        () -> PriceBreakdown.of("QUOTE-001", (List<PricingLine>) null, Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () -> PriceBreakdown.of("QUOTE-001", List.of(), Notes.empty()));

    List<PricingLine> linesWithNull = Arrays.asList(baseFreightLine(), null);

    assertThrows(
        IllegalArgumentException.class,
        () -> PriceBreakdown.of("QUOTE-001", linesWithNull, Notes.empty()));
  }

  @Test
  void shouldNotAllowOnlyDiscountLines() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            PriceBreakdown.of(
                "QUOTE-001",
                PricingLine.discount(
                    "LINE-001", "Sconto", Money.of("50.00", "EUR"), Notes.empty())));
  }

  @Test
  void shouldNotAllowDuplicatedLineCodes() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            PriceBreakdown.of(
                "QUOTE-001", List.of(baseFreightLine(), baseFreightLine()), Notes.empty()));
  }

  @Test
  void shouldNotAllowDiscountGreaterThanCharges() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            PriceBreakdown.of(
                "QUOTE-001",
                baseFreightLine(),
                PricingLine.discount(
                    "LINE-999", "Sconto troppo alto", Money.of("2000.00", "EUR"), Notes.empty())));
  }

  @Test
  void shouldExposeUnmodifiableLines() {
    PriceBreakdown breakdown = standardBreakdown();

    assertThrows(
        UnsupportedOperationException.class, () -> breakdown.getLines().add(baseFreightLine()));
  }

  @Test
  void shouldNotCheckNullLineType() {
    PriceBreakdown breakdown = standardBreakdown();

    assertThrows(IllegalArgumentException.class, () -> breakdown.hasLineType(null));
  }

  @Test
  void shouldDetectNotes() {
    PriceBreakdown breakdown =
        PriceBreakdown.of(
            "QUOTE-001", List.of(baseFreightLine()), Notes.of("Preventivo valido 15 giorni"));

    assertTrue(breakdown.hasNotes());
  }

  @Test
  void shouldFormatSingleLine() {
    assertEquals(
        "QUOTE-001 - lines: 6 - total: 1350.00 EUR", standardBreakdown().formatSingleLine());
  }

  @Test
  void shouldConsiderEquivalentBreakdownsEqual() {
    PriceBreakdown first = standardBreakdown();
    PriceBreakdown second = standardBreakdown();

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }

  private static PriceBreakdown standardBreakdown() {
    RouteCostEstimate estimate = estimate();

    return PriceBreakdown.of(
        "QUOTE-001",
        List.of(
            baseFreightLine(),
            PricingLine.fuelFromEstimate("LINE-002", estimate, Notes.empty()),
            PricingLine.tollsFromEstimate("LINE-003", estimate, Notes.empty()),
            PricingLine.vehicleWearFromEstimate("LINE-004", estimate, Notes.empty()),
            PricingLine.surcharge(
                "LINE-005",
                PricingLineType.ADR_SURCHARGE,
                "Supplemento ADR",
                Money.of("150.00", "EUR"),
                Notes.empty()),
            PricingLine.discount(
                "LINE-006", "Sconto cliente", Money.of("160.00", "EUR"), Notes.empty())),
        Notes.empty());
  }

  private static PricingLine baseFreightLine() {
    return PricingLine.baseFreight(
        "LINE-001", "Trasporto base", Money.of("1000.00", "EUR"), Notes.empty());
  }

  private static RouteCostEstimate estimate() {
    return RouteCostEstimate.of(
        "EST-001",
        CostEstimationSource.VIAMICHELIN,
        Distance.ofKilometers(580),
        Money.of("220.00", "EUR"),
        Money.of("80.00", "EUR"),
        Money.of("60.00", "EUR"),
        Notes.empty());
  }
}
