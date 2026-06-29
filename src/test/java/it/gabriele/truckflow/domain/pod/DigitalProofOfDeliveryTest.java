package it.gabriele.truckflow.domain.pod;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class DigitalProofOfDeliveryTest {

  @Test
  void shouldEvaluateStrongPodAndClaimReview() {
    DigitalProofOfDelivery pod =
        new DigitalProofOfDelivery(
            "pod-001",
            "shipment-001",
            "mission-001",
            "Mario Rossi",
            LocalDateTime.of(2026, 6, 29, 18, 0),
            45.4642,
            9.19,
            PodStatus.VALIDATED,
            List.of(
                new PodEvidence(
                    "ev-sign",
                    PodEvidenceType.RECIPIENT_SIGNATURE,
                    "signature-hash",
                    LocalDateTime.of(2026, 6, 29, 18, 0),
                    "drv-001"),
                new PodEvidence(
                    "ev-time",
                    PodEvidenceType.TIMESTAMP,
                    "2026-06-29T18:00:00",
                    LocalDateTime.of(2026, 6, 29, 18, 0),
                    "drv-001")),
            false);

    assertTrue(PodRules.isLegallyStrong(pod));
    assertFalse(PodRules.requiresClaimReview(pod));
    assertTrue(PodRules.canArchive(pod));
  }
}
