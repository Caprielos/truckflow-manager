package it.gabriele.truckflow.domain.reporting;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Notes;

import java.util.Objects;

/**
 * Definizione di un report richiesto.
 */
public final class ReportDefinition {

    private static final int MAX_CODE_LENGTH = 50;

    private final String reportCode;
    private final ReportType type;
    private final ReportFormat format;
    private final DateRange period;
    private final String requestedByAccountId;
    private final Notes notes;

    private ReportDefinition(
            String reportCode,
            ReportType type,
            ReportFormat format,
            DateRange period,
            String requestedByAccountId,
            Notes notes
    ) {
        this.reportCode = validateCode(reportCode, "Il codice report è obbligatorio.");

        if (type == null) {
            throw new IllegalArgumentException("Il tipo report è obbligatorio.");
        }

        if (format == null) {
            throw new IllegalArgumentException("Il formato report è obbligatorio.");
        }

        if (period == null) {
            throw new IllegalArgumentException("Il periodo report è obbligatorio.");
        }

        this.requestedByAccountId = validateCode(requestedByAccountId, "L'account richiedente report è obbligatorio.");

        if (notes == null) {
            throw new IllegalArgumentException("Le note report sono obbligatorie.");
        }

        this.type = type;
        this.format = format;
        this.period = period;
        this.notes = notes;
    }

    public static ReportDefinition of(
            String reportCode,
            ReportType type,
            ReportFormat format,
            DateRange period,
            String requestedByAccountId,
            Notes notes
    ) {
        return new ReportDefinition(
                reportCode,
                type,
                format,
                period,
                requestedByAccountId,
                notes
        );
    }

    private static String validateCode(String code, String nullMessage) {
        if (code == null) {
            throw new IllegalArgumentException(nullMessage);
        }

        String normalizedCode = code.trim().toUpperCase();

        if (normalizedCode.isEmpty()) {
            throw new IllegalArgumentException(nullMessage);
        }

        if (normalizedCode.length() > MAX_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice report non può superare "
                    + MAX_CODE_LENGTH + " caratteri.");
        }

        if (!normalizedCode.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice report può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedCode;
    }

    public String getReportCode() {
        return reportCode;
    }

    public ReportType getType() {
        return type;
    }

    public ReportFormat getFormat() {
        return format;
    }

    public DateRange getPeriod() {
        return period;
    }

    public String getRequestedByAccountId() {
        return requestedByAccountId;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean isFinancialReport() {
        return type.isFinancialReport();
    }

    public boolean isComplianceReport() {
        return type.isComplianceReport();
    }

    public boolean isSustainabilityReport() {
        return type.isSustainabilityReport();
    }

    public boolean requiresRestrictedAccess() {
        return type.requiresRestrictedAccess();
    }

    public boolean isDownloadable() {
        return format.isDownloadable();
    }

    public boolean isMachineReadable() {
        return format.isMachineReadable();
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public String formatSingleLine() {
        return reportCode
                + " - " + type
                + " - " + format
                + " - requested by: " + requestedByAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportDefinition that)) return false;
        return reportCode.equals(that.reportCode)
                && type == that.type
                && format == that.format
                && period.equals(that.period)
                && requestedByAccountId.equals(that.requestedByAccountId)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportCode, type, format, period, requestedByAccountId, notes);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
