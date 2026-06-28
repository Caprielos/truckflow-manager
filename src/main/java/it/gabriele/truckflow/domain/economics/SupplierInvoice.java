package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Fattura fornitore: registra tutto ciò che l'azienda compra o paga.
 */
public final class SupplierInvoice {

    private static final int MAX_CODE_LENGTH = 50;

    private final String invoiceNumber;
    private final String supplierCode;
    private final LocalDate issueDate;
    private final LocalDate dueDate;
    private final SupplierInvoiceStatus status;
    private final List<PurchaseLine> lines;
    private final Notes notes;

    private SupplierInvoice(
            String invoiceNumber,
            String supplierCode,
            LocalDate issueDate,
            LocalDate dueDate,
            SupplierInvoiceStatus status,
            List<PurchaseLine> lines,
            Notes notes
    ) {
        this.invoiceNumber = validateCode(invoiceNumber, "Il numero fattura fornitore è obbligatorio.");
        this.supplierCode = validateCode(supplierCode, "Il codice fornitore è obbligatorio.");
        if (issueDate == null) {
            throw new IllegalArgumentException("La data fattura fornitore è obbligatoria.");
        }
        if (dueDate == null) {
            throw new IllegalArgumentException("La scadenza fattura fornitore è obbligatoria.");
        }
        if (dueDate.isBefore(issueDate)) {
            throw new IllegalArgumentException("La scadenza non può essere precedente alla data fattura.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Lo stato fattura fornitore è obbligatorio.");
        }
        this.lines = validateLines(lines);
        if (notes == null) {
            throw new IllegalArgumentException("Le note fattura fornitore sono obbligatorie.");
        }
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = status;
        this.notes = notes;
    }

    public static SupplierInvoice received(
            String invoiceNumber,
            String supplierCode,
            LocalDate issueDate,
            LocalDate dueDate,
            List<PurchaseLine> lines,
            Notes notes
    ) {
        return new SupplierInvoice(invoiceNumber, supplierCode, issueDate, dueDate,
                SupplierInvoiceStatus.RECEIVED, lines, notes);
    }

    public static SupplierInvoice received(
            String invoiceNumber,
            String supplierCode,
            LocalDate issueDate,
            LocalDate dueDate,
            PurchaseLine firstLine,
            PurchaseLine... otherLines
    ) {
        if (firstLine == null) {
            throw new IllegalArgumentException("La prima riga fattura fornitore è obbligatoria.");
        }
        List<PurchaseLine> lines = new ArrayList<>();
        lines.add(firstLine);
        if (otherLines != null) {
            for (PurchaseLine line : otherLines) {
                lines.add(line);
            }
        }
        return received(invoiceNumber, supplierCode, issueDate, dueDate, lines, Notes.empty());
    }

    private static List<PurchaseLine> validateLines(List<PurchaseLine> lines) {
        if (lines == null) {
            throw new IllegalArgumentException("Le righe fattura fornitore sono obbligatorie.");
        }
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("La fattura fornitore deve avere almeno una riga.");
        }
        if (lines.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Le righe fattura fornitore non possono contenere null.");
        }
        long uniqueCodes = lines.stream().map(PurchaseLine::getLineCode).distinct().count();
        if (uniqueCodes != lines.size()) {
            throw new IllegalArgumentException("La fattura fornitore non può contenere codici riga duplicati.");
        }
        validateCurrencyCompatibility(lines);
        return List.copyOf(lines);
    }

    private static void validateCurrencyCompatibility(List<PurchaseLine> lines) {
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

    public SupplierInvoice approve() {
        if (status != SupplierInvoiceStatus.RECEIVED) {
            throw new IllegalStateException("Solo una fattura ricevuta può essere approvata.");
        }
        return new SupplierInvoice(invoiceNumber, supplierCode, issueDate, dueDate,
                SupplierInvoiceStatus.APPROVED, lines, notes);
    }

    public SupplierInvoice markPaid() {
        if (!status.isPayable()) {
            throw new IllegalStateException("La fattura fornitore non può essere pagata nello stato attuale.");
        }
        return new SupplierInvoice(invoiceNumber, supplierCode, issueDate, dueDate,
                SupplierInvoiceStatus.PAID, lines, notes);
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public SupplierInvoiceStatus getStatus() {
        return status;
    }

    public List<PurchaseLine> getLines() {
        return lines;
    }

    public Notes getNotes() {
        return notes;
    }

    public Money calculateTotal() {
        return sum(lines);
    }

    public Money calculateCapitalAssetTotal() {
        return sum(lines.stream().filter(PurchaseLine::isCapitalAsset).toList());
    }

    public Money calculateOperatingExpenseTotal() {
        return sum(lines.stream().filter(PurchaseLine::isOperatingExpense).toList());
    }

    private Money sum(List<PurchaseLine> selectedLines) {
        if (selectedLines.isEmpty()) {
            return Money.of(java.math.BigDecimal.ZERO, lines.get(0).getAmount().getCurrency());
        }
        Money total = selectedLines.get(0).getAmount();
        for (int i = 1; i < selectedLines.size(); i++) {
            total = total.add(selectedLines.get(i).getAmount());
        }
        return total;
    }

    public boolean containsCapitalAssets() {
        return lines.stream().anyMatch(PurchaseLine::isCapitalAsset);
    }

    public boolean containsOperatingExpenses() {
        return lines.stream().anyMatch(PurchaseLine::isOperatingExpense);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierInvoice that)) return false;
        return invoiceNumber.equals(that.invoiceNumber)
                && supplierCode.equals(that.supplierCode)
                && issueDate.equals(that.issueDate)
                && dueDate.equals(that.dueDate)
                && status == that.status
                && lines.equals(that.lines)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceNumber, supplierCode, issueDate, dueDate, status, lines, notes);
    }
}
