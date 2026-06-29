package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.util.Objects;

/**
 * Spesa di periodo: assicurazione, bollo, software, affitto, stipendi, leasing, ufficio, ecc.
 */
public final class RecurringExpense {

    private static final int MAX_CODE_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private final String expenseCode;
    private final RecurringExpenseCategory category;
    private final String description;
    private final DateRange period;
    private final VatBreakdown amount;
    private final String assignedVehicleFleetNumber;
    private final Notes notes;

    private RecurringExpense(
            String expenseCode,
            RecurringExpenseCategory category,
            String description,
            DateRange period,
            VatBreakdown amount,
            String assignedVehicleFleetNumber,
            Notes notes
    ) {
        this.expenseCode = validateCode(expenseCode, "Il codice spesa ricorrente è obbligatorio.");
        if (category == null) {
            throw new IllegalArgumentException("La categoria spesa ricorrente è obbligatoria.");
        }
        this.description = validateDescription(description);
        if (period == null) {
            throw new IllegalArgumentException("Il periodo spesa ricorrente è obbligatorio.");
        }
        if (amount == null) {
            throw new IllegalArgumentException("L'importo spesa ricorrente è obbligatorio.");
        }
        this.assignedVehicleFleetNumber = normalizeOptionalCode(assignedVehicleFleetNumber);
        if (notes == null) {
            throw new IllegalArgumentException("Le note spesa ricorrente sono obbligatorie.");
        }
        this.category = category;
        this.period = period;
        this.amount = amount;
        this.notes = notes;
    }

    public static RecurringExpense of(
            String expenseCode,
            RecurringExpenseCategory category,
            String description,
            DateRange period,
            VatBreakdown amount,
            String assignedVehicleFleetNumber,
            Notes notes
    ) {
        return new RecurringExpense(expenseCode, category, description, period, amount, assignedVehicleFleetNumber, notes);
    }

    public static RecurringExpense noVat(
            String expenseCode,
            RecurringExpenseCategory category,
            String description,
            DateRange period,
            Money amount,
            String assignedVehicleFleetNumber,
            Notes notes
    ) {
        return of(expenseCode, category, description, period,
                VatBreakdown.outOfScope(amount, "NO_VAT"), assignedVehicleFleetNumber, notes);
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

    private static String normalizeOptionalCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        return validateCode(code, "Il codice veicolo assegnato non può essere vuoto.");
    }

    private static String validateDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("La descrizione spesa ricorrente è obbligatoria.");
        }
        String normalized = description.trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("La descrizione spesa ricorrente non può essere vuota.");
        }
        if (normalized.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("La descrizione spesa ricorrente non può superare "
                    + MAX_DESCRIPTION_LENGTH + " caratteri.");
        }
        return normalized;
    }

    public String getExpenseCode() {
        return expenseCode;
    }

    public RecurringExpenseCategory getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public DateRange getPeriod() {
        return period;
    }

    public VatBreakdown getAmount() {
        return amount;
    }

    public String getAssignedVehicleFleetNumber() {
        return assignedVehicleFleetNumber;
    }

    public boolean isAssignedToVehicle() {
        return assignedVehicleFleetNumber != null;
    }

    public Notes getNotes() {
        return notes;
    }

    public Money calculateGrossAmount() {
        return amount.getGrossAmount();
    }

    public Money calculateAccountingCost() {
        return amount.calculateAccountingCost();
    }

    public Money calculateRecoverableVatAmount() {
        return amount.calculateRecoverableVatAmount();
    }

    public boolean overlaps(DateRange targetPeriod) {
        if (targetPeriod == null) {
            throw new IllegalArgumentException("Il periodo da confrontare è obbligatorio.");
        }
        return period.overlapsWith(targetPeriod);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecurringExpense that)) return false;
        return expenseCode.equals(that.expenseCode)
                && category == that.category
                && description.equals(that.description)
                && period.equals(that.period)
                && amount.equals(that.amount)
                && Objects.equals(assignedVehicleFleetNumber, that.assignedVehicleFleetNumber)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseCode, category, description, period, amount, assignedVehicleFleetNumber, notes);
    }
}
