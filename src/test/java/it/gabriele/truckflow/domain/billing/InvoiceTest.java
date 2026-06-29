package it.gabriele.truckflow.domain.billing;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.pricing.PriceBreakdown;
import it.gabriele.truckflow.domain.pricing.PricingLine;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/** Testa Invoice. */
class InvoiceTest {

  @Test
  void shouldCreateDraftInvoice() {
    Invoice invoice = draftInvoice();

    assertEquals("INV-001", invoice.getInvoiceNumber());
    assertEquals("CUST-001", invoice.getCustomerCode());
    assertEquals("SHP-001", invoice.getShipmentNumber());
    assertEquals(priceBreakdown(), invoice.getPriceBreakdown());
    assertEquals(issueDate(), invoice.getIssueDate());
    assertEquals(dueDate(), invoice.getDueDate());
    assertEquals(InvoiceStatus.DRAFT, invoice.getStatus());
    assertEquals(Money.of("1000.00", "EUR"), invoice.calculateTotal());
    assertTrue(invoice.isDraft());
    assertFalse(invoice.canReceivePayment());
  }

  @Test
  void shouldCreateIssuedInvoice() {
    Invoice invoice =
        Invoice.issued(
            "INV-001",
            "CUST-001",
            "SHP-001",
            priceBreakdown(),
            issueDate(),
            dueDate(),
            Notes.empty());

    assertTrue(invoice.isIssued());
    assertTrue(invoice.canReceivePayment());
  }

  @Test
  void shouldNormalizeCodes() {
    Invoice invoice =
        Invoice.draft(
            "  inv_001  ",
            "  cust_001  ",
            "  shp_001  ",
            priceBreakdown(),
            issueDate(),
            dueDate(),
            Notes.empty());

    assertEquals("INV_001", invoice.getInvoiceNumber());
    assertEquals("CUST_001", invoice.getCustomerCode());
    assertEquals("SHP_001", invoice.getShipmentNumber());
  }

  @Test
  void shouldRejectInvalidCodes() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Invoice.draft(
                null,
                "CUST-001",
                "SHP-001",
                priceBreakdown(),
                issueDate(),
                dueDate(),
                Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            Invoice.draft(
                "INV 001",
                "CUST-001",
                "SHP-001",
                priceBreakdown(),
                issueDate(),
                dueDate(),
                Notes.empty()));
  }

  @Test
  void shouldRejectNullMandatoryFields() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Invoice.draft(
                "INV-001", "CUST-001", "SHP-001", null, issueDate(), dueDate(), Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            Invoice.draft(
                "INV-001",
                "CUST-001",
                "SHP-001",
                priceBreakdown(),
                null,
                dueDate(),
                Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            Invoice.draft(
                "INV-001",
                "CUST-001",
                "SHP-001",
                priceBreakdown(),
                issueDate(),
                null,
                Notes.empty()));

    assertThrows(
        IllegalArgumentException.class,
        () ->
            Invoice.draft(
                "INV-001", "CUST-001", "SHP-001", priceBreakdown(), issueDate(), dueDate(), null));
  }

  @Test
  void shouldRejectDueDateBeforeIssueDate() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            Invoice.draft(
                "INV-001",
                "CUST-001",
                "SHP-001",
                priceBreakdown(),
                LocalDate.of(2026, 7, 31),
                LocalDate.of(2026, 7, 1),
                Notes.empty()));
  }

  @Test
  void shouldMoveThroughInvoiceLifecycle() {
    Invoice draft = draftInvoice();
    Invoice issued = draft.issue();
    Invoice paid = issued.markPaid();

    assertTrue(issued.isIssued());
    assertTrue(paid.isPaid());
    assertTrue(paid.isTerminal());
  }

  @Test
  void shouldNotAllowInvalidLifecycleTransitions() {
    Invoice draft = draftInvoice();

    assertThrows(IllegalStateException.class, draft::markPaid);

    Invoice issued = draft.issue();

    assertThrows(IllegalStateException.class, issued::issue);

    Invoice paid = issued.markPaid();

    assertThrows(IllegalStateException.class, paid::issue);
    assertThrows(IllegalStateException.class, paid::markPaid);
    assertThrows(IllegalStateException.class, paid::cancel);
  }

  @Test
  void shouldCancelNonTerminalInvoice() {
    Invoice draft = draftInvoice();
    Invoice issued = draft.issue();

    assertTrue(draft.cancel().isCancelled());
    assertTrue(issued.cancel().isCancelled());
  }

  @Test
  void shouldDetectNotes() {
    Invoice invoice =
        Invoice.draft(
            "INV-001",
            "CUST-001",
            "SHP-001",
            priceBreakdown(),
            issueDate(),
            dueDate(),
            Notes.of("Pagamento a 30 giorni"));

    assertTrue(invoice.hasNotes());
  }

  @Test
  void shouldFormatSingleLine() {
    assertEquals(
        "INV-001 - customer: CUST-001 - shipment: SHP-001 - total: 1000.00 EUR - DRAFT",
        draftInvoice().formatSingleLine());
  }

  @Test
  void shouldConsiderEquivalentInvoicesEqual() {
    Invoice first = draftInvoice();
    Invoice second = draftInvoice();

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }

  @Test
  void shouldExposeEnumDetails() {
    assertFalse(InvoiceStatus.DRAFT.isTerminal());
    assertFalse(InvoiceStatus.DRAFT.canReceivePayment());
    assertTrue(InvoiceStatus.ISSUED.canReceivePayment());
    assertTrue(InvoiceStatus.PAID.isTerminal());
    assertTrue(InvoiceStatus.CANCELLED.isTerminal());
  }

  private static Invoice draftInvoice() {
    return Invoice.draft(
        "INV-001", "CUST-001", "SHP-001", priceBreakdown(), issueDate(), dueDate(), Notes.empty());
  }

  private static PriceBreakdown priceBreakdown() {
    return PriceBreakdown.of(
        "QUOTE-001",
        PricingLine.baseFreight(
            "LINE-001", "Trasporto base", Money.of("1000.00", "EUR"), Notes.empty()));
  }

  private static LocalDate issueDate() {
    return LocalDate.of(2026, 7, 1);
  }

  private static LocalDate dueDate() {
    return LocalDate.of(2026, 7, 31);
  }
}
