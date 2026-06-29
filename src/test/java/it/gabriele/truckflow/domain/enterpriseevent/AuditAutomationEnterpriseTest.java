package it.gabriele.truckflow.domain.enterpriseevent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AuditAutomationEnterpriseTest {

  @Test
  void shouldEscalateCriticalDomainEventsAndRequireReason() {
    DomainEventEnvelope event =
        new DomainEventEnvelope(
            "evt-001",
            DomainEventType.SLA_VIOLATED,
            "MISSION",
            "mission-001",
            LocalDateTime.of(2026, 6, 29, 18, 30),
            "system",
            "delivery late beyond contracted SLA",
            Map.of("delayMinutes", "95"));

    assertEquals(AuditDecision.AUDIT_AND_ESCALATE, AuditAutomationRules.decide(event));
    assertTrue(AuditAutomationRules.requiresReason(event));
    assertTrue(event.hasReason());
  }
}
