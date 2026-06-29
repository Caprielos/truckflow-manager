package it.gabriele.truckflow.domain.oversized;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.carrierliability.CarrierLiabilityCase;
import it.gabriele.truckflow.domain.carrierliability.CarrierLiabilityRules;
import it.gabriele.truckflow.domain.carrierliability.CarrierResponsibilityStatus;
import it.gabriele.truckflow.domain.carrierliability.LiabilityEventType;
import it.gabriele.truckflow.domain.roadinspection.InspectionFinding;
import it.gabriele.truckflow.domain.roadinspection.InspectionFindingType;
import it.gabriele.truckflow.domain.roadinspection.InspectionOutcome;
import it.gabriele.truckflow.domain.roadinspection.RoadInspection;
import it.gabriele.truckflow.domain.roadinspection.RoadInspectionAuthority;
import it.gabriele.truckflow.domain.roadinspection.RoadInspectionFindingSeverity;
import it.gabriele.truckflow.domain.roadinspection.RoadInspectionRules;
import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.Weight;
import it.gabriele.truckflow.domain.tachograph.DrivingTimeViolation;
import it.gabriele.truckflow.domain.tachograph.DrivingTimeViolationType;
import it.gabriele.truckflow.domain.tachograph.TachographActivity;
import it.gabriele.truckflow.domain.tachograph.TachographActivityType;
import it.gabriele.truckflow.domain.tachograph.TachographRules;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OversizedInspectionLiabilityTachographTest {

  @Test
  void shouldCheckOversizedPermitBeforeDeparture() {
    OversizedPermit permit =
        new OversizedPermit(
            "EXC-001",
            "COMBO-001",
            Dimension.ofMeters(22.0, 3.2, 4.5),
            Weight.ofKilograms(60000),
            LocalDate.of(2026, 1, 1),
            LocalDate.of(2026, 12, 31),
            Set.of("IT", "AT"),
            EscortRequirement.TECHNICAL_ESCORT,
            true,
            true);
    OversizedLoadProfile load =
        new OversizedLoadProfile(
            "LOAD-EXC-001",
            Dimension.ofMeters(20.0, 3.0, 4.2),
            Weight.ofKilograms(52000),
            true,
            true,
            true);

    assertTrue(OversizedTransportRules.requiresEscort(permit));
    assertTrue(OversizedTransportRules.canDepart(permit, load, LocalDate.of(2026, 6, 1), "IT"));
  }

  @Test
  void shouldBlockVehicleForCriticalRoadInspectionFinding() {
    RoadInspection inspection =
        new RoadInspection(
            "CTRL-001",
            RoadInspectionAuthority.POLICE,
            Instant.parse("2026-06-01T08:00:00Z"),
            "TRUCK-001",
            "DRV-001",
            List.of(
                new InspectionFinding(
                    InspectionFindingType.OVERLOAD,
                    RoadInspectionFindingSeverity.CRITICAL,
                    "Sovraccarico asse rilevato",
                    Money.of("500.00", "EUR"),
                    true)),
            InspectionOutcome.VEHICLE_DETENTION);

    assertTrue(RoadInspectionRules.hasCriticalFinding(inspection));
    assertTrue(RoadInspectionRules.blocksVehicle(inspection));
  }

  @Test
  void shouldEvaluateCarrierLiabilityAndTachographViolation() {
    CarrierLiabilityCase liabilityCase =
        new CarrierLiabilityCase(
            "CMR-CLAIM-001",
            "SHIP-001",
            LiabilityEventType.CARGO_DAMAGE,
            CarrierResponsibilityStatus.UNDER_REVIEW,
            Money.of("2500.00", "EUR"),
            true,
            true,
            true,
            false,
            true,
            Notes.of("Danno segnalato con riserva CMR."));

    assertTrue(CarrierLiabilityRules.isReadyForAssessment(liabilityCase));
    assertFalse(CarrierLiabilityRules.requiresPoliceReport(liabilityCase));

    List<TachographActivity> activities =
        List.of(
            new TachographActivity(
                "DRV-001",
                TachographActivityType.DRIVING,
                Instant.parse("2026-06-01T06:00:00Z"),
                Instant.parse("2026-06-01T16:30:00Z")));
    DrivingTimeViolation violation =
        new DrivingTimeViolation(
            "TACHO-VIOL-001",
            "DRV-001",
            DrivingTimeViolationType.DAILY_DRIVING_EXCEEDED,
            Instant.parse("2026-06-01T16:30:00Z"),
            90,
            false);

    assertTrue(TachographRules.exceedsDailyDrivingLimit(activities));
    assertTrue(TachographRules.violationRequiresAlert(violation));
  }
}
