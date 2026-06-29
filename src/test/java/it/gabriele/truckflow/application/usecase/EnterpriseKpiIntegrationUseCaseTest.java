package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.port.in.EvaluateKpiThresholdUseCase;
import it.gabriele.truckflow.application.port.in.RegisterIntegrationRunUseCase;
import it.gabriele.truckflow.domain.integration.ExternalSystemType;
import it.gabriele.truckflow.domain.integration.IntegrationConnector;
import it.gabriele.truckflow.domain.integration.IntegrationRun;
import it.gabriele.truckflow.domain.kpi.KpiMetric;
import it.gabriele.truckflow.domain.kpi.KpiResult;
import it.gabriele.truckflow.domain.kpi.KpiThreshold;
import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.infrastructure.memory.InMemoryAlertEventRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryIntegrationConnectorRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryIntegrationRunRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryKpiResultRepository;
import it.gabriele.truckflow.infrastructure.memory.InMemoryKpiThresholdRepository;
import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EnterpriseKpiIntegrationUseCaseTest {

  @Test
  void shouldCreateAlertWhenKpiIsCritical() {
    InMemoryKpiResultRepository resultRepository = new InMemoryKpiResultRepository();
    InMemoryKpiThresholdRepository thresholdRepository = new InMemoryKpiThresholdRepository();
    InMemoryAlertEventRepository alertRepository = new InMemoryAlertEventRepository();
    KpiThreshold threshold = KpiThreshold.of(KpiMetric.AVERAGE_DELAY_MINUTES, 30.0, 60.0, true);
    thresholdRepository.save(threshold);
    KpiResult result =
        KpiResult.of(
            "KPI-DELAY-001",
            KpiMetric.AVERAGE_DELAY_MINUTES,
            "CUST-001",
            DateRange.of(LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 30)),
            75.0,
            "minutes",
            Notes.of("Ritardo medio mensile"));

    EvaluateKpiThresholdUseCase.Result evaluation =
        new DefaultEvaluateKpiThresholdUseCase(
                resultRepository, thresholdRepository, alertRepository)
            .handle(
                new EvaluateKpiThresholdUseCase.Command(
                    result,
                    KpiMetric.AVERAGE_DELAY_MINUTES,
                    Instant.parse("2026-06-30T20:00:00Z")));

    assertTrue(evaluation.critical());
    assertTrue(evaluation.alert().isPresent());
    assertEquals(1, alertRepository.findAll().size());
  }

  @Test
  void shouldCreateAlertWhenIntegrationRunNeedsReconciliation() {
    InMemoryIntegrationConnectorRepository connectorRepository =
        new InMemoryIntegrationConnectorRepository();
    InMemoryIntegrationRunRepository runRepository = new InMemoryIntegrationRunRepository();
    InMemoryAlertEventRepository alertRepository = new InMemoryAlertEventRepository();
    IntegrationConnector connector =
        IntegrationConnector.configured(
            "WEBFLEET", ExternalSystemType.TELEMATICS, "Webfleet", Notes.of("Telematica"));
    connectorRepository.save(connector.activate());
    IntegrationRun run =
        IntegrationRun.running(
                "RUN-001", "WEBFLEET", Instant.parse("2026-06-29T08:00:00Z"), Notes.empty())
            .complete(Instant.parse("2026-06-29T08:05:00Z"), 90, 3);

    RegisterIntegrationRunUseCase.Result result =
        new DefaultRegisterIntegrationRunUseCase(
                connectorRepository, runRepository, alertRepository)
            .handle(
                new RegisterIntegrationRunUseCase.Command(
                    run, Instant.parse("2026-06-29T08:06:00Z")));

    assertTrue(result.alert().isPresent());
    assertEquals(1, runRepository.findAll().size());
    assertEquals(1, alertRepository.findAll().size());
  }
}
