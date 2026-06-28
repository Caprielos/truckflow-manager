package it.gabriele.truckflow.domain.shared;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

/**
 * Rappresenta un importo di denaro del dominio.
 * Usa BigDecimal per evitare errori di precisione nei calcoli monetari.
 */
public final class Money {

    private final BigDecimal amount;
    private final Currency currency;

    private Money(BigDecimal amount, Currency currency) {
        if (amount == null) {
            throw new IllegalArgumentException("L'importo è obbligatorio.");
        }

        if (currency == null) {
            throw new IllegalArgumentException("La valuta è obbligatoria.");
        }

        if (amount.signum() < 0) {
            throw new IllegalArgumentException("L'importo non può essere negativo.");
        }

        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    public static Money of(String amount, String currencyCode) {
        return new Money(new BigDecimal(amount), Currency.getInstance(currencyCode));
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getCurrencyCode() {
        return currency.getCurrencyCode();
    }

    public Money add(Money other) {
        validateSameCurrency(other);

        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        validateSameCurrency(other);

        BigDecimal result = this.amount.subtract(other.amount);

        if (result.signum() < 0) {
            throw new IllegalArgumentException("Il risultato non può essere negativo.");
        }

        return new Money(result, this.currency);
    }

    public boolean isGreaterThan(Money other) {
        validateSameCurrency(other);

        return this.amount.compareTo(other.amount) > 0;
    }

    public boolean isLessThanOrEqualTo(Money other) {
        validateSameCurrency(other);

        return this.amount.compareTo(other.amount) <= 0;
    }

    private void validateSameCurrency(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("L'importo da confrontare è obbligatorio.");
        }

        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Gli importi devono avere la stessa valuta.");
        }
    }

    private BigDecimal normalizedAmount() {
        return amount.stripTrailingZeros();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;
        return normalizedAmount().compareTo(money.normalizedAmount()) == 0
                && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalizedAmount(), currency);
    }

    @Override
    public String toString() {
        return amount + " " + currency.getCurrencyCode();
    }
}
