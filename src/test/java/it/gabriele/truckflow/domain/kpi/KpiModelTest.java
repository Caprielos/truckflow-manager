package it.gabriele.truckflow.domain.kpi;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

/** Testa KPI enterprise e soglie dashboard. */
class KpiModelTest {

  @Test
  void shouldCreateFleetKpiResult() {
    KpiResult result = vehicleSaturation();

    assertEquals("KPI-001", result.getResultCode());
    assertEquals(KpiMetric.VEHICLE_SATURATION_RATE, result.getMetric());
    assertEquals(KpiCategory.FLEET, result.getCategory());
    assertEquals("%", result.getUnit());
  }

  @Test
  void shouldDetectWarningAndCriticalThresholds() {
    KpiResult lowSaturation =
        KpiResult.of(
            "kpi-002",
            KpiMetric.VEHICLE_SATURATION_RATE,
            "fleet",
            DateRange.of("2026-06-01", "2026-06-30"),
            62.0,
            "%",
            Notes.empty());
    KpiThreshold threshold = KpiThreshold.of(KpiMetric.VEHICLE_SATURATION_RATE, 70.0, 60.0, false);

    assertTrue(KpiRules.isWarning(lowSaturation, threshold));
    assertFalse(KpiRules.isCritical(lowSaturation, threshold));
  }

  private static KpiResult vehicleSaturation() {
    return KpiResult.of(
        "kpi-001",
        KpiMetric.VEHICLE_SATURATION_RATE,
        "fleet",
        DateRange.of("2026-06-01", "2026-06-30"),
        82.5,
        "%",
        Notes.empty());
  }
}
