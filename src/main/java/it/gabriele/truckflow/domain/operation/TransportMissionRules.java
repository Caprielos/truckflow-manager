package it.gabriele.truckflow.domain.operation;

import it.gabriele.truckflow.domain.compliance.ComplianceRules;

/**
 * Contiene regole di dominio relative alla missione di trasporto.
 */
public final class TransportMissionRules {

    private TransportMissionRules() {
    }

    public static boolean isCompliant(TransportMission mission) {
        validateMission(mission);

        return ComplianceRules.isAssignmentCompliant(
                mission.getDriver(),
                mission.getVehicleCombination(),
                mission.getRoutePlan(),
                mission.getShipment()
        );
    }

    public static boolean canBeDispatched(TransportMission mission) {
        validateMission(mission);

        return mission.getStatus() == TransportMissionStatus.PLANNED
                && isCompliant(mission);
    }

    public static boolean canBeStarted(TransportMission mission) {
        validateMission(mission);

        return mission.getStatus() == TransportMissionStatus.DISPATCHED;
    }

    public static boolean canBeCompleted(TransportMission mission) {
        validateMission(mission);

        return mission.getStatus() == TransportMissionStatus.IN_PROGRESS;
    }

    public static boolean canBeCancelled(TransportMission mission) {
        validateMission(mission);

        return !mission.getStatus().isTerminal();
    }

    public static boolean isCompleted(TransportMission mission) {
        validateMission(mission);

        return mission.getStatus() == TransportMissionStatus.COMPLETED;
    }

    public static boolean isTerminal(TransportMission mission) {
        validateMission(mission);

        return mission.getStatus().isTerminal();
    }

    public static boolean requiresSpecialHandling(TransportMission mission) {
        validateMission(mission);

        return mission.requiresSpecialComplianceChecks();
    }

    private static void validateMission(TransportMission mission) {
        if (mission == null) {
            throw new IllegalArgumentException("La missione è obbligatoria.");
        }
    }
}
