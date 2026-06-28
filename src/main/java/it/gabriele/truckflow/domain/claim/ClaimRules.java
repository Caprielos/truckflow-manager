package it.gabriele.truckflow.domain.claim;

/**
 * Regole di dominio per reclami e contestazioni.
 */
public final class ClaimRules {

    private ClaimRules() {
    }

    public static boolean canBeReviewed(TransportClaim claim) {
        validateClaim(claim);

        return claim.getStatus() == ClaimStatus.OPEN;
    }

    public static boolean canBeAccepted(TransportClaim claim) {
        validateClaim(claim);

        return claim.getStatus() == ClaimStatus.UNDER_REVIEW;
    }

    public static boolean canBeRejected(TransportClaim claim) {
        validateClaim(claim);

        return claim.getStatus() == ClaimStatus.UNDER_REVIEW;
    }

    public static boolean canBeSettled(TransportClaim claim) {
        validateClaim(claim);

        return claim.getStatus() == ClaimStatus.ACCEPTED;
    }

    public static boolean canBeCancelled(TransportClaim claim) {
        validateClaim(claim);

        return !claim.getStatus().isTerminal();
    }

    public static boolean isOpenForAction(TransportClaim claim) {
        validateClaim(claim);

        return !claim.getStatus().isTerminal();
    }

    public static boolean isResolved(TransportClaim claim) {
        validateClaim(claim);

        return claim.getStatus() == ClaimStatus.SETTLED
                || claim.getStatus() == ClaimStatus.REJECTED;
    }

    public static boolean requiresUrgentReview(TransportClaim claim) {
        validateClaim(claim);

        return claim.getSeverity().isUrgent()
                || claim.getType() == ClaimType.TEMPERATURE_EXCURSION
                && claim.getSeverity().isAtLeast(ClaimSeverity.MEDIUM);
    }

    public static boolean isAcceptedCompensationWithinRequestedAmount(TransportClaim claim) {
        validateClaim(claim);

        if (!claim.hasAcceptedCompensation()) {
            return false;
        }

        try {
            claim.getRequestedCompensation().subtract(claim.getAcceptedCompensation());
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    private static void validateClaim(TransportClaim claim) {
        if (claim == null) {
            throw new IllegalArgumentException("Il reclamo è obbligatorio.");
        }
    }
}
