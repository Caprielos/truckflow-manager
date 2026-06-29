package it.gabriele.truckflow.domain.payroll;

import it.gabriele.truckflow.domain.economics.MissionCostLine;
import it.gabriele.truckflow.domain.economics.MissionCostType;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Cedolino/costo autista calcolato per una missione.
 * Include sia importi pagati/rimborsati all'autista sia costi aziendali collegati.
 */
public final class DriverMissionPayroll {

    private static final int MAX_CODE_LENGTH = 50;

    private final String payrollCode;
    private final String missionNumber;
    private final String driverCode;
    private final String payrollPolicyCode;
    private final List<DriverMissionPayLine> payLines;
    private final Notes notes;

    private DriverMissionPayroll(
            String payrollCode,
            String missionNumber,
            String driverCode,
            String payrollPolicyCode,
            List<DriverMissionPayLine> payLines,
            Notes notes
    ) {
        this.payrollCode = validateCode(payrollCode, "Il codice payroll missione è obbligatorio.");
        this.missionNumber = validateCode(missionNumber, "Il numero missione è obbligatorio.");
        this.driverCode = validateCode(driverCode, "Il codice autista è obbligatorio.");
        this.payrollPolicyCode = validateCode(payrollPolicyCode, "Il codice politica paga è obbligatorio.");
        if (payLines == null) {
            throw new IllegalArgumentException("Le righe payroll missione sono obbligatorie.");
        }
        if (payLines.isEmpty()) {
            throw new IllegalArgumentException("Il payroll missione deve avere almeno una riga.");
        }
        if (payLines.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Le righe payroll missione non possono contenere null.");
        }
        long uniqueCodes = payLines.stream().map(DriverMissionPayLine::getLineCode).distinct().count();
        if (uniqueCodes != payLines.size()) {
            throw new IllegalArgumentException("Le righe payroll missione non possono avere codici duplicati.");
        }
        validateCurrencyCompatibility(payLines);
        if (notes == null) {
            throw new IllegalArgumentException("Le note payroll missione sono obbligatorie.");
        }
        this.payLines = List.copyOf(payLines);
        this.notes = notes;
    }

    public static DriverMissionPayroll of(
            String payrollCode,
            String missionNumber,
            String driverCode,
            String payrollPolicyCode,
            List<DriverMissionPayLine> payLines,
            Notes notes
    ) {
        return new DriverMissionPayroll(payrollCode, missionNumber, driverCode, payrollPolicyCode, payLines, notes);
    }

    public Money calculateTotalEmployerCost() {
        Money total = payLines.get(0).getAmount();
        for (int i = 1; i < payLines.size(); i++) {
            total = total.add(payLines.get(i).getAmount());
        }
        return total;
    }

    public Money calculateAllowancesAndReimbursements() {
        List<DriverMissionPayLine> selected = payLines.stream()
                .filter(DriverMissionPayLine::isAllowanceOrReimbursement)
                .toList();
        return sumOrZero(selected);
    }

    public Money calculateEmployerCharges() {
        List<DriverMissionPayLine> selected = payLines.stream()
                .filter(DriverMissionPayLine::isEmployerCost)
                .toList();
        return sumOrZero(selected);
    }

    public boolean containsComponent(DriverPayComponentType componentType) {
        if (componentType == null) {
            throw new IllegalArgumentException("Il tipo voce payroll da cercare è obbligatorio.");
        }
        return payLines.stream().anyMatch(line -> line.getComponentType() == componentType);
    }

    public MissionCostLine toMissionCostLine(String lineCode, Notes notes) {
        return MissionCostLine.of(
                lineCode,
                MissionCostType.DRIVER_WAGE,
                "Costo autista missione " + missionNumber + " - " + driverCode,
                calculateTotalEmployerCost(),
                notes
        );
    }

    private Money sumOrZero(List<DriverMissionPayLine> selected) {
        Currency currency = payLines.get(0).getAmount().getCurrency();
        if (selected.isEmpty()) {
            return Money.of(BigDecimal.ZERO, currency);
        }
        Money total = selected.get(0).getAmount();
        for (int i = 1; i < selected.size(); i++) {
            total = total.add(selected.get(i).getAmount());
        }
        return total;
    }

    private static void validateCurrencyCompatibility(List<DriverMissionPayLine> lines) {
        Money reference = lines.get(0).getAmount();
        for (int i = 1; i < lines.size(); i++) {
            reference.add(lines.get(i).getAmount());
        }
    }

    private static String validateCode(String code, String nullMessage) {
        if (code == null) {
            throw new IllegalArgumentException(nullMessage);
        }
        String normalized = code.trim().toUpperCase();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(nullMessage);
        }
        if (normalized.length() > MAX_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
        }
        if (!normalized.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice può contenere solo lettere, numeri, trattini e underscore.");
        }
        return normalized;
    }

    public String getPayrollCode() {
        return payrollCode;
    }

    public String getMissionNumber() {
        return missionNumber;
    }

    public String getDriverCode() {
        return driverCode;
    }

    public String getPayrollPolicyCode() {
        return payrollPolicyCode;
    }

    public List<DriverMissionPayLine> getPayLines() {
        return payLines;
    }

    public Notes getNotes() {
        return notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DriverMissionPayroll that)) return false;
        return payrollCode.equals(that.payrollCode)
                && missionNumber.equals(that.missionNumber)
                && driverCode.equals(that.driverCode)
                && payrollPolicyCode.equals(that.payrollPolicyCode)
                && payLines.equals(that.payLines)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payrollCode, missionNumber, driverCode, payrollPolicyCode, payLines, notes);
    }
}
