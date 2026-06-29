package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Fattura cliente economica con imponibile, IVA e totale lordo.
 * Serve a non confondere IVA incassata con guadagno dell'azienda.
 */
public final class CustomerRevenueInvoice {

    private static final int MAX_CODE_LENGTH = 50;

    private final String invoiceNumber;
    private final String customerCode;
    private final String shipmentNumber;
    private final LocalDate issueDate;
    private final LocalDate dueDate;
    private final List<TaxableRevenueLine> lines;
    private final Notes notes;

    private CustomerRevenueInvoice(
            String invoiceNumber,
            String customerCode,
            String shipmentNumber,
            LocalDate issueDate,
            LocalDate dueDate,
            List<TaxableRevenueLine> lines,
            Notes notes
    ) {
        this.invoiceNumber = validateCode(invoiceNumber, "Il numero fattura cliente è obbligatorio.");
        this.customerCode = validateCode(customerCode, "Il codice cliente è obbligatorio.");
        this.shipmentNumber = validateCode(shipmentNumber, "Il numero spedizione è obbligatorio.");
        if (issueDate == null) {
            throw new IllegalArgumentException("La data emissione fattura cliente è obbligatoria.");
        }
        if (dueDate == null) {
            throw new IllegalArgumentException("La data scadenza fattura cliente è obbligatoria.");
        }
        if (dueDate.isBefore(issueDate)) {
            throw new IllegalArgumentException("La scadenza fattura cliente non può precedere l'emissione.");
        }
        this.lines = validateLines(lines);
        if (notes == null) {
            throw new IllegalArgumentException("Le note fattura cliente sono obbligatorie.");
        }
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.notes = notes;
    }

    public static CustomerRevenueInvoice of(
            String invoiceNumber,
            String customerCode,
            String shipmentNumber,
            LocalDate issueDate,
            LocalDate dueDate,
            List<TaxableRevenueLine> lines,
            Notes notes
    ) {
        return new CustomerRevenueInvoice(invoiceNumber, customerCode, shipmentNumber, issueDate, dueDate, lines, notes);
    }

    public static CustomerRevenueInvoice of(
            String invoiceNumber,
            String customerCode,
            String shipmentNumber,
            LocalDate issueDate,
            LocalDate dueDate,
            TaxableRevenueLine firstLine,
            TaxableRevenueLine... otherLines
    ) {
        if (firstLine == null) {
            throw new IllegalArgumentException("La prima riga fattura cliente è obbligatoria.");
        }
        List<TaxableRevenueLine> lines = new ArrayList<>();
        lines.add(firstLine);
        if (otherLines != null) {
            for (TaxableRevenueLine line : otherLines) {
                lines.add(line);
            }
        }
        return of(invoiceNumber, customerCode, shipmentNumber, issueDate, dueDate, lines, Notes.empty());
    }

    private static List<TaxableRevenueLine> validateLines(List<TaxableRevenueLine> lines) {
        if (lines == null) {
            throw new IllegalArgumentException("Le righe fattura cliente sono obbligatorie.");
        }
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("La fattura cliente deve avere almeno una riga.");
        }
        if (lines.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Le righe fattura cliente non possono contenere null.");
        }
        long uniqueCodes = lines.stream().map(TaxableRevenueLine::getLineCode).distinct().count();
        if (uniqueCodes != lines.size()) {
            throw new IllegalArgumentException("La fattura cliente non può avere codici riga duplicati.");
        }
        Money reference = lines.get(0).getGrossAmount();
        for (int i = 1; i < lines.size(); i++) {
            reference.add(lines.get(i).getGrossAmount());
        }
        return List.copyOf(lines);
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

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public List<TaxableRevenueLine> getLines() {
        return lines;
    }

    public Notes getNotes() {
        return notes;
    }

    public Money calculateNetTotal() {
        return sum(lines.stream().map(TaxableRevenueLine::getNetAmount).toList());
    }

    public Money calculateVatTotal() {
        return sum(lines.stream().map(TaxableRevenueLine::getVatAmount).toList());
    }

    public Money calculateGrossTotal() {
        return sum(lines.stream().map(TaxableRevenueLine::getGrossAmount).toList());
    }

    public List<MissionRevenueLine> toMissionRevenueLines() {
        return lines.stream().map(TaxableRevenueLine::toMissionRevenueLine).toList();
    }

    private Money sum(List<Money> amounts) {
        Currency currency = lines.get(0).getGrossAmount().getCurrency();
        Money total = Money.of(BigDecimal.ZERO, currency);
        for (Money amount : amounts) {
            total = total.add(amount);
        }
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerRevenueInvoice that)) return false;
        return invoiceNumber.equals(that.invoiceNumber)
                && customerCode.equals(that.customerCode)
                && shipmentNumber.equals(that.shipmentNumber)
                && issueDate.equals(that.issueDate)
                && dueDate.equals(that.dueDate)
                && lines.equals(that.lines)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceNumber, customerCode, shipmentNumber, issueDate, dueDate, lines, notes);
    }
}
