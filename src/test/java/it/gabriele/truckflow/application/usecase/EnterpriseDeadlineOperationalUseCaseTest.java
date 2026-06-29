package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.port.in.EvaluateEnterpriseDeadlinesUseCase;
import it.gabriele.truckflow.application.port.in.EvaluateOperationalReadinessUseCase;
import it.gabriele.truckflow.domain.alerting.AlertStatus;
import it.gabriele.truckflow.domain.deadline.DeadlineOwnerType;
import it.gabriele.truckflow.domain.deadline.DeadlineSeverity;
import it.gabriele.truckflow.domain.deadline.DeadlineType;
import it.gabriele.truckflow.domain.deadline.EnterpriseDeadline;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.infrastructure.memory.alerting.InMemoryAlertEventRepository;
import it.gabriele.truckflow.infrastructure.memory.deadline.InMemoryEnterpriseDeadlineRepository;
import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EnterpriseDeadlineOperationalUseCaseTest {

  @Test
  void shouldGenerateAlertAndBlockOperationsForExpiredCriticalDeadline() {
    InMemoryEnterpriseDeadlineRepository deadlineRepository =
        new InMemoryEnterpriseDeadlineRepository();
    InMemoryAlertEventRepository alertRepository = new InMemoryAlertEventRepository();
    EnterpriseDeadline deadline =
        EnterpriseDeadline.planned(
            "DL-CQC-001",
            DeadlineOwnerType.DRIVER,
            "DRV-001",
            DeadlineType.DRIVER_CQC,
            LocalDate.of(2026, 1, 10),
            LocalDate.of(2025, 12, 10),
            DeadlineSeverity.CRITICAL,
            Notes.of("CQC scaduta"));
    deadlineRepository.save(deadline);

    DefaultEvaluateEnterpriseDeadlinesUseCase deadlineUseCase =
        new DefaultEvaluateEnterpriseDeadlinesUseCase(deadlineRepository, alertRepository);

    EvaluateEnterpriseDeadlinesUseCase.Report report =
        deadlineUseCase.handle(
            new EvaluateEnterpriseDeadlinesUseCase.Command(
                LocalDate.of(2026, 2, 1), Instant.parse("2026-02-01T08:00:00Z")));

    assertEquals(1, report.attentionDeadlines().size());
    assertEquals(1, report.blockingDeadlines().size());
    assertEquals(1, report.generatedAlerts().size());
    assertEquals(AlertStatus.OPEN, report.generatedAlerts().get(0).getStatus());

    DefaultEvaluateOperationalReadinessUseCase readinessUseCase =
        new DefaultEvaluateOperationalReadinessUseCase(deadlineRepository, alertRepository);
    EvaluateOperationalReadinessUseCase.Report readiness =
        readinessUseCase.handle(
            new EvaluateOperationalReadinessUseCase.Command(
                DeadlineOwnerType.DRIVER, "DRV-001", LocalDate.of(2026, 2, 1)));

    assertFalse(readiness.ready());
    assertEquals(1, readiness.blockers().size());
    assertTrue(readiness.warnings().stream().anyMatch(warning -> warning.contains("Alert")));
  }
}
