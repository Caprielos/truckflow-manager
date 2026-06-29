package it.gabriele.truckflow.domain.pricing;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Distance;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

/** Testa PricingLine. */
class PricingLineTest {

  @Test
  void shouldCreatePricingLine() {
    PricingLine line = baseFreightLine();

    assertEquals("LINE-001", line.getLineCode());
    assertEquals(PricingLineType.BASE_FREIGHT, line.getType());
    assertEquals("Trasporto base", line.getDescription());
    assertEquals(Money.of("1000.00", "EUR"), line.getAmount());
    assertFalse(line.isSurcharge());
    assertFalse(line.isDiscount());
    assertTrue(line.increasesTotal());
  }

  @Test
  void shouldCreateSurchargeLine() {
    PricingLine line =
        PricingLine.surcharge(
            "LINE-002",
            PricingLineType.FUEL_SURCHARGE,
            "Supplemento carburante",
            Money.of("100.00", "EUR"),
            Notes.empty());

    assertTrue(line.isSurcharge());
    assertFalse(line.isDiscount());
    assertTrue(line.increasesTotal());
  }

  @Test
  void shouldCreateVehicleWearChargeFromEstimate() {
    PricingLine line = PricingLine.vehicleWearFromEstimate("LINE-WEAR", estimate(), Notes.empty());

    assertEquals(PricingLineType.VEHICLE_WEAR_CHARGE, line.getType());
    assertEquals(Money.of("60.00", "EUR"), line.getAmount());
    assertTrue(line.isSurcharge());
  }

  @Test
  void shouldCreateFuelAndTollChargesFromEstimate() {
    PricingLine fuel = PricingLine.fuelFromEstimate("LINE-FUEL", estimate(), Notes.empty());

    PricingLine toll = PricingLine.tollsFromEstimate("LINE-TOLL", estimate(), Notes.empty());

    assertEquals(PricingLineType.FUEL_SURCHARGE, fuel.getType());
    assertEquals(Money.of("220.00", "EUR"), fuel.getAmount());

    assertEquals(PricingLineType.TOLL_CHARGE, toll.getType());
    assertEquals(Money.of("80.00", "EUR"), toll.getAmount());
  }

  @Test
  void shouldCreateDiscountLine() {
    PricingLine line =
        PricingLine.discount("LINE-003", "Sconto cliente", Money.of("80.00", "EUR"), Notes.empty());

    assertTrue(line.isDiscount());
    assertTrue(line.decreasesTotal());
  }

  @Test
  void shouldNotCreateSurchargeWithNonSurchargeType() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            PricingLine.surcharge(
                "LINE-002",
                PricingLineType.BASE_FREIGHT,
                "Supplemento non valido",
                Money.of("100.00", "EUR"),
                Notes.empty()));
  }

  @Test
  void shouldNotCreateEstimateLineWithoutEstimate() {
    assertThrows(
        IllegalArgumentException.class,
        () -> PricingLine.fuelFromEstimate("LINE-FUEL", null, Notes.empty()));
  }

  @Test
  void shouldNormalizeLineCodeAndDescription() {
    PricingLine line =
        PricingLine.of(
            "  line_001  ",
            PricingLineType.BASE_FREIGHT,
            "  Trasporto base  ",
            Money.of("1000.00", "EUR"),
            Notes.empty());

    assertEquals("LINE_001", line.getLineCode());
    assertEquals("Trasporto base", line.getDescription());
  }

  @Test
  void shouldRejectInvalidLineCode() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            PricingLine.of(
                null,
                PricingLineType.BASE_FREIGHT,
                "Trasporto base",
                Money.of("1000.00", "EUR"),
                Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            PricingLine.of(
                "LINE 001",
                PricingLineType.BASE_FREIGHT,
                "Trasporto base",
                Money.of("1000.00", "EUR"),
                Notes.empty()));
  }

  @Test
  void shouldRejectNullMandatoryFields() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            PricingLine.of(
                "LINE-001", null, "Trasporto base", Money.of("1000.00", "EUR"), Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            PricingLine.of(
                "LINE-001",
                PricingLineType.BASE_FREIGHT,
                null,
                Money.of("1000.00", "EUR"),
                Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            PricingLine.of(
                "LINE-001", PricingLineType.BASE_FREIGHT, "Trasporto base", null, Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            PricingLine.of(
                "LINE-001",
                PricingLineType.BASE_FREIGHT,
                "Trasporto base",
                Money.of("1000.00", "EUR"),
                null));
  }

  @Test
  void shouldDetectNotes() {
    PricingLine line =
        PricingLine.of(
            "LINE-001",
            PricingLineType.BASE_FREIGHT,
            "Trasporto base",
            Money.of("1000.00", "EUR"),
            Notes.of("Prezzo concordato"));

    assertTrue(line.hasNotes());
  }

  @Test
  void shouldFormatSingleLine() {
    assertEquals("LINE-001 - BASE_FREIGHT - 1000.00 EUR", baseFreightLine().formatSingleLine());
  }

  @Test
  void shouldConsiderEquivalentLinesEqual() {
    PricingLine first = baseFreightLine();
    PricingLine second = baseFreightLine();

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }

  @Test
  void shouldExposeEnumDetails() {
    assertTrue(PricingLineType.FUEL_SURCHARGE.isSurcharge());
    assertTrue(PricingLineType.VEHICLE_WEAR_CHARGE.isSurcharge());
    assertTrue(PricingLineType.DISCOUNT.isDiscount());
    assertTrue(PricingLineType.BASE_FREIGHT.increasesTotal());
    assertTrue(PricingLineType.DISCOUNT.decreasesTotal());
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
