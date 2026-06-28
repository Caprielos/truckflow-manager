package it.gabriele.truckflow.domain.reporting;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa GeneratedReport.
 */
class GeneratedReportTest {

    @Test
    void shouldCreateDraftReport() {
        GeneratedReport report = draftReport();

        assertEquals("GEN-001", report.getReportNumber());
        assertEquals(definition(), report.getDefinition());
        assertEquals(0, report.getMetricCount());
        assertEquals(ReportStatus.DRAFT, report.getStatus());
        assertNull(report.getGeneratedAt());
        assertTrue(report.isDraft());
        assertFalse(report.isReadable());
    }

    @Test
    void shouldCreateGeneratedReport() {
        GeneratedReport report = generatedReport();

        assertEquals("GEN-001", report.getReportNumber());
        assertEquals(2, report.getMetricCount());
        assertEquals(ReportStatus.GENERATED, report.getStatus());
        assertEquals(generatedAt(), report.getGeneratedAt());
        assertTrue(report.isGenerated());
        assertTrue(report.isReadable());
        assertTrue(report.hasGeneratedAt());
    }

    @Test
    void shouldNormalizeReportNumber() {
        GeneratedReport report = GeneratedReport.draft(
                "  gen_001  ",
                definition(),
                Notes.empty()
        );

        assertEquals("GEN_001", report.getReportNumber());
    }

    @Test
    void shouldRejectInvalidReportNumber() {
        assertThrows(IllegalArgumentException.class, () -> GeneratedReport.draft(
                null,
                definition(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> GeneratedReport.draft(
                "GEN 001",
                definition(),
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> GeneratedReport.draft(
                "GEN-001",
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> GeneratedReport.draft(
                "GEN-001",
                definition(),
                null
        ));

        assertThrows(IllegalArgumentException.class, () -> GeneratedReport.generated(
                "GEN-001",
                definition(),
                null,
                generatedAt(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> GeneratedReport.generated(
                "GEN-001",
                definition(),
                metrics(),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectInvalidMetricList() {
        assertThrows(IllegalArgumentException.class, () -> GeneratedReport.generated(
                "GEN-001",
                definition(),
                List.of(),
                generatedAt(),
                Notes.empty()
        ));

        List<ReportMetric> metricsWithNull = Arrays.asList(revenueMetric(), null);

        assertThrows(IllegalArgumentException.class, () -> GeneratedReport.generated(
                "GEN-001",
                definition(),
                metricsWithNull,
                generatedAt(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> GeneratedReport.generated(
                "GEN-001",
                definition(),
                List.of(revenueMetric(), revenueMetric()),
                generatedAt(),
                Notes.empty()
        ));
    }

    @Test
    void shouldMoveThroughLifecycle() {
        GeneratedReport generated = draftReport().generate(metrics(), generatedAt());
        GeneratedReport published = generated.publish();
        GeneratedReport archived = published.archive();

        assertTrue(generated.isGenerated());
        assertTrue(published.isPublished());
        assertTrue(archived.isArchived());
        assertTrue(archived.isTerminal());
    }

    @Test
    void shouldFailDraftOrGeneratedReport() {
        assertTrue(draftReport().fail().isFailed());
        assertTrue(generatedReport().fail().isFailed());
    }

    @Test
    void shouldNotAllowInvalidLifecycleTransitions() {
        GeneratedReport draft = draftReport();

        assertThrows(IllegalStateException.class, draft::publish);
        assertThrows(IllegalStateException.class, draft::archive);

        GeneratedReport generated = generatedReport();

        assertThrows(IllegalStateException.class, () -> generated.generate(metrics(), generatedAt()));

        GeneratedReport published = generated.publish();

        assertThrows(IllegalStateException.class, published::publish);

        GeneratedReport archived = published.archive();

        assertThrows(IllegalStateException.class, archived::fail);
        assertThrows(IllegalStateException.class, archived::publish);
    }

    @Test
    void shouldExposeUnmodifiableMetrics() {
        GeneratedReport report = generatedReport();

        assertThrows(UnsupportedOperationException.class, () -> report.getMetrics().add(revenueMetric()));
    }

    @Test
    void shouldFindMetricsByType() {
        GeneratedReport report = generatedReport();

        assertTrue(report.hasMetricType(ReportMetricType.TOTAL_REVENUE));
        assertEquals(1, report.getMetricsByType(ReportMetricType.TOTAL_REVENUE).size());
        assertFalse(report.hasMetricType(ReportMetricType.CLAIM_COUNT));

        assertThrows(IllegalArgumentException.class, () -> report.hasMetricType(null));
        assertThrows(IllegalArgumentException.class, () -> report.getMetricsByType(null));
    }

    @Test
    void shouldDetectNotes() {
        GeneratedReport report = GeneratedReport.draft(
                "GEN-001",
                definition(),
                Notes.of("Report da generare")
        );

        assertTrue(report.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        assertEquals(
                "GEN-001 - OPERATIONS - GENERATED - metrics: 2",
                generatedReport().formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentReportsEqual() {
        GeneratedReport first = generatedReport();
        GeneratedReport second = generatedReport();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldExposeEnumDetails() {
        assertFalse(ReportStatus.DRAFT.isTerminal());
        assertTrue(ReportStatus.GENERATED.isReadable());
        assertTrue(ReportStatus.ARCHIVED.isTerminal());
        assertTrue(ReportStatus.FAILED.isTerminal());
    }

    private static GeneratedReport draftReport() {
        return GeneratedReport.draft(
                "GEN-001",
                definition(),
                Notes.empty()
        );
    }

    private static GeneratedReport generatedReport() {
        return GeneratedReport.generated(
                "GEN-001",
                definition(),
                metrics(),
                generatedAt(),
                Notes.empty()
        );
    }

    private static ReportDefinition definition() {
        return ReportDefinition.of(
                "RPT-001",
                ReportType.OPERATIONS,
                ReportFormat.PDF,
                period(),
                "USR-001",
                Notes.empty()
        );
    }

    private static List<ReportMetric> metrics() {
        return List.of(
                revenueMetric(),
                ReportMetric.ofDefaultUnit(
                        "MET-002",
                        ReportMetricType.SHIPMENT_COUNT,
                        "Spedizioni totali",
                        "25",
                        Notes.empty()
                )
        );
    }

    private static ReportMetric revenueMetric() {
        return ReportMetric.ofDefaultUnit(
                "MET-001",
                ReportMetricType.TOTAL_REVENUE,
                "Ricavi totali",
                "1250.00",
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
