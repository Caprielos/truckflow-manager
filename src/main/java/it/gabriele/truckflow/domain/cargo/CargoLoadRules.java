package it.gabriele.truckflow.domain.cargo;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Volume;
import it.gabriele.truckflow.domain.shared.Weight;

/**
 * Contiene regole di dominio relative a un carico.
 */
public final class CargoLoadRules {

    private CargoLoadRules() {
    }

    public static boolean isWithinMaxWeight(CargoLoad cargoLoad, Weight maxWeight) {
        validateCargoLoad(cargoLoad);

        if (maxWeight == null) {
            throw new IllegalArgumentException("Il peso massimo è obbligatorio.");
        }

        return cargoLoad.calculateTotalWeight().isLessThanOrEqualTo(maxWeight);
    }

    public static boolean isWithinMaxVolume(CargoLoad cargoLoad, Volume maxVolume) {
        validateCargoLoad(cargoLoad);

        if (maxVolume == null) {
            throw new IllegalArgumentException("Il volume massimo è obbligatorio.");
        }

        return cargoLoad.calculateTotalVolume().isLessThanOrEqualTo(maxVolume);
    }

    public static boolean fitsInsideCargoSpace(CargoLoad cargoLoad, Dimension cargoSpaceDimension) {
        validateCargoLoad(cargoLoad);

        if (cargoSpaceDimension == null) {
            throw new IllegalArgumentException("Le dimensioni dello spazio di carico sono obbligatorie.");
        }

        return cargoLoad.allItemsFitInside(cargoSpaceDimension);
    }

    public static boolean requiresTemperatureControlledTransport(CargoLoad cargoLoad) {
        validateCargoLoad(cargoLoad);

        return cargoLoad.requiresTemperatureControl();
    }

    public static boolean containsHazardousMaterial(CargoLoad cargoLoad) {
        validateCargoLoad(cargoLoad);

        return cargoLoad.hasCategory(CargoCategory.HAZARDOUS_MATERIAL)
                || cargoLoad.containsDangerousGoods();
    }

    public static boolean containsDangerousGoods(CargoLoad cargoLoad) {
        validateCargoLoad(cargoLoad);

        return cargoLoad.containsDangerousGoods();
    }

    public static boolean requiresAdrTransport(CargoLoad cargoLoad) {
        validateCargoLoad(cargoLoad);

        return cargoLoad.requiresAdrTransport();
    }

    public static boolean requiresAdrTankTransport(CargoLoad cargoLoad) {
        validateCargoLoad(cargoLoad);

        return cargoLoad.requiresAdrTankTransport();
    }

    public static boolean containsExplosives(CargoLoad cargoLoad) {
        validateCargoLoad(cargoLoad);

        return cargoLoad.containsAdrClass(AdrClass.CLASS_1_EXPLOSIVES);
    }

    public static boolean containsRadioactiveMaterial(CargoLoad cargoLoad) {
        validateCargoLoad(cargoLoad);

        return cargoLoad.containsAdrClass(AdrClass.CLASS_7_RADIOACTIVE_MATERIAL);
    }

    public static boolean containsFragileCargo(CargoLoad cargoLoad) {
        validateCargoLoad(cargoLoad);

        return cargoLoad.hasCategory(CargoCategory.FRAGILE);
    }

    public static boolean containsOversizedCargo(CargoLoad cargoLoad) {
        validateCargoLoad(cargoLoad);

        return cargoLoad.hasCategory(CargoCategory.OVERSIZED);
    }

    private static void validateCargoLoad(CargoLoad cargoLoad) {
        if (cargoLoad == null) {
            throw new IllegalArgumentException("Il carico è obbligatorio.");
        }
    }
}
