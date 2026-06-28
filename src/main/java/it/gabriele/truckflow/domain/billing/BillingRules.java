package it.gabriele.truckflow.domain.billing;

import it.gabriele.truckflow.domain.shared.Money;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Regole di dominio per fatturazione e pagamenti.
 */
public final class BillingRules {

    private BillingRules() {
    }

    public static boolean canBeIssued(Invoice invoice) {
        validateInvoice(invoice);

        return invoice.getStatus() == InvoiceStatus.DRAFT;
    }

    public static boolean canBeMarkedPaid(Invoice invoice) {
        validateInvoice(invoice);

        return invoice.getStatus() == InvoiceStatus.ISSUED;
    }

    public static boolean canBeCancelled(Invoice invoice) {
        validateInvoice(invoice);

        return !invoice.getStatus().isTerminal();
    }

    public static boolean hasReceivableAmount(Invoice invoice) {
        validateInvoice(invoice);

        return invoice.canReceivePayment();
    }

    public static boolean isOverdue(Invoice invoice, LocalDate referenceDate) {
        validateInvoice(invoice);

        if (referenceDate == null) {
            throw new IllegalArgumentException("La data di riferimento è obbligatoria.");
        }

        return invoice.getStatus() == InvoiceStatus.ISSUED
                && referenceDate.isAfter(invoice.getDueDate());
    }

    public static boolean isPaymentMatchingInvoice(
            PaymentRecord paymentRecord,
            Invoice invoice
    ) {
        validatePaymentRecord(paymentRecord);
        validateInvoice(invoice);

        return paymentRecord.isForInvoice(invoice)
                && paymentRecord.getAmount().equals(invoice.calculateTotal());
    }

    public static boolean canRegisterPayment(
            PaymentRecord paymentRecord,
            Invoice invoice
    ) {
        validatePaymentRecord(paymentRecord);
        validateInvoice(invoice);

        return canBeMarkedPaid(invoice)
                && isPaymentMatchingInvoice(paymentRecord, invoice);
    }

    public static boolean paymentsCoverInvoice(
            List<PaymentRecord> paymentRecords,
            Invoice invoice
    ) {
        validatePaymentRecords(paymentRecords);
        validateInvoice(invoice);

        List<PaymentRecord> matchingPayments = paymentRecords.stream()
                .filter(paymentRecord -> paymentRecord.isForInvoice(invoice))
                .toList();

        if (matchingPayments.isEmpty()) {
            return false;
        }

        Money totalPaid = matchingPayments.get(0).getAmount();

        for (int i = 1; i < matchingPayments.size(); i++) {
            totalPaid = totalPaid.add(matchingPayments.get(i).getAmount());
        }

        return totalPaid.equals(invoice.calculateTotal());
    }

    private static void validateInvoice(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("La fattura è obbligatoria.");
        }
    }

    private static void validatePaymentRecord(PaymentRecord paymentRecord) {
        if (paymentRecord == null) {
            throw new IllegalArgumentException("Il pagamento è obbligatorio.");
        }
    }

    private static void validatePaymentRecords(List<PaymentRecord> paymentRecords) {
        if (paymentRecords == null) {
            throw new IllegalArgumentException("La lista pagamenti è obbligatoria.");
        }

        if (paymentRecords.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("La lista pagamenti non può contenere valori nulli.");
        }
    }
}
