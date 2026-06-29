package it.gabriele.truckflow.domain.dispatch;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class DispatchPlanningTest {

  @Test
  void shouldChooseReadyParkedCombinationWithBestMargin() {
    DispatchAssignmentCandidate ready =
        DispatchAssignmentCandidate.of(
            "CAND-001",
            "MIS-001",
            "DRV-001",
            "TRACTOR-001",
            "SEMI-001",
            "PARKED-A12",
            Money.of("1600.00", "EUR"),
            Money.of("1050.00", "EUR"),
            List.of(
                DispatchCheckResult.ready(DispatchCheckType.DRIVER_TIME, "Ore guida disponibili"),
                DispatchCheckResult.ready(
                    DispatchCheckType.PARKING_READY, "Trattore e semirimorchio agganciati"),
                DispatchCheckResult.ready(DispatchCheckType.DOCUMENTS, "Documenti pronti"),
                DispatchCheckResult.ready(DispatchCheckType.COST_MARGIN, "Margine positivo")),
            Notes.empty());

    DispatchAssignmentCandidate blocked =
        DispatchAssignmentCandidate.of(
            "CAND-002",
            "MIS-001",
            "DRV-002",
            "TRUCK-002",
            "",
            "",
            Money.of("1700.00", "EUR"),
            Money.of("900.00", "EUR"),
            List.of(
                DispatchCheckResult.blocked(
                    DispatchCheckType.DRIVER_LICENSE, "Patente non compatibile"),
                DispatchCheckResult.ready(DispatchCheckType.COST_MARGIN, "Margine positivo")),
            Notes.empty());

    DispatchPlan plan =
        DispatchPlan.of(
            "PLAN-001", LocalDate.of(2026, 6, 29), List.of(ready, blocked), Notes.empty());

    assertTrue(ready.isParkedCombinationReady());
    assertEquals(new BigDecimal("550.00"), ready.calculateGrossMarginAmount());
    assertTrue(DispatchRules.canAssign(ready));
    assertFalse(DispatchRules.canAssign(blocked));
    assertEquals("CAND-001", plan.chooseBestAssignableByMargin().orElseThrow().getCandidateCode());
  }
}
