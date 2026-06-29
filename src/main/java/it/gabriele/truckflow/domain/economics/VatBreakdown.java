package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Percentage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

/**
 * Scomposizione di un importo tra imponibile, IVA, totale lordo e IVA recuperabile. Per gli
 * acquisti aziendali il costo contabile è imponibile + IVA non recuperabile.
 */
public final class VatBreakdown {

  private static final Percentage FULLY_RECOVERABLE = Percentage.of("100");
  private static final Percentage NOT_RECOVERABLE = Percentage.of("0");

  private final Money netAmount;
  private final VatRate vatRate;
  private final VatTreatment treatment;
  private final Money vatAmount;
  private final Money grossAmount;
  private final Percentage recoverableVatPercentage;

  private VatBreakdown(
      Money netAmount,
      VatRate vatRate,
      VatTreatment treatment,
      Money vatAmount,
      Money grossAmount,
      Percentage recoverableVatPercentage) {
    if (netAmount == null) {
      throw new IllegalArgumentException("L'imponibile è obbligatorio.");
    }
    if (vatRate == null) {
      throw new IllegalArgumentException("L'aliquota IVA è obbligatoria.");
    }
    if (treatment == null) {
      throw new IllegalArgumentException("Il trattamento IVA è obbligatorio.");
    }
    if (vatAmount == null) {
      throw new IllegalArgumentException("L'importo IVA è obbligatorio.");
    }
    if (grossAmount == null) {
      throw new IllegalArgumentException("Il lordo IVA è obbligatorio.");
    }
    if (recoverableVatPercentage == null) {
      throw new IllegalArgumentException("La percentuale IVA recuperabile è obbligatoria.");
    }
    netAmount.add(vatAmount);
    netAmount.add(grossAmount);
    if (!netAmount.add(vatAmount).equals(grossAmount)) {
      throw new IllegalArgumentException("Il lordo deve essere uguale a imponibile più IVA.");
    }
    if (!treatment.generatesVatAmount() && vatAmount.getAmount().signum() > 0) {
      throw new IllegalArgumentException(
          "Un trattamento IVA non imponibile/esente/non soggetto non può avere IVA calcolata.");
    }
    this.netAmount = netAmount;
    this.vatRate = vatRate;
    this.treatment = treatment;
    this.vatAmount = vatAmount;
    this.grossAmount = grossAmount;
    this.recoverableVatPercentage = recoverableVatPercentage;
  }

  public static VatBreakdown taxableFromNet(Money netAmount, VatRate vatRate) {
    return taxableFromNet(netAmount, vatRate, FULLY_RECOVERABLE);
  }

  public static VatBreakdown taxableFromNet(
      Money netAmount, VatRate vatRate, Percentage recoverableVatPercentage) {
    validateNetAndRate(netAmount, vatRate);
    Money vat = calculateVatFromNet(netAmount, vatRate);
    return new VatBreakdown(
        netAmount,
        vatRate,
        VatTreatment.TAXABLE,
        vat,
        netAmount.add(vat),
        recoverableVatPercentage);
  }

  public static VatBreakdown taxableFromGross(Money grossAmount, VatRate vatRate) {
    return taxableFromGross(grossAmount, vatRate, FULLY_RECOVERABLE);
  }

  public static VatBreakdown taxableFromGross(
      Money grossAmount, VatRate vatRate, Percentage recoverableVatPercentage) {
    if (grossAmount == null) {
      throw new IllegalArgumentException("Il lordo IVA è obbligatorio.");
    }
    if (vatRate == null) {
      throw new IllegalArgumentException("L'aliquota IVA è obbligatoria.");
    }
    BigDecimal divisor = BigDecimal.ONE.add(vatRate.toMultiplier());
    BigDecimal netValue = grossAmount.getAmount().divide(divisor, 2, RoundingMode.HALF_UP);
    Money net = Money.of(netValue, grossAmount.getCurrency());
    Money vat = grossAmount.subtract(net);
    return new VatBreakdown(
        net, vatRate, VatTreatment.TAXABLE, vat, grossAmount, recoverableVatPercentage);
  }

  public static VatBreakdown exempt(Money amount, String vatCode) {
    return withoutVat(amount, VatRate.zero(vatCode), VatTreatment.EXEMPT);
  }

  public static VatBreakdown reverseCharge(Money amount, VatRate vatRate) {
    return withoutVat(amount, vatRate, VatTreatment.REVERSE_CHARGE);
  }

  public static VatBreakdown outOfScope(Money amount, String vatCode) {
    return withoutVat(amount, VatRate.zero(vatCode), VatTreatment.OUT_OF_SCOPE);
  }

  public static VatBreakdown noVatKnown(Money grossAmount) {
    return withoutVat(grossAmount, VatRate.zero("NO_VAT_DETAIL"), VatTreatment.OUT_OF_SCOPE);
  }

  public static VatBreakdown nonDeductibleFromNet(Money netAmount, VatRate vatRate) {
    validateNetAndRate(netAmount, vatRate);
    Money vat = calculateVatFromNet(netAmount, vatRate);
    return new VatBreakdown(
        netAmount, vatRate, VatTreatment.NOT_DEDUCTIBLE, vat, netAmount.add(vat), NOT_RECOVERABLE);
  }

  private static VatBreakdown withoutVat(Money amount, VatRate vatRate, VatTreatment treatment) {
    if (amount == null) {
      throw new IllegalArgumentException("L'importo senza IVA è obbligatorio.");
    }
    if (vatRate == null) {
      throw new IllegalArgumentException("L'aliquota IVA è obbligatoria.");
    }
    return new VatBreakdown(
        amount,
        vatRate,
        treatment,
        Money.of(BigDecimal.ZERO, amount.getCurrency()),
        amount,
        NOT_RECOVERABLE);
  }

  private static void validateNetAndRate(Money netAmount, VatRate vatRate) {
    if (netAmount == null) {
      throw new IllegalArgumentException("L'imponibile è obbligatorio.");
    }
    if (vatRate == null) {
      throw new IllegalArgumentException("L'aliquota IVA è obbligatoria.");
    }
  }

  private static Money calculateVatFromNet(Money netAmount, VatRate vatRate) {
    BigDecimal value =
        netAmount.getAmount().multiply(vatRate.toMultiplier()).setScale(2, RoundingMode.HALF_UP);
    return Money.of(value, netAmount.getCurrency());
  }

  public Money getNetAmount() {
    return netAmount;
  }

  public VatRate getVatRate() {
    return vatRate;
  }

  public VatTreatment getTreatment() {
    return treatment;
  }

  public Money getVatAmount() {
    return vatAmount;
  }

  public Money getGrossAmount() {
    return grossAmount;
  }

  public Percentage getRecoverableVatPercentage() {
    return recoverableVatPercentage;
  }

  public Money calculateRecoverableVatAmount() {
    BigDecimal value =
        vatAmount
            .getAmount()
            .multiply(recoverableVatPercentage.toMultiplier())
            .setScale(2, RoundingMode.HALF_UP);
    return Money.of(value, vatAmount.getCurrency());
  }

  public Money calculateNonRecoverableVatAmount() {
    return vatAmount.subtract(calculateRecoverableVatAmount());
  }

  public Money calculateAccountingCost() {
    return netAmount.add(calculateNonRecoverableVatAmount());
  }

  public boolean hasRecoverableVat() {
    return calculateRecoverableVatAmount().getAmount().signum() > 0;
  }

  public boolean sameCurrencyAs(VatBreakdown other) {
    if (other == null) {
      throw new IllegalArgumentException("Il breakdown IVA da confrontare è obbligatorio.");
    }
    Currency currency = netAmount.getCurrency();
    return currency.equals(other.netAmount.getCurrency());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof VatBreakdown that)) return false;
    return netAmount.equals(that.netAmount)
        && vatRate.equals(that.vatRate)
        && treatment == that.treatment
        && vatAmount.equals(that.vatAmount)
        && grossAmount.equals(that.grossAmount)
        && recoverableVatPercentage.equals(that.recoverableVatPercentage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        netAmount, vatRate, treatment, vatAmount, grossAmount, recoverableVatPercentage);
  }
}
