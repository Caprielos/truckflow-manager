package it.gabriele.truckflow.domain.billing;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.pricing.PriceBreakdown;
import it.gabriele.truckflow.domain.pricing.PricingLine;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Testa BillingRules. */
class BillingRulesTest {

  @Test
  void shouldCheckInvoiceLifecycleRules() {
    Invoice draft = draftInvoice();
    Invoice issued = issuedInvoice();
    Invoice paid = issued.markPaid();
    Invoice cancelled = issued.cancel();

    assertTrue(BillingRules.canBeIssued(draft));
    assertFalse(BillingRules.canBeIssued(issued));

    assertFalse(BillingRules.canBeMarkedPaid(draft));
    assertTrue(BillingRules.canBeMarkedPaid(issued));
    assertFalse(BillingRules.canBeMarkedPaid(paid));

    assertTrue(BillingRules.canBeCancelled(draft));
    assertTrue(BillingRules.canBeCancelled(issued));
    assertFalse(BillingRules.canBeCancelled(paid));
    assertFalse(BillingRules.canBeCancelled(cancelled));
  }

  @Test
  void shouldCheckReceivableAmount() {
    assertFalse(BillingRules.hasReceivableAmount(draftInvoice()));
    assertTrue(BillingRules.hasReceivableAmount(issuedInvoice()));
    assertFalse(BillingRules.hasReceivableAmount(issuedInvoice().markPaid()));
  }

  @Test
  void shouldDetectOverdueInvoice() {
    Invoice issued = issuedInvoice();

    assertFalse(BillingRules.isOverdue(issued, LocalDate.of(2026, 7, 31)));
    assertTrue(BillingRules.isOverdue(issued, LocalDate.of(2026, 8, 1)));
    assertFalse(BillingRules.isOverdue(issued.markPaid(), LocalDate.of(2026, 8, 1)));
  }

  @Test
  void shouldCheckPaymentMatchingInvoice() {
    assertTrue(BillingRules.isPaymentMatchingInvoice(fullPayment(), issuedInvoice()));

    assertFalse(BillingRules.isPaymentMatchingInvoice(partialPayment(), issuedInvoice()));

    assertFalse(
        BillingRules.isPaymentMatchingInvoice(paymentForDifferentInvoice(), issuedInvoice()));
  }

  @Test
  void shouldCheckIfPaymentCanBeRegistered() {
    assertTrue(BillingRules.canRegisterPayment(fullPayment(), issuedInvoice()));

    assertFalse(BillingRules.canRegisterPayment(fullPayment(), draftInvoice()));

    assertFalse(BillingRules.canRegisterPayment(partialPayment(), issuedInvoice()));
  }

  @Test
  void shouldCheckIfPaymentsCoverInvoice() {
    List<PaymentRecord> payments =
        List.of(
            PaymentRecord.bankTransfer(
                "PAY-001",
                "INV-001",
                Money.of("400.00", "EUR"),
                LocalDate.of(2026, 7, 10),
                Notes.empty()),
            PaymentRecord.bankTransfer(
                "PAY-002",
                "INV-001",
                Money.of("600.00", "EUR"),
                LocalDate.of(2026, 7, 11),
                Notes.empty()));

    assertTrue(BillingRules.paymentsCoverInvoice(payments, issuedInvoice()));
    assertFalse(BillingRules.paymentsCoverInvoice(List.of(partialPayment()), issuedInvoice()));
    assertFalse(
        BillingRules.paymentsCoverInvoice(List.of(paymentForDifferentInvoice()), issuedInvoice()));
  }

  @Test
  void shouldNotAllowNullValues() {
    Invoice invoice = issuedInvoice();
    PaymentRecord payment = fullPayment();

    assertThrows(IllegalArgumentException.class, () -> BillingRules.canBeIssued(null));
    assertThrows(IllegalArgumentException.class, () -> BillingRules.canBeMarkedPaid(null));
    assertThrows(IllegalArgumentException.class, () -> BillingRules.canBeCancelled(null));
    assertThrows(IllegalArgumentException.class, () -> BillingRules.hasReceivableAmount(null));
    assertThrows(
        IllegalArgumentException.class,
        () -> BillingRules.isOverdue(null, LocalDate.of(2026, 8, 1)));
    assertThrows(IllegalArgumentException.class, () -> BillingRules.isOverdue(invoice, null));
    assertThrows(
        IllegalArgumentException.class, () -> BillingRules.isPaymentMatchingInvoice(null, invoice));
    assertThrows(
        IllegalArgumentException.class, () -> BillingRules.isPaymentMatchingInvoice(payment, null));
    assertThrows(
        IllegalArgumentException.class, () -> BillingRules.canRegisterPayment(null, invoice));
    assertThrows(
        IllegalArgumentException.class, () -> BillingRules.canRegisterPayment(payment, null));
    assertThrows(
        IllegalArgumentException.class, () -> BillingRules.paymentsCoverInvoice(null, invoice));
    assertThrows(
        IllegalArgumentException.class,
        () -> BillingRules.paymentsCoverInvoice(List.of(payment), null));
  }

  @Test
  void shouldNotAllowNullPaymentsInsideList() {
    List<PaymentRecord> paymentsWithNull = Arrays.asList(fullPayment(), null);

    assertThrows(
        IllegalArgumentException.class,
        () -> BillingRules.paymentsCoverInvoice(paymentsWithNull, issuedInvoice()));
  }

  private static Invoice draftInvoice() {
    return Invoice.draft(
        "INV-001",
        "CUST-001",
        "SHP-001",
        priceBreakdown(),
        LocalDate.of(2026, 7, 1),
        LocalDate.of(2026, 7, 31),
        Notes.empty());
  }

  private static Invoice issuedInvoice() {
    return Invoice.issued(
        "INV-001",
        "CUST-001",
        "SHP-001",
        priceBreakdown(),
        LocalDate.of(2026, 7, 1),
        LocalDate.of(2026, 7, 31),
        Notes.empty());
  }

  private static PaymentRecord fullPayment() {
    return PaymentRecord.bankTransfer(
        "PAY-001", "INV-001", Money.of("1000.00", "EUR"), LocalDate.of(2026, 7, 10), Notes.empty());
  }

  private static PaymentRecord partialPayment() {
    return PaymentRecord.bankTransfer(
        "PAY-002", "INV-001", Money.of("500.00", "EUR"), LocalDate.of(2026, 7, 10), Notes.empty());
  }

  private static PaymentRecord paymentForDifferentInvoice() {
    return PaymentRecord.bankTransfer(
        "PAY-003", "INV-999", Money.of("1000.00", "EUR"), LocalDate.of(2026, 7, 10), Notes.empty());
  }

  private static PriceBreakdown priceBreakdown() {
    return PriceBreakdown.of(
        "QUOTE-001",
        PricingLine.baseFreight(
            "LINE-001", "Trasporto base", Money.of("1000.00", "EUR"), Notes.empty()));
  }
}
