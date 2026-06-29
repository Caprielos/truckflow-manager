package it.gabriele.truckflow.application.usecase.deadlinepolicy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.port.in.deadlinepolicy.CalculateManagedDeadlinePlanUseCase;
import it.gabriele.truckflow.domain.deadlinepolicy.DeadlinePolicySource;
import it.gabriele.truckflow.domain.deadlinepolicy.DeadlineUsageSnapshot;
import it.gabriele.truckflow.domain.deadlinepolicy.ManagedDeadlineElementType;
import it.gabriele.truckflow.domain.regulation.EuropeanCountry;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CalculateManagedDeadlinePlanUseCaseTest {

  @Test
  void shouldCalculateDeadlinePlanFromCountryAndVehicleModel() {
    CalculateManagedDeadlinePlanUseCase useCase = new DefaultCalculateManagedDeadlinePlanUseCase();

    var plan =
        useCase.handle(
            new CalculateManagedDeadlinePlanUseCase.Command(
                EuropeanCountry.ITALY,
                "Iveco",
                "S-Way",
                "TRUCK-001",
                ManagedDeadlineElementType.VEHICLE_ENGINE_OIL,
                DeadlineUsageSnapshot.vehicleUsage(
                    LocalDate.of(2026, 6, 29),
                    LocalDate.of(2025, 6, 1),
                    100000,
                    195000,
                    4000,
                    4700)));

    assertTrue(plan.hasTechnicalRules());
    assertEquals(
        DeadlinePolicySource.TECHNICAL_MANUFACTURER,
        plan.nextEffectiveDeadline().orElseThrow().source());
    assertTrue(plan.hasBlockingDeadline());
  }
}
