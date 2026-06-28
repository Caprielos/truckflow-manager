package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.util.Objects;

/**
 * Singola riga di fattura fornitore: camion, rimorchio, gomme, manutenzione, assicurazione, ecc.
 */
public final class PurchaseLine {

    private static final int MAX_CODE_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private final String lineCode;
    private final PurchaseCategory category;
    private final String description;
    private final Money amount;
    private final Notes notes;

    private PurchaseLine(String lineCode, PurchaseCategory category, String description, Money amount, Notes notes) {
        this.lineCode = validateCode(lineCode, "Il codice riga acquisto è obbligatorio.");
        if (category == null) {
            throw new IllegalArgumentException("La categoria acquisto è obbligatoria.");
        }
        this.description = validateDescription(description);
        if (amount == null) {
            throw new IllegalArgumentException("L'importo riga acquisto è obbligatorio.");
        }
        if (amount.getAmount().signum() == 0) {
            throw new IllegalArgumentException("L'importo riga acquisto deve essere maggiore di zero.");
        }
        if (notes == null) {
            throw new IllegalArgumentException("Le note riga acquisto sono obbligatorie.");
        }
        this.category = category;
        this.amount = amount;
        this.notes = notes;
    }

    public static PurchaseLine of(String lineCode, PurchaseCategory category, String description, Money amount, Notes notes) {
        return new PurchaseLine(lineCode, category, description, amount, notes);
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

    private static String validateDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("La descrizione riga acquisto è obbligatoria.");
        }
        String normalized = description.trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("La descrizione riga acquisto non può essere vuota.");
        }
        if (normalized.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("La descrizione riga acquisto non può superare "
                    + MAX_DESCRIPTION_LENGTH + " caratteri.");
        }
        return normalized;
    }

    public String getLineCode() {
        return lineCode;
    }

    public PurchaseCategory getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Money getAmount() {
        return amount;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean isCapitalAsset() {
        return category.isCapitalAsset();
    }

    public boolean isOperatingExpense() {
        return category.isOperatingExpense();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseLine that)) return false;
        return lineCode.equals(that.lineCode)
                && category == that.category
                && description.equals(that.description)
                && amount.equals(that.amount)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineCode, category, description, amount, notes);
    }
}
