package it.gabriele.truckflow.domain.claim;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa ClaimRules.
 */
class ClaimRulesTest {

    @Test
    void shouldCheckLifecycleRules() {
        TransportClaim open = openClaim();
        TransportClaim underReview = open.startReview();
        TransportClaim accepted = underReview.accept(Money.of("800.00", "EUR"));
        TransportClaim settled = accepted.settle(closedDate());
        TransportClaim rejected = openClaim().startReview().reject(closedDate());

        assertTrue(ClaimRules.canBeReviewed(open));
        assertFalse(ClaimRules.canBeReviewed(underReview));

        assertTrue(ClaimRules.canBeAccepted(underReview));
        assertFalse(ClaimRules.canBeAccepted(open));

        assertTrue(ClaimRules.canBeRejected(underReview));
        assertFalse(ClaimRules.canBeRejected(open));

        assertTrue(ClaimRules.canBeSettled(accepted));
        assertFalse(ClaimRules.canBeSettled(underReview));

        assertTrue(ClaimRules.canBeCancelled(open));
        assertTrue(ClaimRules.canBeCancelled(accepted));
        assertFalse(ClaimRules.canBeCancelled(settled));

        assertFalse(ClaimRules.isOpenForAction(settled));
        assertTrue(ClaimRules.isResolved(settled));
        assertTrue(ClaimRules.isResolved(rejected));
    }

    @Test
    void shouldRequireUrgentReviewForHighSeverityClaims() {
        assertTrue(ClaimRules.requiresUrgentReview(openClaim()));

        TransportClaim lowSeverityClaim = TransportClaim.open(
                "CLM-002",
                "SHP-001",
                "CUST-001",
                ClaimType.DOCUMENT_DISPUTE,
                ClaimSeverity.LOW,
                Money.of("100.00", "EUR"),
                openedDate(),
                Notes.empty()
        );

        assertFalse(ClaimRules.requiresUrgentReview(lowSeverityClaim));
    }

    @Test
    void shouldRequireUrgentReviewForTemperatureExcursionFromMediumSeverity() {
        TransportClaim temperatureClaim = TransportClaim.open(
                "CLM-003",
                "SHP-001",
                "CUST-001",
                ClaimType.TEMPERATURE_EXCURSION,
                ClaimSeverity.MEDIUM,
                Money.of("700.00", "EUR"),
                openedDate(),
                Notes.empty()
        );

        assertTrue(ClaimRules.requiresUrgentReview(temperatureClaim));
    }

    @Test
    void shouldCheckAcceptedCompensationWithinRequestedAmount() {
        TransportClaim accepted = openClaim()
                .startReview()
                .accept(Money.of("800.00", "EUR"));

        assertTrue(ClaimRules.isAcceptedCompensationWithinRequestedAmount(accepted));
        assertFalse(ClaimRules.isAcceptedCompensationWithinRequestedAmount(openClaim()));
    }

    @Test
    void shouldNotAllowNullClaim() {
        assertThrows(IllegalArgumentException.class, () -> ClaimRules.canBeReviewed(null));
        assertThrows(IllegalArgumentException.class, () -> ClaimRules.canBeAccepted(null));
        assertThrows(IllegalArgumentException.class, () -> ClaimRules.canBeRejected(null));
        assertThrows(IllegalArgumentException.class, () -> ClaimRules.canBeSettled(null));
        assertThrows(IllegalArgumentException.class, () -> ClaimRules.canBeCancelled(null));
        assertThrows(IllegalArgumentException.class, () -> ClaimRules.isOpenForAction(null));
        assertThrows(IllegalArgumentException.class, () -> ClaimRules.isResolved(null));
        assertThrows(IllegalArgumentException.class, () -> ClaimRules.requiresUrgentReview(null));
        assertThrows(IllegalArgumentException.class, () -> ClaimRules.isAcceptedCompensationWithinRequestedAmount(null));
    }

    @Test
    void shouldExposeEnumDetails() {
        assertTrue(ClaimType.CARGO_DAMAGE.isCargoRelated());
        assertTrue(ClaimType.DELAY.isTimeRelated());
        assertTrue(ClaimType.DOCUMENT_DISPUTE.isDocumentRelated());
        assertTrue(ClaimType.BILLING_DISPUTE.isFinancialDispute());

        assertFalse(ClaimStatus.OPEN.isTerminal());
        assertFalse(ClaimStatus.ACCEPTED.isTerminal());
        assertTrue(ClaimStatus.SETTLED.isTerminal());
        assertTrue(ClaimStatus.REJECTED.isTerminal());
        assertTrue(ClaimStatus.CANCELLED.isTerminal());
    }

    private static TransportClaim openClaim() {
        return TransportClaim.open(
                "CLM-001",
                "SHP-001",
                "CUST-001",
                ClaimType.CARGO_DAMAGE,
                ClaimSeverity.HIGH,
                Money.of("1200.00", "EUR"),
                openedDate(),
                Notes.empty()
        );
    }

    private static LocalDate openedDate() {
        return LocalDate.of(2026, 7, 1);
    }

    private static LocalDate closedDate() {
        return LocalDate.of(2026, 7, 10);
    }
}
