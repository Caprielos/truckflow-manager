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
        if (cargoLoad == null) {
            throw new IllegalArgumentException("Il carico è obbligatorio.");
        }

        if (maxWeight == null) {
            throw new IllegalArgumentException("Il peso massimo è obbligatorio.");
        }

        return cargoLoad.calculateTotalWeight().isLessThanOrEqualTo(maxWeight);
    }

    public static boolean isWithinMaxVolume(CargoLoad cargoLoad, Volume maxVolume) {
        if (cargoLoad == null) {
            throw new IllegalArgumentException("Il carico è obbligatorio.");
        }

        if (maxVolume == null) {
            throw new IllegalArgumentException("Il volume massimo è obbligatorio.");
        }

        return cargoLoad.calculateTotalVolume().isLessThanOrEqualTo(maxVolume);
    }

    public static boolean fitsInsideCargoSpace(CargoLoad cargoLoad, Dimension cargoSpaceDimension) {
        if (cargoLoad == null) {
            throw new IllegalArgumentException("Il carico è obbligatorio.");
        }

        if (cargoSpaceDimension == null) {
            throw new IllegalArgumentException("Le dimensioni dello spazio di carico sono obbligatorie.");
        }

        return cargoLoad.allItemsFitInside(cargoSpaceDimension);
    }

    public static boolean requiresTemperatureControlledTransport(CargoLoad cargoLoad) {
        if (cargoLoad == null) {
            throw new IllegalArgumentException("Il carico è obbligatorio.");
        }

        return cargoLoad.requiresTemperatureControl();
    }

    public static boolean containsHazardousMaterial(CargoLoad cargoLoad) {
        if (cargoLoad == null) {
            throw new IllegalArgumentException("Il carico è obbligatorio.");
        }

        return cargoLoad.hasCategory(CargoCategory.HAZARDOUS_MATERIAL);
    }

    public static boolean containsFragileCargo(CargoLoad cargoLoad) {
        if (cargoLoad == null) {
            throw new IllegalArgumentException("Il carico è obbligatorio.");
        }

        return cargoLoad.hasCategory(CargoCategory.FRAGILE);
    }

    public static boolean containsOversizedCargo(CargoLoad cargoLoad) {
        if (cargoLoad == null) {
            throw new IllegalArgumentException("Il carico è obbligatorio.");
        }

        return cargoLoad.hasCategory(CargoCategory.OVERSIZED);
    }
}
