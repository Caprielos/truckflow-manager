package it.gabriele.truckflow.domain.cargo;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.Volume;
import it.gabriele.truckflow.domain.shared.Weight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa CargoLoadRules.
 */
class CargoLoadRulesTest {

    @Test
    void shouldCheckIfCargoLoadIsWithinMaxWeight() {
        CargoLoad cargoLoad = CargoLoad.of(
                item("Bancale 1", CargoCategory.GENERAL, 100, Dimension.ofMeters(1, 1, 1)),
                item("Bancale 2", CargoCategory.GENERAL, 150, Dimension.ofMeters(1, 1, 1))
        );

        assertTrue(CargoLoadRules.isWithinMaxWeight(cargoLoad, Weight.ofKilograms(300)));
        assertFalse(CargoLoadRules.isWithinMaxWeight(cargoLoad, Weight.ofKilograms(200)));
    }

    @Test
    void shouldCheckIfCargoLoadIsWithinMaxVolume() {
        CargoLoad cargoLoad = CargoLoad.of(
                item("Bancale 1", CargoCategory.GENERAL, 100, Dimension.ofMeters(1, 1, 1)),
                item("Bancale 2", CargoCategory.GENERAL, 150, Dimension.ofMeters(2, 1, 1))
        );

        assertTrue(CargoLoadRules.isWithinMaxVolume(cargoLoad, Volume.ofCubicMeters(4)));
        assertFalse(CargoLoadRules.isWithinMaxVolume(cargoLoad, Volume.ofCubicMeters(2)));
    }

    @Test
    void shouldCheckIfCargoLoadFitsInsideCargoSpace() {
        CargoLoad cargoLoad = CargoLoad.of(
                item("Bancale 1", CargoCategory.GENERAL, 100, Dimension.ofMeters(1, 1, 1)),
                item("Bancale 2", CargoCategory.GENERAL, 150, Dimension.ofMeters(2, 1, 1))
        );

        assertTrue(CargoLoadRules.fitsInsideCargoSpace(cargoLoad, Dimension.ofMeters(2, 2, 2)));
        assertFalse(CargoLoadRules.fitsInsideCargoSpace(cargoLoad, Dimension.ofMeters(1, 1, 1)));
    }

    @Test
    void shouldDetectTemperatureControlledTransportRequirement() {
        CargoLoad cargoLoad = CargoLoad.of(
                CargoItem.temperatureControlled(
                        "Vaccini",
                        CargoCategory.PHARMACEUTICAL,
                        Weight.ofKilograms(50),
                        Dimension.ofMeters(1, 1, 1),
                        TemperatureRange.ofCelsius(2, 8),
                        Notes.empty()
                )
        );

        assertTrue(CargoLoadRules.requiresTemperatureControlledTransport(cargoLoad));
    }

    @Test
    void shouldDetectHazardousMaterial() {
        CargoLoad cargoLoad = CargoLoad.of(
                item("Prodotto chimico", CargoCategory.HAZARDOUS_MATERIAL, 100, Dimension.ofMeters(1, 1, 1))
        );

        assertTrue(CargoLoadRules.containsHazardousMaterial(cargoLoad));
    }

    @Test
    void shouldDetectFragileCargo() {
        CargoLoad cargoLoad = CargoLoad.of(
                item("Vetri", CargoCategory.FRAGILE, 80, Dimension.ofMeters(1, 1, 1))
        );

        assertTrue(CargoLoadRules.containsFragileCargo(cargoLoad));
    }

    @Test
    void shouldDetectOversizedCargo() {
        CargoLoad cargoLoad = CargoLoad.of(
                item("Macchinario industriale", CargoCategory.OVERSIZED, 1000, Dimension.ofMeters(4, 2, 2))
        );

        assertTrue(CargoLoadRules.containsOversizedCargo(cargoLoad));
    }

    @Test
    void shouldNotAllowNullCargoLoad() {
        assertThrows(IllegalArgumentException.class,
                () -> CargoLoadRules.isWithinMaxWeight(null, Weight.ofKilograms(100)));

        assertThrows(IllegalArgumentException.class,
                () -> CargoLoadRules.isWithinMaxVolume(null, Volume.ofCubicMeters(10)));

        assertThrows(IllegalArgumentException.class,
                () -> CargoLoadRules.fitsInsideCargoSpace(null, Dimension.ofMeters(1, 1, 1)));

        assertThrows(IllegalArgumentException.class,
                () -> CargoLoadRules.requiresTemperatureControlledTransport(null));

        assertThrows(IllegalArgumentException.class,
                () -> CargoLoadRules.containsHazardousMaterial(null));

        assertThrows(IllegalArgumentException.class,
                () -> CargoLoadRules.containsFragileCargo(null));

        assertThrows(IllegalArgumentException.class,
                () -> CargoLoadRules.containsOversizedCargo(null));
    }

    @Test
    void shouldNotAllowNullLimits() {
        CargoLoad cargoLoad = CargoLoad.of(
                item("Bancale", CargoCategory.GENERAL, 100, Dimension.ofMeters(1, 1, 1))
        );

        assertThrows(IllegalArgumentException.class,
                () -> CargoLoadRules.isWithinMaxWeight(cargoLoad, null));

        assertThrows(IllegalArgumentException.class,
                () -> CargoLoadRules.isWithinMaxVolume(cargoLoad, null));

        assertThrows(IllegalArgumentException.class,
                () -> CargoLoadRules.fitsInsideCargoSpace(cargoLoad, null));
    }

    private static CargoItem item(
            String description,
            CargoCategory category,
            double kilograms,
            Dimension dimension
    ) {
        return CargoItem.of(
                description,
                category,
                Weight.ofKilograms(kilograms),
                dimension,
                Notes.empty()
        );
    }
}
