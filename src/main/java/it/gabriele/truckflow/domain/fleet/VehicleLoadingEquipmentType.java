package it.gabriele.truckflow.domain.fleet;

/**
 * Moduli/accessori per carico e scarico.
 */
public enum VehicleLoadingEquipmentType {

    HYDRAULIC_CRANE,
    TAIL_LIFT,
    HYDRAULIC_RAMP,
    MANUAL_RAMP,
    HYDRAULIC_WINCH,
    ELECTRIC_WINCH,
    POLYP_GRAPPLE_LOADER,
    REFRIGERATION_UNIT,
    TWIST_LOCK;

    public boolean requiresOperatorQualification() {
        return this == HYDRAULIC_CRANE
                || this == POLYP_GRAPPLE_LOADER
                || this == HYDRAULIC_WINCH
                || this == ELECTRIC_WINCH;
    }
}
