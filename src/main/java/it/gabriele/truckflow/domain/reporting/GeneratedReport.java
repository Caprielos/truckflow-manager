package it.gabriele.truckflow.domain.reporting;

import it.gabriele.truckflow.domain.shared.Notes;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * Report generato con le sue metriche.
 */
public final class GeneratedReport {

    private static final int MAX_REPORT_NUMBER_LENGTH = 50;

    private final String reportNumber;
    private final ReportDefinition definition;
    private final List<ReportMetric> metrics;
    private final ReportStatus status;
    private final Instant generatedAt;
    private final Notes notes;

    private GeneratedReport(
            String reportNumber,
            ReportDefinition definition,
            List<ReportMetric> metrics,
            ReportStatus status,
            Instant generatedAt,
            Notes notes
    ) {
        this.reportNumber = validateReportNumber(reportNumber);

        if (definition == null) {
            throw new IllegalArgumentException("La definizione report è obbligatoria.");
        }

        if (status == null) {
            throw new IllegalArgumentException("Lo stato report è obbligatorio.");
        }

        this.metrics = validateMetrics(metrics, status);
        validateGeneratedAt(status, generatedAt);

        if (notes == null) {
            throw new IllegalArgumentException("Le note report generato sono obbligatorie.");
        }

        this.definition = definition;
        this.status = status;
        this.generatedAt = generatedAt;
        this.notes = notes;
    }

    public static GeneratedReport draft(
            String reportNumber,
            ReportDefinition definition,
            Notes notes
    ) {
        return new GeneratedReport(
                reportNumber,
                definition,
                List.of(),
                ReportStatus.DRAFT,
                null,
                notes
        );
    }

    public static GeneratedReport generated(
            String reportNumber,
            ReportDefinition definition,
            List<ReportMetric> metrics,
            Instant generatedAt,
            Notes notes
    ) {
        return new GeneratedReport(
                reportNumber,
                definition,
                metrics,
                ReportStatus.GENERATED,
                generatedAt,
                notes
        );
    }

    private static String validateReportNumber(String reportNumber) {
        if (reportNumber == null) {
            throw new IllegalArgumentException("Il numero report generato è obbligatorio.");
        }

        String normalizedReportNumber = reportNumber.trim().toUpperCase();

        if (normalizedReportNumber.isEmpty()) {
            throw new IllegalArgumentException("Il numero report generato non può essere vuoto.");
        }

        if (normalizedReportNumber.length() > MAX_REPORT_NUMBER_LENGTH) {
            throw new IllegalArgumentException("Il numero report generato non può superare "
                    + MAX_REPORT_NUMBER_LENGTH + " caratteri.");
        }

        if (!normalizedReportNumber.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il numero report generato può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedReportNumber;
    }

    private static List<ReportMetric> validateMetrics(
            List<ReportMetric> metrics,
            ReportStatus status
    ) {
        if (metrics == null) {
            throw new IllegalArgumentException("La lista metriche report è obbligatoria.");
        }

        if (metrics.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("La lista metriche report non può contenere valori nulli.");
        }

        boolean mustHaveMetrics = status == ReportStatus.GENERATED
                || status == ReportStatus.PUBLISHED
                || status == ReportStatus.ARCHIVED;

        if (mustHaveMetrics && metrics.isEmpty()) {
            throw new IllegalArgumentException("Un report generato deve contenere almeno una metrica.");
        }

        boolean mustBeEmpty = status == ReportStatus.DRAFT
                || status == ReportStatus.FAILED;

        if (mustBeEmpty && !metrics.isEmpty()) {
            throw new IllegalArgumentException("Un report draft o fallito non deve contenere metriche generate.");
        }

        long uniqueMetricCodes = metrics.stream()
                .map(ReportMetric::getMetricCode)
                .distinct()
                .count();

        if (uniqueMetricCodes != metrics.size()) {
            throw new IllegalArgumentException("Il report non può contenere metriche duplicate.");
        }

        return List.copyOf(metrics);
    }

    private static void validateGeneratedAt(
            ReportStatus status,
            Instant generatedAt
    ) {
        boolean requiresGeneratedAt = status == ReportStatus.GENERATED
                || status == ReportStatus.PUBLISHED
                || status == ReportStatus.ARCHIVED;

        if (requiresGeneratedAt && generatedAt == null) {
            throw new IllegalArgumentException("Un report generato deve avere data e ora di generazione.");
        }

        if (!requiresGeneratedAt && generatedAt != null) {
            throw new IllegalArgumentException("Un report non generato non può avere data e ora di generazione.");
        }
    }

    public GeneratedReport generate(
            List<ReportMetric> metrics,
            Instant generatedAt
    ) {
        if (!ReportingRules.canBeGenerated(this)) {
            throw new IllegalStateException("Il report non può essere generato.");
        }

        return new GeneratedReport(
                reportNumber,
                definition,
                metrics,
                ReportStatus.GENERATED,
                generatedAt,
                notes
        );
    }

    public GeneratedReport publish() {
        if (!ReportingRules.canBePublished(this)) {
            throw new IllegalStateException("Il report non può essere pubblicato.");
        }

        return new GeneratedReport(
                reportNumber,
                definition,
                metrics,
                ReportStatus.PUBLISHED,
                generatedAt,
                notes
        );
    }

    public GeneratedReport archive() {
        if (!ReportingRules.canBeArchived(this)) {
            throw new IllegalStateException("Il report non può essere archiviato.");
        }

        return new GeneratedReport(
                reportNumber,
                definition,
                metrics,
                ReportStatus.ARCHIVED,
                generatedAt,
                notes
        );
    }

    public GeneratedReport fail() {
        if (!ReportingRules.canBeFailed(this)) {
            throw new IllegalStateException("Il report non può essere marcato come fallito.");
        }

        return new GeneratedReport(
                reportNumber,
                definition,
                List.of(),
                ReportStatus.FAILED,
                null,
                notes
        );
    }

    public String getReportNumber() {
        return reportNumber;
    }

    public ReportDefinition getDefinition() {
        return definition;
    }

    public List<ReportMetric> getMetrics() {
        return metrics;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public Instant getGeneratedAt() {
        return generatedAt;
    }

    public Notes getNotes() {
        return notes;
    }

    public int getMetricCount() {
        return metrics.size();
    }

    public boolean isDraft() {
        return status == ReportStatus.DRAFT;
    }

    public boolean isGenerated() {
        return status == ReportStatus.GENERATED;
    }

    public boolean isPublished() {
        return status == ReportStatus.PUBLISHED;
    }

    public boolean isArchived() {
        return status == ReportStatus.ARCHIVED;
    }

    public boolean isFailed() {
        return status == ReportStatus.FAILED;
    }

    public boolean isTerminal() {
        return status.isTerminal();
    }

    public boolean isReadable() {
        return status.isReadable();
    }

    public boolean hasGeneratedAt() {
        return generatedAt != null;
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public boolean hasMetricType(ReportMetricType type) {
        if (type == null) {
            throw new IllegalArgumentException("Il tipo metrica da verificare è obbligatorio.");
        }

        return metrics.stream().anyMatch(metric -> metric.getType() == type);
    }

    public List<ReportMetric> getMetricsByType(ReportMetricType type) {
        if (type == null) {
            throw new IllegalArgumentException("Il tipo metrica da filtrare è obbligatorio.");
        }

        return metrics.stream()
                .filter(metric -> metric.getType() == type)
                .toList();
    }

    public String formatSingleLine() {
        return reportNumber
                + " - " + definition.getType()
                + " - " + status
                + " - metrics: " + metrics.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeneratedReport that)) return false;
        return reportNumber.equals(that.reportNumber)
                && definition.equals(that.definition)
                && metrics.equals(that.metrics)
                && status == that.status
                && Objects.equals(generatedAt, that.generatedAt)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportNumber, definition, metrics, status, generatedAt, notes);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
