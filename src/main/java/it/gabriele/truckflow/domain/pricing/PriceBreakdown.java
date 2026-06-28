package it.gabriele.truckflow.domain.pricing;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Rappresenta il dettaglio economico di un preventivo.
 */
public final class PriceBreakdown {

    private static final int MAX_QUOTE_NUMBER_LENGTH = 50;

    private final String quoteNumber;
    private final List<PricingLine> lines;
    private final Notes notes;

    private PriceBreakdown(
            String quoteNumber,
            List<PricingLine> lines,
            Notes notes
    ) {
        this.quoteNumber = validateQuoteNumber(quoteNumber);
        this.lines = validateLines(lines);

        if (notes == null) {
            throw new IllegalArgumentException("Le note del preventivo sono obbligatorie.");
        }

        validateTotalIsNotNegative(this.lines);

        this.notes = notes;
    }

    public static PriceBreakdown of(
            String quoteNumber,
            List<PricingLine> lines,
            Notes notes
    ) {
        return new PriceBreakdown(quoteNumber, lines, notes);
    }

    public static PriceBreakdown of(
            String quoteNumber,
            PricingLine firstLine,
            PricingLine... otherLines
    ) {
        if (firstLine == null) {
            throw new IllegalArgumentException("La prima voce prezzo è obbligatoria.");
        }

        List<PricingLine> lines = new ArrayList<>();
        lines.add(firstLine);

        if (otherLines != null) {
            for (PricingLine line : otherLines) {
                lines.add(line);
            }
        }

        return new PriceBreakdown(quoteNumber, lines, Notes.empty());
    }

    private static String validateQuoteNumber(String quoteNumber) {
        if (quoteNumber == null) {
            throw new IllegalArgumentException("Il numero preventivo è obbligatorio.");
        }

        String normalizedQuoteNumber = quoteNumber.trim().toUpperCase();

        if (normalizedQuoteNumber.isEmpty()) {
            throw new IllegalArgumentException("Il numero preventivo non può essere vuoto.");
        }

        if (normalizedQuoteNumber.length() > MAX_QUOTE_NUMBER_LENGTH) {
            throw new IllegalArgumentException("Il numero preventivo non può superare "
                    + MAX_QUOTE_NUMBER_LENGTH + " caratteri.");
        }

        if (!normalizedQuoteNumber.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il numero preventivo può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedQuoteNumber;
    }

    private static List<PricingLine> validateLines(List<PricingLine> lines) {
        if (lines == null) {
            throw new IllegalArgumentException("La lista voci prezzo è obbligatoria.");
        }

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Il preventivo deve avere almeno una voce prezzo.");
        }

        if (lines.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("La lista voci prezzo non può contenere valori nulli.");
        }

        boolean hasPositiveChargeLine = lines.stream()
                .anyMatch(PricingLine::increasesTotal);

        if (!hasPositiveChargeLine) {
            throw new IllegalArgumentException("Il preventivo deve avere almeno una voce che aumenta il totale.");
        }

        long uniqueCodes = lines.stream()
                .map(PricingLine::getLineCode)
                .distinct()
                .count();

        if (uniqueCodes != lines.size()) {
            throw new IllegalArgumentException("Il preventivo non può contenere codici voce duplicati.");
        }

        validateCurrencyCompatibility(lines);

        return List.copyOf(lines);
    }

    private static void validateCurrencyCompatibility(List<PricingLine> lines) {
        Money reference = lines.get(0).getAmount();

        for (int i = 1; i < lines.size(); i++) {
            reference.add(lines.get(i).getAmount());
        }
    }

    private static void validateTotalIsNotNegative(List<PricingLine> lines) {
        calculateTotalFrom(lines);
    }

    private static Money calculateTotalFrom(List<PricingLine> lines) {
        Money total = null;

        for (PricingLine line : lines) {
            if (line.increasesTotal()) {
                total = total == null ? line.getAmount() : total.add(line.getAmount());
            }
        }

        if (total == null) {
            throw new IllegalArgumentException("Il preventivo deve avere almeno una voce positiva.");
        }

        for (PricingLine line : lines) {
            if (line.decreasesTotal()) {
                total = total.subtract(line.getAmount());
            }
        }

        return total;
    }

    public String getQuoteNumber() {
        return quoteNumber;
    }

    public List<PricingLine> getLines() {
        return lines;
    }

    public Notes getNotes() {
        return notes;
    }

    public int getLineCount() {
        return lines.size();
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public boolean hasDiscounts() {
        return lines.stream().anyMatch(PricingLine::isDiscount);
    }

    public boolean hasSurcharges() {
        return lines.stream().anyMatch(PricingLine::isSurcharge);
    }

    public boolean hasLineType(PricingLineType type) {
        if (type == null) {
            throw new IllegalArgumentException("Il tipo voce prezzo da verificare è obbligatorio.");
        }

        return lines.stream().anyMatch(line -> line.getType() == type);
    }

    public List<PricingLine> getChargeLines() {
        return lines.stream()
                .filter(PricingLine::increasesTotal)
                .toList();
    }

    public List<PricingLine> getDiscountLines() {
        return lines.stream()
                .filter(PricingLine::isDiscount)
                .toList();
    }

    public Money calculateTotal() {
        return calculateTotalFrom(lines);
    }

    public String formatSingleLine() {
        return quoteNumber + " - lines: " + lines.size() + " - total: " + calculateTotal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceBreakdown that)) return false;
        return quoteNumber.equals(that.quoteNumber)
                && lines.equals(that.lines)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quoteNumber, lines, notes);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
