package it.gabriele.truckflow.domain.integration;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import org.junit.jupiter.api.Test;

/** Testa connettori e run di integrazione esterna. */
class IntegrationModelTest {

  @Test
  void shouldConfigureAndActivateConnector() {
    IntegrationConnector connector =
        IntegrationConnector.configured(
                "int-fuel-dkv", ExternalSystemType.FUEL_CARD, "DKV", Notes.empty())
            .activate();

    assertEquals("INT-FUEL-DKV", connector.getConnectorCode());
    assertEquals(ExternalSystemType.FUEL_CARD, connector.getSystemType());
    assertTrue(connector.isActive());
  }

  @Test
  void shouldCompleteRunWithErrorsAndRequireReconciliation() {
    IntegrationRun run =
        IntegrationRun.running(
                "run-001", "int-fuel-dkv", Instant.parse("2026-06-01T08:00:00Z"), Notes.empty())
            .complete(Instant.parse("2026-06-01T08:05:00Z"), 98, 2);

    assertEquals(IntegrationStatus.COMPLETED_WITH_ERRORS, run.getStatus());
    assertTrue(run.hasFailures());
    assertTrue(IntegrationRules.needsReconciliation(run));
  }
}
