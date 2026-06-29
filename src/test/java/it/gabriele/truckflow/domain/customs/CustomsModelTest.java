package it.gabriele.truckflow.domain.customs;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Testa dogana e viaggi internazionali. */
class CustomsModelTest {

  @Test
  void shouldCreateSubmitAndClearCustomsDeclaration() {
    CustomsDeclaration declaration = declaration();
    CustomsDeclaration submitted = declaration.submit();
    CustomsDeclaration cleared = submitted.clear();

    assertEquals("CUS-DEC-001", declaration.getDeclarationCode());
    assertTrue(CustomsRules.hasCustomsCost(declaration));
    assertEquals(CustomsStatus.SUBMITTED, submitted.getStatus());
    assertEquals(CustomsStatus.CLEARED, cleared.getStatus());
  }

  @Test
  void shouldRejectSameOriginAndDestination() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            CustomsDeclaration.draft(
                "cus-dec-001",
                "shp-001",
                "IT",
                "IT",
                List.of(CustomsDocumentType.CMR),
                Money.of("120.00", "EUR"),
                Notes.empty()));
  }

  @Test
  void shouldRegisterBorderWaitingTime() {
    BorderCrossing crossing =
        BorderCrossing.planned(
                "brd-001", "mis-001", "CH", Instant.parse("2026-06-01T08:00:00Z"), Notes.empty())
            .registerActual(Instant.parse("2026-06-01T08:45:00Z"), 45);

    assertTrue(crossing.hasActualCrossing());
    assertTrue(crossing.hasWaitingTime());
    assertEquals(45, crossing.getWaitingMinutes());
  }

  private static CustomsDeclaration declaration() {
    return CustomsDeclaration.draft(
        "cus-dec-001",
        "shp-001",
        "IT",
        "CH",
        List.of(CustomsDocumentType.CMR, CustomsDocumentType.COMMERCIAL_INVOICE),
        Money.of("120.00", "EUR"),
        Notes.empty());
  }
}
