package it.gabriele.truckflow.domain.billing;

import it.gabriele.truckflow.domain.pricing.PriceBreakdown;
import it.gabriele.truckflow.domain.pricing.PricingLine;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa PaymentRecord.
 */
class PaymentRecordTest {

    @Test
    void shouldCreatePaymentRecord() {
        PaymentRecord payment = paymentRecord();

        assertEquals("PAY-001", payment.getPaymentNumber());
        assertEquals("INV-001", payment.getInvoiceNumber());
        assertEquals(Money.of("1000.00", "EUR"), payment.getAmount());
        assertEquals(PaymentMethod.BANK_TRANSFER, payment.getMethod());
        assertEquals(LocalDate.of(2026, 7, 10), payment.getReceivedDate());
        assertTrue(payment.isElectronicPayment());
        assertFalse(payment.hasNotes());
    }

    @Test
    void shouldNormalizeCodes() {
        PaymentRecord payment = PaymentRecord.of(
                "  pay_001  ",
                "  inv_001  ",
                Money.of("1000.00", "EUR"),
                PaymentMethod.CARD,
                LocalDate.of(2026, 7, 10),
                Notes.empty()
        );

        assertEquals("PAY_001", payment.getPaymentNumber());
        assertEquals("INV_001", payment.getInvoiceNumber());
    }

    @Test
    void shouldRejectInvalidCodes() {
        assertThrows(IllegalArgumentException.class, () -> PaymentRecord.of(
                null,
                "INV-001",
                Money.of("1000.00", "EUR"),
                PaymentMethod.BANK_TRANSFER,
                LocalDate.of(2026, 7, 10),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> PaymentRecord.of(
                "PAY 001",
                "INV-001",
                Money.of("1000.00", "EUR"),
                PaymentMethod.BANK_TRANSFER,
                LocalDate.of(2026, 7, 10),
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> PaymentRecord.of(
                "PAY-001",
                "INV-001",
                null,
                PaymentMethod.BANK_TRANSFER,
                LocalDate.of(2026, 7, 10),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> PaymentRecord.of(
                "PAY-001",
                "INV-001",
                Money.of("1000.00", "EUR"),
                null,
                LocalDate.of(2026, 7, 10),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> PaymentRecord.of(
                "PAY-001",
                "INV-001",
                Money.of("1000.00", "EUR"),
                PaymentMethod.BANK_TRANSFER,
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> PaymentRecord.of(
                "PAY-001",
                "INV-001",
                Money.of("1000.00", "EUR"),
                PaymentMethod.BANK_TRANSFER,
                LocalDate.of(2026, 7, 10),
                null
        ));
    }

    @Test
    void shouldCheckInvoiceMatching() {
        PaymentRecord payment = paymentRecord();

        assertTrue(payment.isForInvoice(issuedInvoice()));
        assertTrue(payment.isForInvoiceNumber("inv-001"));
        assertFalse(payment.isForInvoiceNumber("INV-999"));

        assertThrows(IllegalArgumentException.class, () -> payment.isForInvoice(null));
        assertThrows(IllegalArgumentException.class, () -> payment.isForInvoiceNumber(null));
    }

    @Test
    void shouldDetectNotes() {
        PaymentRecord payment = PaymentRecord.bankTransfer(
                "PAY-001",
                "INV-001",
                Money.of("1000.00", "EUR"),
                LocalDate.of(2026, 7, 10),
                Notes.of("Bonifico ricevuto")
        );

        assertTrue(payment.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        assertEquals(
                "PAY-001 - invoice: INV-001 - amount: 1000.00 EUR - BANK_TRANSFER",
                paymentRecord().formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentPaymentsEqual() {
        PaymentRecord first = paymentRecord();
        PaymentRecord second = paymentRecord();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldExposePaymentMethodDetails() {
        assertTrue(PaymentMethod.BANK_TRANSFER.isElectronic());
        assertTrue(PaymentMethod.CARD.isElectronic());
        assertFalse(PaymentMethod.CASH.isElectronic());
        assertFalse(PaymentMethod.CREDIT_NOTE.isElectronic());
    }

    private static PaymentRecord paymentRecord() {
        return PaymentRecord.bankTransfer(
                "PAY-001",
                "INV-001",
                Money.of("1000.00", "EUR"),
                LocalDate.of(2026, 7, 10),
                Notes.empty()
        );
    }

    private static Invoice issuedInvoice() {
        return Invoice.issued(
                "INV-001",
                "CUST-001",
                "SHP-001",
                PriceBreakdown.of(
                        "QUOTE-001",
                        PricingLine.baseFreight(
                                "LINE-001",
                                "Trasporto base",
                                Money.of("1000.00", "EUR"),
                                Notes.empty()
                        )
                ),
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 7, 31),
                Notes.empty()
        );
    }
}
