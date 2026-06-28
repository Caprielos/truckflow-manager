package it.gabriele.truckflow.domain.reporting;

import java.math.BigDecimal;

/**
 * Regole di dominio per reporting e metriche.
 */
public final class ReportingRules {

    private ReportingRules() {
    }

    public static boolean canBeGenerated(GeneratedReport report) {
        validateReport(report);

        return report.getStatus() == ReportStatus.DRAFT
                || report.getStatus() == ReportStatus.FAILED;
    }

    public static boolean canBePublished(GeneratedReport report) {
        validateReport(report);

        return report.getStatus() == ReportStatus.GENERATED;
    }

    public static boolean canBeArchived(GeneratedReport report) {
        validateReport(report);

        return report.getStatus() == ReportStatus.GENERATED
                || report.getStatus() == ReportStatus.PUBLISHED;
    }

    public static boolean canBeFailed(GeneratedReport report) {
        validateReport(report);

        return report.getStatus() == ReportStatus.DRAFT
                || report.getStatus() == ReportStatus.GENERATED;
    }

    public static boolean isReadyForPublication(GeneratedReport report) {
        validateReport(report);

        return report.isGenerated()
                && report.hasGeneratedAt()
                && report.getMetricCount() > 0;
    }

    public static boolean containsFinancialMetrics(GeneratedReport report) {
        validateReport(report);

        return report.getMetrics().stream()
                .anyMatch(ReportMetric::isMonetary);
    }

    public static boolean containsSustainabilityMetrics(GeneratedReport report) {
        validateReport(report);

        return report.getDefinition().isSustainabilityReport()
                || report.hasMetricType(ReportMetricType.TOTAL_CO2_KG);
    }

    public static boolean requiresRestrictedAccess(GeneratedReport report) {
        validateReport(report);

        return report.getDefinition().requiresRestrictedAccess()
                || containsFinancialMetrics(report);
    }

    public static boolean containsMetricType(
            GeneratedReport report,
            ReportMetricType type
    ) {
        validateReport(report);

        if (type == null) {
            throw new IllegalArgumentException("Il tipo metrica report è obbligatorio.");
        }

        return report.hasMetricType(type);
    }

    public static BigDecimal calculateMetricTotal(
            GeneratedReport report,
            ReportMetricType type
    ) {
        validateReport(report);

        if (type == null) {
            throw new IllegalArgumentException("Il tipo metrica report è obbligatorio.");
        }

        return report.getMetricsByType(type).stream()
                .map(ReportMetric::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .stripTrailingZeros();
    }

    private static void validateReport(GeneratedReport report) {
        if (report == null) {
            throw new IllegalArgumentException("Il report è obbligatorio.");
        }
    }
}
