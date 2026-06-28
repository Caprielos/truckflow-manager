package it.gabriele.truckflow.domain.cargo;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.Volume;
import it.gabriele.truckflow.domain.shared.Weight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa CargoItem.
 */
class CargoItemTest {

    @Test
    void shouldCreateCargoItem() {
        CargoItem item = CargoItem.of(
                "Merce generale",
                CargoCategory.GENERAL,
                Weight.ofKilograms(100),
                Dimension.ofMeters(2, 1, 1),
                Notes.empty()
        );

        assertEquals("Merce generale", item.getDescription());
        assertEquals(CargoCategory.GENERAL, item.getCategory());
        assertEquals(Weight.ofKilograms(100), item.getWeight());
        assertEquals(Dimension.ofMeters(2, 1, 1), item.getDimension());
        assertEquals(Notes.empty(), item.getNotes());
        assertFalse(item.requiresTemperatureControl());
    }

    @Test
    void shouldTrimDescription() {
        CargoItem item = CargoItem.of(
                "   Merce generale   ",
                CargoCategory.GENERAL,
                Weight.ofKilograms(100),
                Dimension.ofMeters(2, 1, 1),
                Notes.empty()
        );

        assertEquals("Merce generale", item.getDescription());
    }

    @Test
    void shouldNotAllowNullOrBlankDescription() {
        assertThrows(IllegalArgumentException.class, () -> CargoItem.of(
                null,
                CargoCategory.GENERAL,
                Weight.ofKilograms(100),
                Dimension.ofMeters(2, 1, 1),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> CargoItem.of(
                "   ",
                CargoCategory.GENERAL,
                Weight.ofKilograms(100),
                Dimension.ofMeters(2, 1, 1),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowTooLongDescription() {
        String tooLongDescription = "a".repeat(201);

        assertThrows(IllegalArgumentException.class, () -> CargoItem.of(
                tooLongDescription,
                CargoCategory.GENERAL,
                Weight.ofKilograms(100),
                Dimension.ofMeters(2, 1, 1),
                Notes.empty()
        ));
    }

    @Test
    void shouldNotAllowNullMandatoryFields() {
        assertThrows(IllegalArgumentException.class, () -> CargoItem.of(
                "Merce generale",
                null,
                Weight.ofKilograms(100),
                Dimension.ofMeters(2, 1, 1),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> CargoItem.of(
                "Merce generale",
                CargoCategory.GENERAL,
                null,
                Dimension.ofMeters(2, 1, 1),
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> CargoItem.of(
                "Merce generale",
                CargoCategory.GENERAL,
                Weight.ofKilograms(100),
                null,
                Notes.empty()
        ));

        assertThrows(IllegalArgumentException.class, () -> CargoItem.of(
                "Merce generale",
                CargoCategory.GENERAL,
                Weight.ofKilograms(100),
                Dimension.ofMeters(2, 1, 1),
                null
        ));
    }

    @Test
    void shouldCalculateVolumeFromDimension() {
        CargoItem item = CargoItem.of(
                "Bancale",
                CargoCategory.GENERAL,
                Weight.ofKilograms(100),
                Dimension.ofMeters(2, 3, 4),
                Notes.empty()
        );

        assertEquals(Volume.ofCubicMeters(24), item.calculateVolume());
    }

    @Test
    void shouldCreateTemperatureControlledCargoItem() {
        TemperatureRange requiredTemperature = TemperatureRange.ofCelsius(2, 8);

        CargoItem item = CargoItem.temperatureControlled(
                "Vaccini",
                CargoCategory.PHARMACEUTICAL,
                Weight.ofKilograms(50),
                Dimension.ofMeters(1, 1, 1),
                requiredTemperature,
                Notes.of("Mantenere refrigerato")
        );

        assertTrue(item.requiresTemperatureControl());
        assertEquals(requiredTemperature, item.getRequiredTemperatureRange());
    }

    @Test
    void shouldNotAllowTemperatureControlledCargoWithoutTemperatureRange() {
        assertThrows(IllegalArgumentException.class, () -> CargoItem.temperatureControlled(
                "Vaccini",
                CargoCategory.PHARMACEUTICAL,
                Weight.ofKilograms(50),
                Dimension.ofMeters(1, 1, 1),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldRequireTemperatureRangeForTemperatureControlledCategory() {
        assertThrows(IllegalArgumentException.class, () -> CargoItem.of(
                "Latte fresco",
                CargoCategory.REFRIGERATED_FOOD,
                Weight.ofKilograms(200),
                Dimension.ofMeters(2, 1, 1),
                Notes.empty()
        ));
    }

    @Test
    void shouldDetectNotes() {
        CargoItem item = CargoItem.of(
                "Vetri",
                CargoCategory.FRAGILE,
                Weight.ofKilograms(80),
                Dimension.ofMeters(2, 1, 1),
                Notes.of("Merce fragile")
        );

        assertTrue(item.hasNotes());
    }

    @Test
    void shouldConsiderEquivalentCargoItemsEqual() {
        CargoItem first = CargoItem.of(
                "  Merce generale  ",
                CargoCategory.GENERAL,
                Weight.ofKilograms(100),
                Dimension.ofMeters(2, 1, 1),
                Notes.empty()
        );

        CargoItem second = CargoItem.of(
                "Merce generale",
                CargoCategory.GENERAL,
                Weight.ofKilograms(100),
                Dimension.ofMeters(2, 1, 1),
                Notes.empty()
        );

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}
