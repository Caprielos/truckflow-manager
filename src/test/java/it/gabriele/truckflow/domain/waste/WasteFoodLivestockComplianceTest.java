package it.gabriele.truckflow.domain.waste;

import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.foodsafety.FoodProductType;
import it.gabriele.truckflow.domain.foodsafety.FoodSafetyProfile;
import it.gabriele.truckflow.domain.foodsafety.FoodSafetyRules;
import it.gabriele.truckflow.domain.foodsafety.SanitationRecord;
import it.gabriele.truckflow.domain.livestock.AnimalSpecies;
import it.gabriele.truckflow.domain.livestock.LivestockRules;
import it.gabriele.truckflow.domain.livestock.LivestockTripPlan;
import it.gabriele.truckflow.domain.livestock.LivestockVehicleProfile;
import it.gabriele.truckflow.domain.shared.Weight;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class WasteFoodLivestockComplianceTest {

  @Test
  void shouldCheckWasteAuthorizationAndFirCompleteness() {
    EnvironmentalManagerRegistration registration =
        new EnvironmentalManagerRegistration(
            "ALBO-001",
            "COMP-001",
            Set.of(WasteCategory.DANGEROUS, WasteCategory.ADR_WASTE),
            Set.of("TRUCK-WASTE-001"),
            LocalDate.of(2027, 1, 1));
    WasteTransportDocument document =
        new WasteTransportDocument(
            "FIR-001",
            "PROD-001",
            "COMP-001",
            "PLANT-001",
            new WasteEerCode("13.02.05*", "oli minerali esausti", true),
            WasteCategory.ADR_WASTE,
            Weight.ofKilograms(8000),
            true,
            true,
            false,
            true);

    assertTrue(WasteTransportRules.requiresAdrControls(document));
    assertTrue(
        WasteTransportRules.canDepart(
            document, registration, "TRUCK-WASTE-001", LocalDate.of(2026, 6, 1)));
  }

  @Test
  void shouldCheckFoodSanitationAndLivestockWelfare() {
    Instant now = Instant.parse("2026-06-01T08:00:00Z");
    FoodSafetyProfile foodProfile =
        new FoodSafetyProfile(
            "FRIGO-001",
            true,
            true,
            true,
            true,
            List.of(
                new SanitationRecord(
                    "FRIGO-001",
                    now.minusSeconds(3600),
                    now.plusSeconds(86400),
                    "HACCP",
                    true,
                    false)));

    assertTrue(FoodSafetyRules.canLoadFood(foodProfile, FoodProductType.FRESH_CHILLED, now));

    LivestockVehicleProfile livestockProfile =
        new LivestockVehicleProfile(
            "LIV-001",
            Set.of(AnimalSpecies.CATTLE),
            true,
            true,
            true,
            true,
            45.0,
            LocalDate.of(2027, 1, 1));
    LivestockTripPlan tripPlan =
        new LivestockTripPlan(
            "LIV-TRIP-001", AnimalSpecies.CATTLE, 20, 1.5, Duration.ofHours(9), true, true, true);

    assertTrue(LivestockRules.canDepart(livestockProfile, tripPlan, LocalDate.of(2026, 6, 1)));
  }
}
