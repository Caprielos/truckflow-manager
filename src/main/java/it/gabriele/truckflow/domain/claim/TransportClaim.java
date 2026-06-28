package it.gabriele.truckflow.domain.claim;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Rappresenta un reclamo collegato a una spedizione e a un cliente.
 */
public final class TransportClaim {

    private static final int MAX_CODE_LENGTH = 50;

    private final String claimNumber;
    private final String shipmentNumber;
    private final String customerCode;
    private final ClaimType type;
    private final ClaimSeverity severity;
    private final ClaimStatus status;
    private final Money requestedCompensation;
    private final Money acceptedCompensation;
    private final LocalDate openedDate;
    private final LocalDate closedDate;
    private final Notes notes;

    private TransportClaim(
            String claimNumber,
            String shipmentNumber,
            String customerCode,
            ClaimType type,
            ClaimSeverity severity,
            ClaimStatus status,
            Money requestedCompensation,
            Money acceptedCompensation,
            LocalDate openedDate,
            LocalDate closedDate,
            Notes notes
    ) {
        this.claimNumber = validateCode(claimNumber, "Il numero reclamo è obbligatorio.");
        this.shipmentNumber = validateCode(shipmentNumber, "Il numero spedizione del reclamo è obbligatorio.");
        this.customerCode = validateCode(customerCode, "Il codice cliente del reclamo è obbligatorio.");

        if (type == null) {
            throw new IllegalArgumentException("Il tipo reclamo è obbligatorio.");
        }

        if (severity == null) {
            throw new IllegalArgumentException("La gravità reclamo è obbligatoria.");
        }

        if (status == null) {
            throw new IllegalArgumentException("Lo stato reclamo è obbligatorio.");
        }

        if (requestedCompensation == null) {
            throw new IllegalArgumentException("L'importo richiesto del reclamo è obbligatorio.");
        }

        if (openedDate == null) {
            throw new IllegalArgumentException("La data apertura reclamo è obbligatoria.");
        }

        if (notes == null) {
            throw new IllegalArgumentException("Le note reclamo sono obbligatorie.");
        }

        validateCompensation(status, requestedCompensation, acceptedCompensation);
        validateClosedDate(status, openedDate, closedDate);

        this.type = type;
        this.severity = severity;
        this.status = status;
        this.requestedCompensation = requestedCompensation;
        this.acceptedCompensation = acceptedCompensation;
        this.openedDate = openedDate;
        this.closedDate = closedDate;
        this.notes = notes;
    }

    public static TransportClaim open(
            String claimNumber,
            String shipmentNumber,
            String customerCode,
            ClaimType type,
            ClaimSeverity severity,
            Money requestedCompensation,
            LocalDate openedDate,
            Notes notes
    ) {
        return new TransportClaim(
                claimNumber,
                shipmentNumber,
                customerCode,
                type,
                severity,
                ClaimStatus.OPEN,
                requestedCompensation,
                null,
                openedDate,
                null,
                notes
        );
    }

    private static String validateCode(String code, String nullMessage) {
        if (code == null) {
            throw new IllegalArgumentException(nullMessage);
        }

        String normalizedCode = code.trim().toUpperCase();

        if (normalizedCode.isEmpty()) {
            throw new IllegalArgumentException(nullMessage);
        }

        if (normalizedCode.length() > MAX_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
        }

        if (!normalizedCode.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedCode;
    }

    private static void validateCompensation(
            ClaimStatus status,
            Money requestedCompensation,
            Money acceptedCompensation
    ) {
        if (status == ClaimStatus.ACCEPTED || status == ClaimStatus.SETTLED) {
            if (acceptedCompensation == null) {
                throw new IllegalArgumentException("Un reclamo accettato o liquidato deve avere un importo accettato.");
            }

            try {
                requestedCompensation.subtract(acceptedCompensation);
            } catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException("L'importo accettato non può superare l'importo richiesto.", exception);
            }

            return;
        }

        if (acceptedCompensation != null) {
            throw new IllegalArgumentException("Un reclamo non accettato non può avere un importo accettato.");
        }
    }

    private static void validateClosedDate(
            ClaimStatus status,
            LocalDate openedDate,
            LocalDate closedDate
    ) {
        if (closedDate != null && closedDate.isBefore(openedDate)) {
            throw new IllegalArgumentException("La data chiusura reclamo non può essere precedente alla data apertura.");
        }

        if (status.isTerminal() && closedDate == null) {
            throw new IllegalArgumentException("Un reclamo terminale deve avere una data chiusura.");
        }

        if (!status.isTerminal() && closedDate != null) {
            throw new IllegalArgumentException("Un reclamo non terminale non può avere una data chiusura.");
        }
    }

    public TransportClaim startReview() {
        if (!ClaimRules.canBeReviewed(this)) {
            throw new IllegalStateException("Il reclamo non può passare in revisione.");
        }

        return new TransportClaim(
                claimNumber,
                shipmentNumber,
                customerCode,
                type,
                severity,
                ClaimStatus.UNDER_REVIEW,
                requestedCompensation,
                null,
                openedDate,
                null,
                notes
        );
    }

    public TransportClaim accept(Money acceptedCompensation) {
        if (!ClaimRules.canBeAccepted(this)) {
            throw new IllegalStateException("Il reclamo non può essere accettato.");
        }

        return new TransportClaim(
                claimNumber,
                shipmentNumber,
                customerCode,
                type,
                severity,
                ClaimStatus.ACCEPTED,
                requestedCompensation,
                acceptedCompensation,
                openedDate,
                null,
                notes
        );
    }

    public TransportClaim settle(LocalDate closedDate) {
        if (!ClaimRules.canBeSettled(this)) {
            throw new IllegalStateException("Il reclamo non può essere liquidato.");
        }

        return new TransportClaim(
                claimNumber,
                shipmentNumber,
                customerCode,
                type,
                severity,
                ClaimStatus.SETTLED,
                requestedCompensation,
                acceptedCompensation,
                openedDate,
                closedDate,
                notes
        );
    }

    public TransportClaim reject(LocalDate closedDate) {
        if (!ClaimRules.canBeRejected(this)) {
            throw new IllegalStateException("Il reclamo non può essere rifiutato.");
        }

        return new TransportClaim(
                claimNumber,
                shipmentNumber,
                customerCode,
                type,
                severity,
                ClaimStatus.REJECTED,
                requestedCompensation,
                null,
                openedDate,
                closedDate,
                notes
        );
    }

    public TransportClaim cancel(LocalDate closedDate) {
        if (!ClaimRules.canBeCancelled(this)) {
            throw new IllegalStateException("Il reclamo non può essere cancellato.");
        }

        return new TransportClaim(
                claimNumber,
                shipmentNumber,
                customerCode,
                type,
                severity,
                ClaimStatus.CANCELLED,
                requestedCompensation,
                null,
                openedDate,
                closedDate,
                notes
        );
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public ClaimType getType() {
        return type;
    }

    public ClaimSeverity getSeverity() {
        return severity;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public Money getRequestedCompensation() {
        return requestedCompensation;
    }

    public Money getAcceptedCompensation() {
        return acceptedCompensation;
    }

    public LocalDate getOpenedDate() {
        return openedDate;
    }

    public LocalDate getClosedDate() {
        return closedDate;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean isOpen() {
        return status == ClaimStatus.OPEN;
    }

    public boolean isUnderReview() {
        return status == ClaimStatus.UNDER_REVIEW;
    }

    public boolean isAccepted() {
        return status == ClaimStatus.ACCEPTED;
    }

    public boolean isSettled() {
        return status == ClaimStatus.SETTLED;
    }

    public boolean isRejected() {
        return status == ClaimStatus.REJECTED;
    }

    public boolean isCancelled() {
        return status == ClaimStatus.CANCELLED;
    }

    public boolean isTerminal() {
        return status.isTerminal();
    }

    public boolean hasAcceptedCompensation() {
        return acceptedCompensation != null;
    }

    public boolean hasClosedDate() {
        return closedDate != null;
    }

    public boolean hasNotes() {
        return notes.hasText();
    }

    public boolean isCargoRelated() {
        return type.isCargoRelated();
    }

    public boolean isTimeRelated() {
        return type.isTimeRelated();
    }

    public boolean isFinancialDispute() {
        return type.isFinancialDispute();
    }

    public String formatSingleLine() {
        return claimNumber
                + " - shipment: " + shipmentNumber
                + " - customer: " + customerCode
                + " - " + type
                + " - " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransportClaim that)) return false;
        return claimNumber.equals(that.claimNumber)
                && shipmentNumber.equals(that.shipmentNumber)
                && customerCode.equals(that.customerCode)
                && type == that.type
                && severity == that.severity
                && status == that.status
                && requestedCompensation.equals(that.requestedCompensation)
                && Objects.equals(acceptedCompensation, that.acceptedCompensation)
                && openedDate.equals(that.openedDate)
                && Objects.equals(closedDate, that.closedDate)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                claimNumber,
                shipmentNumber,
                customerCode,
                type,
                severity,
                status,
                requestedCompensation,
                acceptedCompensation,
                openedDate,
                closedDate,
                notes
        );
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
