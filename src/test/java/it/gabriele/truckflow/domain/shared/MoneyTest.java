package it.gabriele.truckflow.domain.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Currency;
import org.junit.jupiter.api.Test;

/** Testa il Value Object Money. */
class MoneyTest {

  @Test
  void shouldCreateMoney() {
    Money money = Money.of("150.50", "EUR");

    assertEquals(new BigDecimal("150.50"), money.getAmount());
    assertEquals("EUR", money.getCurrencyCode());
  }

  @Test
  void shouldCreateMoneyUsingBigDecimalAndCurrency() {
    Money money = Money.of(new BigDecimal("99.99"), Currency.getInstance("EUR"));

    assertEquals(new BigDecimal("99.99"), money.getAmount());
    assertEquals(Currency.getInstance("EUR"), money.getCurrency());
  }

  @Test
  void shouldNotAllowNegativeAmount() {
    assertThrows(IllegalArgumentException.class, () -> Money.of("-1", "EUR"));
  }

  @Test
  void shouldNotAllowNullAmountOrCurrency() {
    assertThrows(IllegalArgumentException.class, () -> Money.of(null, Currency.getInstance("EUR")));
    assertThrows(IllegalArgumentException.class, () -> Money.of(new BigDecimal("10"), null));
  }

  @Test
  void shouldAddMoneyWithSameCurrency() {
    Money first = Money.of("100", "EUR");
    Money second = Money.of("50", "EUR");

    Money result = first.add(second);

    assertEquals(Money.of("150", "EUR"), result);
  }

  @Test
  void shouldNotAddMoneyWithDifferentCurrency() {
    Money euros = Money.of("100", "EUR");
    Money dollars = Money.of("50", "USD");

    assertThrows(IllegalArgumentException.class, () -> euros.add(dollars));
  }

  @Test
  void shouldSubtractMoneyWithSameCurrency() {
    Money first = Money.of("100", "EUR");
    Money second = Money.of("40", "EUR");

    Money result = first.subtract(second);

    assertEquals(Money.of("60", "EUR"), result);
  }

  @Test
  void shouldNotAllowNegativeSubtractionResult() {
    Money first = Money.of("40", "EUR");
    Money second = Money.of("100", "EUR");

    assertThrows(IllegalArgumentException.class, () -> first.subtract(second));
  }

  @Test
  void shouldCompareMoneyWithSameCurrency() {
    Money highAmount = Money.of("100", "EUR");
    Money lowAmount = Money.of("50", "EUR");

    assertTrue(highAmount.isGreaterThan(lowAmount));
    assertTrue(lowAmount.isLessThanOrEqualTo(highAmount));
  }

  @Test
  void shouldNotCompareMoneyWithDifferentCurrency() {
    Money euros = Money.of("100", "EUR");
    Money dollars = Money.of("100", "USD");

    assertThrows(IllegalArgumentException.class, () -> euros.isGreaterThan(dollars));
    assertThrows(IllegalArgumentException.class, () -> euros.isLessThanOrEqualTo(dollars));
  }

  @Test
  void shouldConsiderEquivalentAmountsEqual() {
    Money first = Money.of("10.0", "EUR");
    Money second = Money.of("10.00", "EUR");

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }
}
