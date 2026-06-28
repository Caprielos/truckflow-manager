package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

/**
 * Rappresenta un saldo economico che può essere positivo, zero o negativo.
 * Money nel dominio non può essere negativo, quindi per utile/perdita/debito serve un value object dedicato.
 */
public final class FinancialBalance {

    private final BigDecimal amount;
    private final Currency currency;

    private FinancialBalance(BigDecimal amount, Currency currency) {
        if (amount == null) {
            throw new IllegalArgumentException("L'importo saldo è obbligatorio.");
        }
        if (currency == null) {
            throw new IllegalArgumentException("La valuta saldo è obbligatoria.");
        }
        this.amount = amount;
        this.currency = currency;
    }

    public static FinancialBalance of(BigDecimal amount, Currency currency) {
        return new FinancialBalance(amount, currency);
    }

    public static FinancialBalance zero(Currency currency) {
        return new FinancialBalance(BigDecimal.ZERO, currency);
    }

    public static FinancialBalance from(Money money) {
        if (money == null) {
            throw new IllegalArgumentException("L'importo è obbligatorio.");
        }
        return new FinancialBalance(money.getAmount(), money.getCurrency());
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

    public boolean isPositive() {
        return amount.signum() > 0;
    }

    public boolean isZero() {
        return amount.signum() == 0;
    }

    public boolean isNegative() {
        return amount.signum() < 0;
    }

    public FinancialBalance add(Money money) {
        validateSameCurrency(money);
        return new FinancialBalance(amount.add(money.getAmount()), currency);
    }

    public FinancialBalance subtract(Money money) {
        validateSameCurrency(money);
        return new FinancialBalance(amount.subtract(money.getAmount()), currency);
    }

    public FinancialBalance add(FinancialBalance other) {
        validateSameCurrency(other);
        return new FinancialBalance(amount.add(other.amount), currency);
    }

    public FinancialBalance subtract(FinancialBalance other) {
        validateSameCurrency(other);
        return new FinancialBalance(amount.subtract(other.amount), currency);
    }

    public Money absoluteMoney() {
        return Money.of(amount.abs(), currency);
    }

    private void validateSameCurrency(Money money) {
        if (money == null) {
            throw new IllegalArgumentException("L'importo da usare nel saldo è obbligatorio.");
        }
        if (!currency.equals(money.getCurrency())) {
            throw new IllegalArgumentException("Il saldo e l'importo devono avere la stessa valuta.");
        }
    }

    private void validateSameCurrency(FinancialBalance other) {
        if (other == null) {
            throw new IllegalArgumentException("Il saldo da confrontare è obbligatorio.");
        }
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("I saldi devono avere la stessa valuta.");
        }
    }

    private BigDecimal normalizedAmount() {
        return amount.stripTrailingZeros();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinancialBalance that)) return false;
        return normalizedAmount().compareTo(that.normalizedAmount()) == 0
                && currency.equals(that.currency);
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
