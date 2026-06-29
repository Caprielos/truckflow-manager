package it.gabriele.truckflow.domain.parking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Risorsa fisica parcheggiata: singolo mezzo, rimorchio, semirimorchio o convoglio già agganciato.
 */
public final class ParkedResource {

    private static final int MAX_ID_LENGTH = 80;
    private static final int MAX_DISPLAY_NAME_LENGTH = 200;

    private final ParkingResourceType type;
    private final String resourceId;
    private final String displayName;
    private final List<String> componentResourceIds;
    private final double totalLengthMeters;
    private final boolean readyForMission;

    private ParkedResource(
            ParkingResourceType type,
            String resourceId,
            String displayName,
            List<String> componentResourceIds,
            double totalLengthMeters,
            boolean readyForMission
    ) {
        this.type = validateType(type);
        this.resourceId = validateIdentifier(resourceId, "Il codice risorsa parcheggiata è obbligatorio.");
        this.displayName = validateDisplayName(displayName);
        this.componentResourceIds = List.copyOf(validateComponentResourceIds(componentResourceIds));
        this.totalLengthMeters = validateLength(totalLengthMeters);
        this.readyForMission = readyForMission;

        validateCombinationConsistency();
    }

    public static ParkedResource van(String vehicleId, String displayName, double totalLengthMeters) {
        return new ParkedResource(ParkingResourceType.VAN, vehicleId, displayName, List.of(vehicleId), totalLengthMeters, true);
    }

    public static ParkedResource rigidTruck(String vehicleId, String displayName, double totalLengthMeters) {
        return new ParkedResource(ParkingResourceType.RIGID_TRUCK, vehicleId, displayName, List.of(vehicleId), totalLengthMeters, true);
    }

    public static ParkedResource tractorUnit(String vehicleId, String displayName, double totalLengthMeters) {
        return new ParkedResource(ParkingResourceType.TRACTOR_UNIT, vehicleId, displayName, List.of(vehicleId), totalLengthMeters, false);
    }

    public static ParkedResource trailer(String trailerId, String displayName, double totalLengthMeters) {
        return new ParkedResource(ParkingResourceType.TRAILER, trailerId, displayName, List.of(trailerId), totalLengthMeters, false);
    }

    public static ParkedResource semiTrailer(String trailerId, String displayName, double totalLengthMeters) {
        return new ParkedResource(ParkingResourceType.SEMI_TRAILER, trailerId, displayName, List.of(trailerId), totalLengthMeters, false);
    }

    public static ParkedResource articulatedVehicle(
            String combinationId,
            String tractorUnitId,
            String semiTrailerId,
            String displayName,
            double totalLengthMeters,
            boolean readyForMission
    ) {
        return new ParkedResource(
                ParkingResourceType.ARTICULATED_VEHICLE,
                combinationId,
                displayName,
                List.of(
                        validateIdentifier(tractorUnitId, "Il codice trattore del complesso veicolare è obbligatorio."),
                        validateIdentifier(semiTrailerId, "Il codice semirimorchio del complesso veicolare è obbligatorio.")
                ),
                totalLengthMeters,
                readyForMission
        );
    }

    public static ParkedResource truckAndTrailer(
            String combinationId,
            String truckId,
            String trailerId,
            String displayName,
            double totalLengthMeters,
            boolean readyForMission
    ) {
        return new ParkedResource(
                ParkingResourceType.TRUCK_AND_TRAILER,
                combinationId,
                displayName,
                List.of(
                        validateIdentifier(truckId, "Il codice motrice dell'autotreno è obbligatorio."),
                        validateIdentifier(trailerId, "Il codice rimorchio dell'autotreno è obbligatorio.")
                ),
                totalLengthMeters,
                readyForMission
        );
    }

    public static ParkedResource equipment(String equipmentId, String displayName, double totalLengthMeters) {
        return new ParkedResource(ParkingResourceType.EQUIPMENT, equipmentId, displayName, List.of(equipmentId), totalLengthMeters, false);
    }

    private static ParkingResourceType validateType(ParkingResourceType type) {
        if (type == null) {
            throw new IllegalArgumentException("Il tipo risorsa parcheggiata è obbligatorio.");
        }
        return type;
    }

    private static String validateIdentifier(String value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }

        String normalizedValue = value.trim().toUpperCase();

        if (normalizedValue.isEmpty()) {
            throw new IllegalArgumentException(message);
        }

        if (normalizedValue.length() > MAX_ID_LENGTH) {
            throw new IllegalArgumentException("Il codice risorsa parcheggiata non può superare " + MAX_ID_LENGTH + " caratteri.");
        }

        if (!normalizedValue.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice risorsa parcheggiata può contenere solo lettere, numeri, trattini e underscore.");
        }

        return normalizedValue;
    }

    private static String validateDisplayName(String displayName) {
        if (displayName == null) {
            throw new IllegalArgumentException("La descrizione risorsa parcheggiata è obbligatoria.");
        }

        String normalizedDisplayName = displayName.trim();

        if (normalizedDisplayName.isEmpty()) {
            throw new IllegalArgumentException("La descrizione risorsa parcheggiata non può essere vuota.");
        }

        if (normalizedDisplayName.length() > MAX_DISPLAY_NAME_LENGTH) {
            throw new IllegalArgumentException("La descrizione risorsa parcheggiata non può superare " + MAX_DISPLAY_NAME_LENGTH + " caratteri.");
        }

        return normalizedDisplayName;
    }

    private static List<String> validateComponentResourceIds(List<String> componentResourceIds) {
        if (componentResourceIds == null || componentResourceIds.isEmpty()) {
            throw new IllegalArgumentException("I componenti della risorsa parcheggiata sono obbligatori.");
        }

        List<String> normalizedIds = new ArrayList<>();
        for (String componentResourceId : componentResourceIds) {
            normalizedIds.add(validateIdentifier(componentResourceId, "Il codice componente parcheggiato è obbligatorio."));
        }
        return normalizedIds;
    }

    private static double validateLength(double totalLengthMeters) {
        if (Double.isNaN(totalLengthMeters) || Double.isInfinite(totalLengthMeters) || totalLengthMeters <= 0) {
            throw new IllegalArgumentException("La lunghezza totale risorsa parcheggiata deve essere positiva.");
        }
        return totalLengthMeters;
    }

    private void validateCombinationConsistency() {
        if (type.isCombination() && componentResourceIds.size() < 2) {
            throw new IllegalArgumentException("Un convoglio parcheggiato deve contenere almeno motrice e unità rimorchiata.");
        }

        if (!type.isCombination() && componentResourceIds.size() != 1) {
            throw new IllegalArgumentException("Una risorsa singola deve contenere un solo componente.");
        }
    }

    public ParkingResourceType getType() {
        return type;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getComponentResourceIds() {
        return componentResourceIds;
    }

    public double getTotalLengthMeters() {
        return totalLengthMeters;
    }

    public boolean isReadyForMission() {
        return readyForMission;
    }

    public boolean isCombination() {
        return type.isCombination();
    }

    public boolean includesResource(String componentResourceId) {
        String normalizedId = validateIdentifier(componentResourceId, "Il codice componente da cercare è obbligatorio.");
        return componentResourceIds.contains(normalizedId);
    }

    public int componentCount() {
        return componentResourceIds.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkedResource that)) return false;
        return Double.compare(totalLengthMeters, that.totalLengthMeters) == 0
                && readyForMission == that.readyForMission
                && type == that.type
                && resourceId.equals(that.resourceId)
                && displayName.equals(that.displayName)
                && componentResourceIds.equals(that.componentResourceIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, resourceId, displayName, componentResourceIds, totalLengthMeters, readyForMission);
    }

    @Override
    public String toString() {
        return resourceId + " - " + type + " - " + displayName;
    }
}
