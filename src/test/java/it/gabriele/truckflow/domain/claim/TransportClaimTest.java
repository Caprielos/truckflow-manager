package it.gabriele.truckflow.domain.claim;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa TransportClaim.
 */
class TransportClaimTest {

    @Test
    void shouldCreateOpenClaim() {
        TransportClaim claim = openClaim();

        assertEquals("CLM-001", claim.getClaimNumber());
        assertEquals("SHP-001", claim.getShipmentNumber());
        assertEquals("CUST-001", claim.getCustomerCode());
        assertEquals(ClaimType.CARGO_DAMAGE, claim.getType());
        assertEquals(ClaimSeverity.HIGH, claim.getSeverity());
        assertEquals(ClaimStatus.OPEN, claim.getStatus());
        assertEquals(Money.of("1200.00", "EUR"), claim.getRequestedCompensation());
        assertNull(claim.getAcceptedCompensation());
        assertEquals(openedDate(), claim.getOpenedDate());
        assertNull(claim.getClosedDate());
        assertTrue(claim.isOpen());
        assertFalse(claim.isTerminal());
        assertTrue(claim.isCargoRelated());
    }

    @Test
    void shouldNormalizeCodes() {
        TransportClaim claim = TransportClaim.open(
                "  clm_001  ",
                "  shp_001  ",
                "  cust_001  ",
                ClaimType.DELAY,
                ClaimSeverity.MEDIUM,
                Money.of("300.00", "EUR"),
                openedDate(),
                Notes.empty()
        );

        assertEquals("CLM_001", claim.getClaimNumber());
        assertEquals("SHP_001", claim.getShipmentNumber());
        assertEquals("CUST_001", claim.getCustomerCode());
    }

    @Test
    void shouldRejectInvalidCodes() {
        assertThrows(IllegalArgumentException.class, () -> TransportClaim.open(
                null,
                "SHP-001",
                "CUST-001",
                ClaimType.CARGO_DAMAGE,
                ClaimSeverity.HIGH,
                Money.of("1200.00", "EUR"),
                openedDate(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> TransportClaim.open(
                "CLM 001",
                "SHP-001",
                "CUST-001",
                ClaimType.CARGO_DAMAGE,
                ClaimSeverity.HIGH,
                Money.of("1200.00", "EUR"),
                openedDate(),
                Notes.empty()
        ));
    }

    @Test
    void shouldRejectNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> TransportClaim.open(
                "CLM-001",
                "SHP-001",
                "CUST-001",
                null,
                ClaimSeverity.HIGH,
                Money.of("1200.00", "EUR"),
                openedDate(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> TransportClaim.open(
                "CLM-001",
                "SHP-001",
                "CUST-001",
                ClaimType.CARGO_DAMAGE,
                null,
                Money.of("1200.00", "EUR"),
                openedDate(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> TransportClaim.open(
                "CLM-001",
                "SHP-001",
                "CUST-001",
                ClaimType.CARGO_DAMAGE,
                ClaimSeverity.HIGH,
                null,
                openedDate(),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> TransportClaim.open(
                "CLM-001",
                "SHP-001",
                "CUST-001",
                ClaimType.CARGO_DAMAGE,
                ClaimSeverity.HIGH,
                Money.of("1200.00", "EUR"),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> TransportClaim.open(
                "CLM-001",
                "SHP-001",
                "CUST-001",
                ClaimType.CARGO_DAMAGE,
                ClaimSeverity.HIGH,
                Money.of("1200.00", "EUR"),
                openedDate(),
                null
        ));
    }

    @Test
    void shouldMoveThroughAcceptedAndSettledLifecycle() {
        TransportClaim underReview = openClaim().startReview();
        TransportClaim accepted = underReview.accept(Money.of("800.00", "EUR"));
        TransportClaim settled = accepted.settle(closedDate());

        assertTrue(underReview.isUnderReview());
        assertTrue(accepted.isAccepted());
        assertTrue(accepted.hasAcceptedCompensation());
        assertEquals(Money.of("800.00", "EUR"), accepted.getAcceptedCompensation());

        assertTrue(settled.isSettled());
        assertTrue(settled.isTerminal());
        assertEquals(closedDate(), settled.getClosedDate());
    }

    @Test
    void shouldRejectClaimUnderReview() {
        TransportClaim rejected = openClaim()
                .startReview()
                .reject(closedDate());

        assertTrue(rejected.isRejected());
        assertTrue(rejected.isTerminal());
        assertTrue(rejected.hasClosedDate());
    }

    @Test
    void shouldCancelNonTerminalClaim() {
        TransportClaim open = openClaim();
        TransportClaim underReview = open.startReview();
        TransportClaim accepted = underReview.accept(Money.of("600.00", "EUR"));

        assertTrue(open.cancel(closedDate()).isCancelled());
        assertTrue(underReview.cancel(closedDate()).isCancelled());
        assertTrue(accepted.cancel(closedDate()).isCancelled());
    }

    @Test
    void shouldNotAllowInvalidLifecycleTransitions() {
        TransportClaim open = openClaim();

        assertThrows(IllegalStateException.class, () -> open.accept(Money.of("500.00", "EUR")));
        assertThrows(IllegalStateException.class, () -> open.reject(closedDate()));
        assertThrows(IllegalStateException.class, () -> open.settle(closedDate()));

        TransportClaim underReview = open.startReview();

        assertThrows(IllegalStateException.class, underReview::startReview);
        assertThrows(IllegalStateException.class, () -> underReview.settle(closedDate()));

        TransportClaim settled = underReview
                .accept(Money.of("800.00", "EUR"))
                .settle(closedDate());

        assertThrows(IllegalStateException.class, settled::startReview);
        assertThrows(IllegalStateException.class, () -> settled.accept(Money.of("500.00", "EUR")));
        assertThrows(IllegalStateException.class, () -> settled.cancel(closedDate()));
    }

    @Test
    void shouldRejectAcceptedCompensationGreaterThanRequested() {
        TransportClaim underReview = openClaim().startReview();

        assertThrows(IllegalArgumentException.class, () -> underReview.accept(Money.of("1500.00", "EUR")));
    }

    @Test
    void shouldRejectClosedDateBeforeOpenedDate() {
        TransportClaim underReview = openClaim().startReview();

        assertThrows(IllegalArgumentException.class, () -> underReview.reject(LocalDate.of(2026, 6, 30)));
    }

    @Test
    void shouldDetectTypeAndSeverityDetails() {
        TransportClaim delay = TransportClaim.open(
                "CLM-002",
                "SHP-001",
                "CUST-001",
                ClaimType.DELAY,
                ClaimSeverity.MEDIUM,
                Money.of("300.00", "EUR"),
                openedDate(),
                Notes.empty()
        );

        TransportClaim billing = TransportClaim.open(
                "CLM-003",
                "SHP-001",
                "CUST-001",
                ClaimType.BILLING_DISPUTE,
                ClaimSeverity.LOW,
                Money.of("100.00", "EUR"),
                openedDate(),
                Notes.empty()
        );

        assertTrue(delay.isTimeRelated());
        assertTrue(billing.isFinancialDispute());
        assertTrue(ClaimSeverity.HIGH.isUrgent());
        assertTrue(ClaimSeverity.CRITICAL.isAtLeast(ClaimSeverity.HIGH));
    }

    @Test
    void shouldDetectNotes() {
        TransportClaim claim = TransportClaim.open(
                "CLM-001",
                "SHP-001",
                "CUST-001",
                ClaimType.CARGO_DAMAGE,
                ClaimSeverity.HIGH,
                Money.of("1200.00", "EUR"),
                openedDate(),
                Notes.of("Cliente segnala pallet danneggiati")
        );

        assertTrue(claim.hasNotes());
    }

    @Test
    void shouldFormatSingleLine() {
        assertEquals(
                "CLM-001 - shipment: SHP-001 - customer: CUST-001 - CARGO_DAMAGE - OPEN",
                openClaim().formatSingleLine()
        );
    }

    @Test
    void shouldConsiderEquivalentClaimsEqual() {
        TransportClaim first = openClaim();
        TransportClaim second = openClaim();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
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
