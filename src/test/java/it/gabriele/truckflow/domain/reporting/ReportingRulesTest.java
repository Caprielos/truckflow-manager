package it.gabriele.truckflow.domain.reporting;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa ReportingRules.
 */
class ReportingRulesTest {

    @Test
    void shouldCheckLifecycleRules() {
        GeneratedReport draft = draftReport();
        GeneratedReport generated = generatedReport();
        GeneratedReport published = generated.publish();
        GeneratedReport archived = published.archive();
        GeneratedReport failed = draft.fail();

        assertTrue(ReportingRules.canBeGenerated(draft));
        assertTrue(ReportingRules.canBeGenerated(failed));
        assertFalse(ReportingRules.canBeGenerated(generated));

        assertTrue(ReportingRules.canBePublished(generated));
        assertFalse(ReportingRules.canBePublished(draft));

        assertTrue(ReportingRules.canBeArchived(generated));
        assertTrue(ReportingRules.canBeArchived(published));
        assertFalse(ReportingRules.canBeArchived(draft));

        assertTrue(ReportingRules.canBeFailed(draft));
        assertTrue(ReportingRules.canBeFailed(generated));
        assertFalse(ReportingRules.canBeFailed(archived));
    }

    @Test
    void shouldCheckIfReportIsReadyForPublication() {
        assertTrue(ReportingRules.isReadyForPublication(generatedReport()));
        assertFalse(ReportingRules.isReadyForPublication(draftReport()));
    }

    @Test
    void shouldDetectFinancialMetricsAndRestrictedAccess() {
        GeneratedReport report = generatedReport();

        assertTrue(ReportingRules.containsFinancialMetrics(report));
        assertTrue(ReportingRules.requiresRestrictedAccess(report));
    }

    @Test
    void shouldDetectSustainabilityMetrics() {
        GeneratedReport report = GeneratedReport.generated(
                "GEN-SUS",
                sustainabilityDefinition(),
                List.of(
                        ReportMetric.ofDefaultUnit(
                                "MET-CO2",
                                ReportMetricType.TOTAL_CO2_KG,
                                "CO2 totale",
                                "480.7",
                                Notes.empty()
                        )
                ),
                generatedAt(),
                Notes.empty()
        );

        assertTrue(ReportingRules.containsSustainabilityMetrics(report));
    }

    @Test
    void shouldCheckMetricTypeAndCalculateTotal() {
        GeneratedReport report = GeneratedReport.generated(
                "GEN-001",
                operationsDefinition(),
                List.of(
                        ReportMetric.ofDefaultUnit(
                                "MET-001",
                                ReportMetricType.SHIPMENT_COUNT,
                                "Spedizioni nazionali",
                                "25",
                                Notes.empty()
                        ),
                        ReportMetric.ofDefaultUnit(
                                "MET-002",
                                ReportMetricType.SHIPMENT_COUNT,
                                "Spedizioni internazionali",
                                "10",
                                Notes.empty()
                        )
                ),
                generatedAt(),
                Notes.empty()
        );

        assertTrue(ReportingRules.containsMetricType(report, ReportMetricType.SHIPMENT_COUNT));
        assertEquals(new BigDecimal("35"), ReportingRules.calculateMetricTotal(report, ReportMetricType.SHIPMENT_COUNT));
        assertEquals(BigDecimal.ZERO, ReportingRules.calculateMetricTotal(report, ReportMetricType.CLAIM_COUNT));
    }

    @Test
    void shouldNotAllowNullValues() {
        GeneratedReport report = generatedReport();

        assertThrows(IllegalArgumentException.class, () -> ReportingRules.canBeGenerated(null));
        assertThrows(IllegalArgumentException.class, () -> ReportingRules.canBePublished(null));
        assertThrows(IllegalArgumentException.class, () -> ReportingRules.canBeArchived(null));
        assertThrows(IllegalArgumentException.class, () -> ReportingRules.canBeFailed(null));
        assertThrows(IllegalArgumentException.class, () -> ReportingRules.isReadyForPublication(null));
        assertThrows(IllegalArgumentException.class, () -> ReportingRules.containsFinancialMetrics(null));
        assertThrows(IllegalArgumentException.class, () -> ReportingRules.containsSustainabilityMetrics(null));
        assertThrows(IllegalArgumentException.class, () -> ReportingRules.requiresRestrictedAccess(null));
        assertThrows(IllegalArgumentException.class, () -> ReportingRules.containsMetricType(null, ReportMetricType.SHIPMENT_COUNT));
        assertThrows(IllegalArgumentException.class, () -> ReportingRules.containsMetricType(report, null));
        assertThrows(IllegalArgumentException.class, () -> ReportingRules.calculateMetricTotal(null, ReportMetricType.SHIPMENT_COUNT));
        assertThrows(IllegalArgumentException.class, () -> ReportingRules.calculateMetricTotal(report, null));
    }

    private static GeneratedReport draftReport() {
        return GeneratedReport.draft(
                "GEN-001",
                operationsDefinition(),
                Notes.empty()
        );
    }

    private static GeneratedReport generatedReport() {
        return GeneratedReport.generated(
                "GEN-001",
                financialDefinition(),
                List.of(
                        ReportMetric.ofDefaultUnit(
                                "MET-001",
                                ReportMetricType.TOTAL_REVENUE,
                                "Ricavi totali",
                                "1250.00",
                                Notes.empty()
                        ),
                        ReportMetric.ofDefaultUnit(
                                "MET-002",
                                ReportMetricType.TOTAL_COST,
                                "Costi totali",
                                "800.00",
                                Notes.empty()
                        )
                ),
                generatedAt(),
                Notes.empty()
        );
    }

    private static ReportDefinition operationsDefinition() {
        return ReportDefinition.of(
                "RPT-OPS",
                ReportType.OPERATIONS,
                ReportFormat.PDF,
                period(),
                "USR-001",
                Notes.empty()
        );
    }

    private static ReportDefinition financialDefinition() {
        return ReportDefinition.of(
                "RPT-FIN",
                ReportType.FINANCIAL,
                ReportFormat.XLSX,
                period(),
                "USR-001",
                Notes.empty()
        );
    }

    private static ReportDefinition sustainabilityDefinition() {
        return ReportDefinition.of(
                "RPT-SUS",
                ReportType.SUSTAINABILITY,
                ReportFormat.PDF,
                period(),
                "USR-001",
                Notes.empty()
        );
    }

    private static DateRange period() {
        return DateRange.of(
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 7, 31)
        );
    }

    private static Instant generatedAt() {
        return Instant.parse("2026-08-01T08:00:00Z");
    }
}
