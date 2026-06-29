package it.gabriele.truckflow.domain.sla;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Testa SLA, regole e penali. */
class SlaModelTest {

  @Test
  void shouldCreateAndActivateServiceLevelAgreement() {
    ServiceLevelAgreement agreement = agreement().activate();

    assertEquals("SLA-001", agreement.getAgreementCode());
    assertTrue(agreement.isActive());
    assertTrue(agreement.hasRuleFor(SlaMetric.DELIVERY_ON_TIME));
    assertTrue(agreement.hasPenaltyFor(SlaMetric.DELIVERY_ON_TIME));
  }

  @Test
  void shouldDetectLateEvent() {
    assertTrue(
        SlaRules.isLate(
            Instant.parse("2026-06-01T10:00:00Z"), Instant.parse("2026-06-01T10:45:00Z"), 30));
  }

  @Test
  void shouldCreateAndWaiveViolationWithPenalty() {
    SlaViolation violation =
        SlaViolation.detected(
            "vio-001",
            "sla-001",
            SlaMetric.DELIVERY_ON_TIME,
            "mis-001",
            Instant.parse("2026-06-01T10:45:00Z"),
            Money.of("150.00", "EUR"),
            Notes.of("Consegna oltre finestra SLA"));

    assertTrue(violation.hasPenalty());
    assertTrue(SlaRules.canBeWaived(violation));
    assertTrue(violation.waive(Notes.of("Deroga approvata dal cliente")).isWaived());
  }

  private static ServiceLevelAgreement agreement() {
    return ServiceLevelAgreement.draft(
        "sla-001",
        "cus-001",
        DateRange.of("2026-01-01", "2026-12-31"),
        List.of(SlaRule.of("sla-rule-001", SlaMetric.DELIVERY_ON_TIME, 30, 98.5, true)),
        List.of(PenaltyRule.fixed(SlaMetric.DELIVERY_ON_TIME, Money.of("150.00", "EUR"), true)),
        Notes.empty());
  }
}
