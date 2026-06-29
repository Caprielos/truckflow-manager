package it.gabriele.truckflow.domain.economics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.shared.Money;
import org.junit.jupiter.api.Test;

class VatBreakdownTest {

  @Test
  void shouldCalculateItalianVatFromNetAmountAndKeepVatSeparateFromCost() {
    VatBreakdown vat =
        VatBreakdown.taxableFromNet(Money.of("100000", "EUR"), VatRate.italianStandard22());

    assertEquals(Money.of("100000", "EUR"), vat.getNetAmount());
    assertEquals(Money.of("22000.00", "EUR"), vat.getVatAmount());
    assertEquals(Money.of("122000.00", "EUR"), vat.getGrossAmount());
    assertEquals(Money.of("22000.00", "EUR"), vat.calculateRecoverableVatAmount());
    assertEquals(Money.of("100000.00", "EUR"), vat.calculateAccountingCost());
    assertTrue(vat.hasRecoverableVat());
  }

  @Test
  void shouldTreatNonDeductibleVatAsRealCost() {
    VatBreakdown vat =
        VatBreakdown.nonDeductibleFromNet(Money.of("1000", "EUR"), VatRate.italianStandard22());

    assertEquals(Money.of("220.00", "EUR"), vat.getVatAmount());
    assertEquals(Money.of("1220.00", "EUR"), vat.getGrossAmount());
    assertEquals(Money.of("0.00", "EUR"), vat.calculateRecoverableVatAmount());
    assertEquals(Money.of("1220.00", "EUR"), vat.calculateAccountingCost());
  }
}
