package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.port.in.sla.DetectSlaViolationUseCase;
import it.gabriele.truckflow.application.usecase.sla.DefaultDetectSlaViolationUseCase;
import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.sla.PenaltyRule;
import it.gabriele.truckflow.domain.sla.ServiceLevelAgreement;
import it.gabriele.truckflow.domain.sla.SlaMetric;
import it.gabriele.truckflow.domain.sla.SlaRule;
import it.gabriele.truckflow.infrastructure.memory.alerting.InMemoryAlertEventRepository;
import it.gabriele.truckflow.infrastructure.memory.sla.InMemoryServiceLevelAgreementRepository;
import it.gabriele.truckflow.infrastructure.memory.sla.InMemorySlaViolationRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class EnterpriseSlaOperationalUseCaseTest {

  @Test
  void shouldDetectLateDeliveryAndCreatePenaltyAlert() {
    InMemoryServiceLevelAgreementRepository agreementRepository =
        new InMemoryServiceLevelAgreementRepository();
    InMemorySlaViolationRepository violationRepository = new InMemorySlaViolationRepository();
    InMemoryAlertEventRepository alertRepository = new InMemoryAlertEventRepository();
    ServiceLevelAgreement agreement =
        ServiceLevelAgreement.draft(
                "SLA-CUST-001",
                "CUST-001",
                DateRange.of(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31)),
                List.of(SlaRule.of("DELIVERY-30", SlaMetric.DELIVERY_ON_TIME, 30, 98.0, true)),
                List.of(
                    PenaltyRule.fixed(SlaMetric.DELIVERY_ON_TIME, Money.of("150.00", "EUR"), true)),
                Notes.of("SLA cliente enterprise"))
            .activate();
    agreementRepository.save(agreement);

    DefaultDetectSlaViolationUseCase useCase =
        new DefaultDetectSlaViolationUseCase(
            agreementRepository, violationRepository, alertRepository);

    DetectSlaViolationUseCase.Result result =
        useCase.handle(
            new DetectSlaViolationUseCase.Command(
                "SLA-VIOL-001",
                "SLA-CUST-001",
                SlaMetric.DELIVERY_ON_TIME,
                "MISSION-001",
                Instant.parse("2026-06-29T10:00:00Z"),
                Instant.parse("2026-06-29T11:00:00Z"),
                Instant.parse("2026-06-29T11:05:00Z")));

    assertTrue(result.violated());
    assertTrue(result.violation().orElseThrow().hasPenalty());
    assertTrue(result.alert().isPresent());
    assertEquals(1, violationRepository.findAll().size());
    assertEquals(1, alertRepository.findAll().size());
  }
}
