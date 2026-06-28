package it.gabriele.truckflow.domain.cargo;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.Weight;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa il collegamento tra cargo e merci pericolose ADR.
 */
class DangerousCargoTest {

    @Test
    void shouldCreateDangerousGoodsCargoItem() {
        DangerousGoodsProfile profile = gasolineProfile();

        CargoItem item = CargoItem.dangerousGoods(
                "Benzina in cisterna",
                CargoCategory.HAZARDOUS_MATERIAL,
                Weight.ofKilograms(10000),
                Dimension.ofMeters(6, 2.4, 2.5),
                profile,
                Notes.empty()
        );

        assertEquals("Benzina in cisterna", item.getDescription());
        assertEquals(CargoCategory.HAZARDOUS_MATERIAL, item.getCategory());
        assertEquals(profile, item.getDangerousGoodsProfile());
        assertTrue(item.isDangerousGoods());
        assertTrue(item.hasDangerousGoodsProfile());
        assertTrue(item.requiresAdrTransport());
        assertTrue(item.requiresAdrTankTransport());
        assertTrue(item.isAdrClass(AdrClass.CLASS_3_FLAMMABLE_LIQUIDS));
    }

    @Test
    void shouldCreateTemperatureControlledDangerousGoodsCargoItem() {
        DangerousGoodsProfile profile = DangerousGoodsProfile.of(
                "UN 3373",
                "Biological substance, category B",
                AdrClass.CLASS_6_2_INFECTIOUS_SUBSTANCES,
                "",
                null,
                Set.of(HazardLabel.LABEL_6_2_INFECTIOUS),
                "",
                2,
                false
        );

        CargoItem item = CargoItem.temperatureControlledDangerousGoods(
                "Campioni biologici refrigerati",
                CargoCategory.PHARMACEUTICAL,
                Weight.ofKilograms(100),
                Dimension.ofMeters(1, 1, 1),
                TemperatureRange.ofCelsius(2, 8),
                profile,
                Notes.empty()
        );

        assertTrue(item.requiresTemperatureControl());
        assertTrue(item.requiresAdrTransport());
        assertFalse(item.requiresAdrTankTransport());
        assertTrue(item.isAdrClass(AdrClass.CLASS_6_2_INFECTIOUS_SUBSTANCES));
    }

    @Test
    void shouldNotCreateDangerousGoodsItemWithoutProfile() {
        assertThrows(IllegalArgumentException.class, () -> CargoItem.dangerousGoods(
                "Benzina",
                CargoCategory.HAZARDOUS_MATERIAL,
                Weight.ofKilograms(10000),
                Dimension.ofMeters(6, 2.4, 2.5),
                null,
                Notes.empty()
        ));
    }

    @Test
    void shouldKeepNormalCargoBackwardCompatible() {
        CargoItem item = CargoItem.of(
                "Merce generale",
                CargoCategory.GENERAL,
                Weight.ofKilograms(500),
                Dimension.ofMeters(2, 1, 1),
                Notes.empty()
        );

        assertFalse(item.isDangerousGoods());
        assertFalse(item.hasDangerousGoodsProfile());
        assertFalse(item.requiresAdrTransport());
        assertFalse(item.requiresAdrTankTransport());
        assertNull(item.getDangerousGoodsProfile());
    }

    @Test
    void shouldRecognizeGenericHazardousCategoryEvenWithoutDetailedProfile() {
        CargoItem item = CargoItem.of(
                "Prodotto chimico generico",
                CargoCategory.HAZARDOUS_MATERIAL,
                Weight.ofKilograms(500),
                Dimension.ofMeters(2, 1, 1),
                Notes.empty()
        );

        assertTrue(item.isDangerousGoods());
        assertFalse(item.hasDangerousGoodsProfile());
        assertFalse(item.requiresAdrTransport());
    }

    @Test
    void shouldDetectDangerousGoodsAtCargoLoadLevel() {
        CargoLoad cargoLoad = CargoLoad.of(
                CargoItem.dangerousGoods(
                        "Benzina in cisterna",
                        CargoCategory.HAZARDOUS_MATERIAL,
                        Weight.ofKilograms(10000),
                        Dimension.ofMeters(6, 2.4, 2.5),
                        gasolineProfile(),
                        Notes.empty()
                )
        );

        assertTrue(cargoLoad.containsDangerousGoods());
        assertTrue(cargoLoad.hasDangerousGoodsProfile());
        assertTrue(cargoLoad.requiresAdrTransport());
        assertTrue(cargoLoad.requiresAdrTankTransport());
        assertTrue(cargoLoad.containsAdrClass(AdrClass.CLASS_3_FLAMMABLE_LIQUIDS));
        assertEquals(1, cargoLoad.getDangerousGoodsProfiles().size());
    }

    @Test
    void shouldDetectDangerousGoodsRules() {
        CargoLoad cargoLoad = CargoLoad.of(
                CargoItem.dangerousGoods(
                        "Benzina in cisterna",
                        CargoCategory.HAZARDOUS_MATERIAL,
                        Weight.ofKilograms(10000),
                        Dimension.ofMeters(6, 2.4, 2.5),
                        gasolineProfile(),
                        Notes.empty()
                )
        );

        assertTrue(CargoLoadRules.containsHazardousMaterial(cargoLoad));
        assertTrue(CargoLoadRules.containsDangerousGoods(cargoLoad));
        assertTrue(CargoLoadRules.requiresAdrTransport(cargoLoad));
        assertTrue(CargoLoadRules.requiresAdrTankTransport(cargoLoad));
        assertFalse(CargoLoadRules.containsExplosives(cargoLoad));
        assertFalse(CargoLoadRules.containsRadioactiveMaterial(cargoLoad));
    }

    @Test
    void shouldDetectExplosivesAndRadioactiveMaterial() {
        CargoLoad explosives = CargoLoad.of(
                CargoItem.dangerousGoods(
                        "Materiale esplosivo",
                        CargoCategory.HAZARDOUS_MATERIAL,
                        Weight.ofKilograms(100),
                        Dimension.ofMeters(1, 1, 1),
                        explosiveProfile(),
                        Notes.empty()
                )
        );

        CargoLoad radioactive = CargoLoad.of(
                CargoItem.dangerousGoods(
                        "Materiale radioattivo",
                        CargoCategory.HAZARDOUS_MATERIAL,
                        Weight.ofKilograms(100),
                        Dimension.ofMeters(1, 1, 1),
                        radioactiveProfile(),
                        Notes.empty()
                )
        );

        assertTrue(CargoLoadRules.containsExplosives(explosives));
        assertTrue(CargoLoadRules.containsRadioactiveMaterial(radioactive));
    }

    @Test
    void shouldNotCheckNullAdrClass() {
        CargoItem item = CargoItem.dangerousGoods(
                "Benzina in cisterna",
                CargoCategory.HAZARDOUS_MATERIAL,
                Weight.ofKilograms(10000),
                Dimension.ofMeters(6, 2.4, 2.5),
                gasolineProfile(),
                Notes.empty()
        );

        CargoLoad cargoLoad = CargoLoad.of(item);

        assertThrows(IllegalArgumentException.class, () -> item.isAdrClass(null));
        assertThrows(IllegalArgumentException.class, () -> cargoLoad.containsAdrClass(null));
    }

    private static DangerousGoodsProfile gasolineProfile() {
        return DangerousGoodsProfile.of(
                "UN 1203",
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                2,
                true
        );
    }

    private static DangerousGoodsProfile explosiveProfile() {
        return DangerousGoodsProfile.of(
                "UN 0336",
                "Fireworks",
                AdrClass.CLASS_1_EXPLOSIVES,
                "1.4G",
                null,
                Set.of(HazardLabel.LABEL_1_EXPLOSIVES),
                "E",
                2,
                false
        );
    }

    private static DangerousGoodsProfile radioactiveProfile() {
        return DangerousGoodsProfile.of(
                "UN 2915",
                "Radioactive material, type A package",
                AdrClass.CLASS_7_RADIOACTIVE_MATERIAL,
                "",
                null,
                Set.of(HazardLabel.LABEL_7_RADIOACTIVE),
                "E",
                2,
                false
        );
    }
}
