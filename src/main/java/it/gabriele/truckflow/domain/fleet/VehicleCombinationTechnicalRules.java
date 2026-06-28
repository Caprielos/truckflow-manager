package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Weight;

public final class VehicleCombinationTechnicalRules {

    private VehicleCombinationTechnicalRules() {
    }

    public static Weight calculateGrossCombinationWeight(
            VehicleMassSpecification poweredUnitMass,
            VehicleMassSpecification towedUnitMass
    ) {
        validateMass(poweredUnitMass);
        validateMass(towedUnitMass);

        return Weight.ofKilograms(
                poweredUnitMass.getGrossVehicleWeight().getKilograms()
                        + towedUnitMass.getGrossVehicleWeight().getKilograms()
        );
    }

    public static Weight calculateTotalTareWeight(
            VehicleMassSpecification poweredUnitMass,
            VehicleMassSpecification towedUnitMass
    ) {
        validateMass(poweredUnitMass);
        validateMass(towedUnitMass);

        return Weight.ofKilograms(
                poweredUnitMass.getTareWeight().getKilograms()
                        + towedUnitMass.getTareWeight().getKilograms()
        );
    }

    public static Weight calculateTotalPayload(
            VehicleMassSpecification poweredUnitMass,
            VehicleMassSpecification towedUnitMass
    ) {
        Weight gross = calculateGrossCombinationWeight(poweredUnitMass, towedUnitMass);
        Weight tare = calculateTotalTareWeight(poweredUnitMass, towedUnitMass);
        return Weight.ofKilograms(gross.getKilograms() - tare.getKilograms());
    }

    public static boolean isWithinGrossWeightLimit(
            VehicleMassSpecification poweredUnitMass,
            VehicleMassSpecification towedUnitMass,
            VehicleCombinationLegalLimitProfile legalLimitProfile
    ) {
        if (legalLimitProfile == null) {
            throw new IllegalArgumentException("Il profilo limiti legali è obbligatorio.");
        }
        return calculateGrossCombinationWeight(poweredUnitMass, towedUnitMass)
                .isLessThanOrEqualTo(legalLimitProfile.getMaximumGrossCombinationWeight());
    }

    public static boolean canTow(
            VehicleMassSpecification poweredUnitMass,
            VehicleMassSpecification towedUnitMass
    ) {
        validateMass(poweredUnitMass);
        validateMass(towedUnitMass);
        return poweredUnitMass.canTow(towedUnitMass.getGrossVehicleWeight());
    }

    public static boolean isWithinExternalDimensionLimit(
            Dimension calculatedExternalDimension,
            VehicleCombinationLegalLimitProfile legalLimitProfile
    ) {
        if (calculatedExternalDimension == null) {
            throw new IllegalArgumentException("Le dimensioni calcolate del convoglio sono obbligatorie.");
        }
        if (legalLimitProfile == null) {
            throw new IllegalArgumentException("Il profilo limiti legali è obbligatorio.");
        }
        return calculatedExternalDimension.fitsInside(legalLimitProfile.getMaximumExternalDimension());
    }

    private static void validateMass(VehicleMassSpecification massSpecification) {
        if (massSpecification == null) {
            throw new IllegalArgumentException("Le masse veicolo sono obbligatorie.");
        }
    }
}
