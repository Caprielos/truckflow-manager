package it.gabriele.truckflow.domain.fleet;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class VehicleAxleSpecification {

    private final List<VehicleAxle> axles;
    private final SuspensionType suspensionType;
    private final BrakeType brakeType;
    private final Set<BrakeSafetySystem> brakeSafetySystems;
    private final String axleBrand;

    private VehicleAxleSpecification(
            List<VehicleAxle> axles,
            SuspensionType suspensionType,
            BrakeType brakeType,
            Set<BrakeSafetySystem> brakeSafetySystems,
            String axleBrand
    ) {
        if (axles == null || axles.isEmpty()) {
            throw new IllegalArgumentException("Gli assi sono obbligatori.");
        }
        if (axles.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Gli assi non possono contenere valori nulli.");
        }
        if (suspensionType == null) {
            throw new IllegalArgumentException("Il tipo sospensioni è obbligatorio.");
        }
        if (brakeType == null) {
            throw new IllegalArgumentException("Il tipo freni è obbligatorio.");
        }
        if (brakeSafetySystems == null) {
            throw new IllegalArgumentException("I sistemi sicurezza freni sono obbligatori.");
        }
        if (brakeSafetySystems.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("I sistemi sicurezza freni non possono contenere valori nulli.");
        }
        this.axles = List.copyOf(axles);
        this.suspensionType = suspensionType;
        this.brakeType = brakeType;
        this.brakeSafetySystems = Set.copyOf(brakeSafetySystems);
        this.axleBrand = axleBrand == null ? "" : axleBrand.trim();
    }

    public static VehicleAxleSpecification of(
            List<VehicleAxle> axles,
            SuspensionType suspensionType,
            BrakeType brakeType,
            Set<BrakeSafetySystem> brakeSafetySystems,
            String axleBrand
    ) {
        return new VehicleAxleSpecification(axles, suspensionType, brakeType, brakeSafetySystems, axleBrand);
    }

    public List<VehicleAxle> getAxles() {
        return axles;
    }

    public int getAxleCount() {
        return axles.size();
    }

    public long countLiftableAxles() {
        return axles.stream().filter(VehicleAxle::isLiftable).count();
    }

    public long countSteeringAxles() {
        return axles.stream().filter(VehicleAxle::isSteering).count();
    }

    public SuspensionType getSuspensionType() {
        return suspensionType;
    }

    public BrakeType getBrakeType() {
        return brakeType;
    }

    public Set<BrakeSafetySystem> getBrakeSafetySystems() {
        return brakeSafetySystems;
    }

    public String getAxleBrand() {
        return axleBrand;
    }
}
