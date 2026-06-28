package it.gabriele.truckflow.domain.reporting;

import it.gabriele.truckflow.domain.shared.Notes;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Metrica numerica inclusa in un report.
 */
public final class ReportMetric {

    private static final int MAX_CODE_LENGTH = 50;
    private static final int MAX_LABEL_LENGTH = 150;
    private static final int MAX_UNIT_LENGTH = 30;

    private final String metricCode;
    private final ReportMetricType type;
    private final String label;
    private final BigDecimal value;
    private final String unit;
    private final Notes notes;

    private ReportMetric(
            String metricCode,
            ReportMetricType type,
            String label,
            String value,
            String unit,
            Notes notes
    ) {
        this.metricCode = validateCode(metricCode, "Il codice metrica report è obbligatorio.");

        if (type == null) {
            throw new IllegalArgumentException("Il tipo metrica report è obbligatorio.");
        }

        this.label = validateLabel(label);
        this.value = validateValue(value);
        this.unit = validateUnit(unit);

        if (notes == null) {
            throw new IllegalArgumentException("Le note metrica report sono obbligatorie.");
        }

        this.type = type;
        this.notes = notes;
    }

    public static ReportMetric of(
            String metricCode,
            ReportMetricType type,
            String label,
            String value,
            String unit,
            Notes notes
    ) {
        return new ReportMetric(metricCode, type, label, value, unit, notes);
    }

    public static ReportMetric ofDefaultUnit(
            String metricCode,
            ReportMetricType type,
            String label,
            String value,
            Notes notes
    ) {
        if (type == null) {
            throw new IllegalArgumentException("Il tipo metrica report è obbligatorio.");
        }

        return new ReportMetric(
                metricCode,
                type,
                label,
                value,
                type.getDefaultUnit(),
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
            throw new IllegalArgumentException("Il codice metrica report non può superare "
                    + MAX_CODE_LENGTH + " caratteri.");
        }

        if (!normalizedCode.matches("[A-Z0-9_.-]+")) {
            throw new IllegalArgumentException("Il codice metrica report può contenere solo lettere, numeri, punti, trattini e underscore.");
        }

        return normalizedCode;
    }

    private static String validateLabel(String label) {
        if (label == null) {
            throw new IllegalArgumentException("L'etichetta metrica report è obbligatoria.");
        }

        String normalizedLabel = label.trim();

        if (normalizedLabel.isEmpty()) {
            throw new IllegalArgumentException("L'etichetta metrica report non può essere vuota.");
        }

        if (normalizedLabel.length() > MAX_LABEL_LENGTH) {
            throw new IllegalArgumentException("L'etichetta metrica report non può superare "
                    + MAX_LABEL_LENGTH + " caratteri.");
        }

        return normalizedLabel;
    }

    private static BigDecimal validateValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Il valore metrica report è obbligatorio.");
        }

        BigDecimal parsedValue;

        try {
            parsedValue = new BigDecimal(value.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Il valore metrica report non è valido.", exception);
        }

        if (parsedValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Il valore metrica report non può essere negativo.");
        }

        return parsedValue.stripTrailingZeros();
    }

    private static String validateUnit(String unit) {
        if (unit == null) {
            throw new IllegalArgumentException("L'unità metrica report è obbligatoria.");
        }

        String normalizedUnit = unit.trim();

        if (normalizedUnit.isEmpty()) {
            throw new IllegalArgumentException("L'unità metrica report non può essere vuota.");
        }

        if (normalizedUnit.length() > MAX_UNIT_LENGTH) {
            throw new IllegalArgumentException("L'unità metrica report non può superare "
                    + MAX_UNIT_LENGTH + " caratteri.");
        }

        return normalizedUnit;
    }

    public String getMetricCode() {
        return metricCode;
    }

    public ReportMetricType getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean isMonetary() {
        return type.isMonetary();
    }

    public boolean isPercentage() {
        return type.isPercentage();
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public String formatSingleLine() {
        return metricCode
                + " - " + type
                + " - " + value.toPlainString()
                + " " + unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportMetric that)) return false;
        return metricCode.equals(that.metricCode)
                && type == that.type
                && label.equals(that.label)
                && value.compareTo(that.value) == 0
                && unit.equals(that.unit)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metricCode, type, label, value.stripTrailingZeros(), unit, notes);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
