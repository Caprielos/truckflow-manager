package it.gabriele.truckflow.infrastructure.client.deadline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.port.out.deadline.DeadlineGatewayResult;
import it.gabriele.truckflow.application.port.out.deadline.DeadlineGatewayStatus;
import it.gabriele.truckflow.application.port.out.deadline.DeadlineGatewaySubject;
import it.gabriele.truckflow.deadlineservice.application.DeadlineServiceFacade;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InProcessDeadlineGatewayTest {

  @Test
  void shouldEvaluateSubjectThroughGatewayWithoutExposingMainDomainClasses() {
    InProcessDeadlineGateway gateway =
        new InProcessDeadlineGateway(DeadlineServiceFacade.usingDefaultRulePack());

    DeadlineGatewaySubject subject =
        DeadlineGatewaySubject.of(
            "DEFAULT",
            "VEHICLE",
            "VEH-001",
            "AB123CD",
            "IT",
            "IVECO",
            "S-WAY",
            Set.of("VEHICLE_ENGINE_OIL"),
            Map.of("currentKm", "180000"));

    DeadlineGatewayResult result = gateway.evaluate(subject, LocalDate.of(2026, 6, 30));

    assertEquals(DeadlineGatewayStatus.CONFIGURATION_MISSING, result.overallStatus());
    assertTrue(result.hasConfigurationMissing());
    assertEquals(1, result.evaluations().size());
    assertEquals("VEHICLE_ENGINE_OIL", result.evaluations().getFirst().elementCode());
  }

  @Test
  void shouldEvaluateBatchPreservingOrder() {
    InProcessDeadlineGateway gateway =
        new InProcessDeadlineGateway(DeadlineServiceFacade.usingDefaultRulePack());

    DeadlineGatewaySubject vehicle =
        DeadlineGatewaySubject.of(
            "DEFAULT",
            "VEHICLE",
            "VEH-001",
            "AB123CD",
            "IT",
            "IVECO",
            "S-WAY",
            Set.of("VEHICLE_ENGINE_OIL"),
            Map.of());
    DeadlineGatewaySubject driver =
        DeadlineGatewaySubject.of(
            "DEFAULT",
            "DRIVER",
            "DRV-001",
            "Mario Rossi",
            "IT",
            "",
            "",
            Set.of("DRIVER_LICENSE"),
            Map.of());

    List<DeadlineGatewayResult> results =
        gateway.evaluateBatch(List.of(vehicle, driver), LocalDate.of(2026, 6, 30));

    assertEquals(2, results.size());
    assertEquals("VEHICLE", results.get(0).subject().objectRef().objectType());
    assertEquals("DRIVER", results.get(1).subject().objectRef().objectType());
  }

  @Test
  void shouldRejectUnknownManagedElement() {
    InProcessDeadlineGateway gateway =
        new InProcessDeadlineGateway(DeadlineServiceFacade.usingDefaultRulePack());

    DeadlineGatewaySubject subject =
        DeadlineGatewaySubject.of(
            "DEFAULT",
            "VEHICLE",
            "VEH-001",
            "AB123CD",
            "IT",
            "IVECO",
            "S-WAY",
            Set.of("UNKNOWN_ELEMENT"),
            Map.of());

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> gateway.evaluate(subject, LocalDate.of(2026, 6, 30)));

    assertTrue(exception.getMessage().contains("Elemento gestito non riconosciuto"));
  }
}
