package it.gabriele.truckflow.domain.cargo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa DangerousGoodsProfile.
 */
class DangerousGoodsProfileTest {

    @Test
    void shouldCreateDangerousGoodsProfileForGasoline() {
        DangerousGoodsProfile profile = gasolineProfile();

        assertEquals("UN 1203", profile.getUnNumber());
        assertEquals("Gasoline", profile.getProperShippingName());
        assertEquals(AdrClass.CLASS_3_FLAMMABLE_LIQUIDS, profile.getAdrClass());
        assertEquals("F1", profile.getClassificationCode());
        assertEquals(PackingGroup.II, profile.getPackingGroup());
        assertEquals(Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID), profile.getHazardLabels());
        assertEquals("D/E", profile.getTunnelRestrictionCode());
        assertEquals(2, profile.getTransportCategory());
        assertTrue(profile.requiresTankTransport());
        assertTrue(profile.hasPackingGroup());
        assertTrue(profile.isFlammableLiquid());
        assertFalse(profile.isGas());
    }

    @Test
    void shouldCreateDangerousGoodsProfileForGasWithoutPackingGroup() {
        DangerousGoodsProfile profile = DangerousGoodsProfile.of(
                "UN 1965",
                "Hydrocarbon gas mixture, liquefied, n.o.s.",
                AdrClass.CLASS_2_GASES,
                "2F",
                null,
                Set.of(HazardLabel.LABEL_2_1_FLAMMABLE_GAS),
                "B/D",
                2,
                true
        );

        assertEquals("UN 1965", profile.getUnNumber());
        assertFalse(profile.hasPackingGroup());
        assertTrue(profile.isGas());
        assertTrue(profile.requiresAdrTankCertificate());
    }

    @Test
    void shouldNormalizeUnNumber() {
        DangerousGoodsProfile first = DangerousGoodsProfile.of(
                "1203",
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "f1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "d/e",
                2,
                true
        );

        DangerousGoodsProfile second = DangerousGoodsProfile.of(
                "un1203",
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                2,
                true
        );

        assertEquals("UN 1203", first.getUnNumber());
        assertEquals("UN 1203", second.getUnNumber());
        assertEquals("F1", first.getClassificationCode());
        assertEquals("D/E", first.getTunnelRestrictionCode());
    }

    @Test
    void shouldNotAllowInvalidUnNumber() {
        assertThrows(IllegalArgumentException.class, () -> DangerousGoodsProfile.of(
                null,
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                2,
                true
        ));

        assertThrows(IllegalArgumentException.class, () -> DangerousGoodsProfile.of(
                "UN 12",
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                2,
                true
        ));
    }

    @Test
    void shouldNotAllowInvalidProperShippingName() {
        assertThrows(IllegalArgumentException.class, () -> DangerousGoodsProfile.of(
                "UN 1203",
                null,
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                2,
                true
        ));

        assertThrows(IllegalArgumentException.class, () -> DangerousGoodsProfile.of(
                "UN 1203",
                "   ",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                2,
                true
        ));
    }

    @Test
    void shouldNotAllowNullAdrClass() {
        assertThrows(IllegalArgumentException.class, () -> DangerousGoodsProfile.of(
                "UN 1203",
                "Gasoline",
                null,
                "F1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                2,
                true
        ));
    }

    @Test
    void shouldAllowEmptyOptionalClassificationCodeAndTunnelCode() {
        DangerousGoodsProfile profile = DangerousGoodsProfile.of(
                "UN 9999",
                "Generic dangerous substance",
                AdrClass.CLASS_9_MISCELLANEOUS,
                null,
                PackingGroup.III,
                Set.of(HazardLabel.LABEL_9_MISCELLANEOUS),
                null,
                3,
                false
        );

        assertEquals("", profile.getClassificationCode());
        assertEquals("", profile.getTunnelRestrictionCode());
    }

    @Test
    void shouldNotAllowInvalidClassificationCode() {
        assertThrows(IllegalArgumentException.class, () -> DangerousGoodsProfile.of(
                "UN 1203",
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F 1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                2,
                true
        ));
    }

    @Test
    void shouldNotAllowInvalidHazardLabels() {
        assertThrows(IllegalArgumentException.class, () -> DangerousGoodsProfile.of(
                "UN 1203",
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                null,
                "D/E",
                2,
                true
        ));

        assertThrows(IllegalArgumentException.class, () -> DangerousGoodsProfile.of(
                "UN 1203",
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                Set.of(),
                "D/E",
                2,
                true
        ));

        Set<HazardLabel> labelsWithNull = new HashSet<>(Arrays.asList(
                HazardLabel.LABEL_3_FLAMMABLE_LIQUID,
                null
        ));

        assertThrows(IllegalArgumentException.class, () -> DangerousGoodsProfile.of(
                "UN 1203",
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                labelsWithNull,
                "D/E",
                2,
                true
        ));
    }

    @Test
    void shouldNotAllowInvalidTunnelCode() {
        assertThrows(IllegalArgumentException.class, () -> DangerousGoodsProfile.of(
                "UN 1203",
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D E",
                2,
                true
        ));
    }

    @Test
    void shouldNotAllowInvalidTransportCategory() {
        assertThrows(IllegalArgumentException.class, () -> DangerousGoodsProfile.of(
                "UN 1203",
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                -1,
                true
        ));

        assertThrows(IllegalArgumentException.class, () -> DangerousGoodsProfile.of(
                "UN 1203",
                "Gasoline",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.II,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                5,
                true
        ));
    }

    @Test
    void shouldCheckHazardLabel() {
        DangerousGoodsProfile profile = gasolineProfile();

        assertTrue(profile.hasHazardLabel(HazardLabel.LABEL_3_FLAMMABLE_LIQUID));
        assertFalse(profile.hasHazardLabel(HazardLabel.LABEL_8_CORROSIVE));
    }

    @Test
    void shouldNotCheckNullHazardLabel() {
        DangerousGoodsProfile profile = gasolineProfile();

        assertThrows(IllegalArgumentException.class, () -> profile.hasHazardLabel(null));
    }

    @Test
    void shouldCheckUnNumber() {
        DangerousGoodsProfile profile = gasolineProfile();

        assertTrue(profile.isUnNumber("1203"));
        assertTrue(profile.isUnNumber("UN1203"));
        assertTrue(profile.isUnNumber("UN 1203"));
        assertFalse(profile.isUnNumber("UN 1202"));
    }

    @Test
    void shouldExposeAdrCertificateRequirements() {
        DangerousGoodsProfile tankProfile = gasolineProfile();

        DangerousGoodsProfile packagedProfile = DangerousGoodsProfile.of(
                "UN 1202",
                "Diesel fuel",
                AdrClass.CLASS_3_FLAMMABLE_LIQUIDS,
                "F1",
                PackingGroup.III,
                Set.of(HazardLabel.LABEL_3_FLAMMABLE_LIQUID),
                "D/E",
                3,
                false
        );

        assertTrue(tankProfile.requiresAdrBasicCertificate());
        assertTrue(tankProfile.requiresAdrTankCertificate());

        assertTrue(packagedProfile.requiresAdrBasicCertificate());
        assertFalse(packagedProfile.requiresAdrTankCertificate());
    }

    @Test
    void shouldFormatSingleLine() {
        DangerousGoodsProfile profile = gasolineProfile();

        assertEquals("UN 1203 - Gasoline - class 3", profile.formatSingleLine());
    }

    @Test
    void shouldConsiderEquivalentProfilesEqual() {
        DangerousGoodsProfile first = gasolineProfile();
        DangerousGoodsProfile second = gasolineProfile();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldExposeEnumDetails() {
        assertEquals("3", AdrClass.CLASS_3_FLAMMABLE_LIQUIDS.getCode());
        assertTrue(AdrClass.CLASS_1_EXPLOSIVES.isExplosives());
        assertTrue(AdrClass.CLASS_7_RADIOACTIVE_MATERIAL.isRadioactive());

        assertTrue(PackingGroup.I.isHighDanger());
        assertTrue(PackingGroup.II.isMediumDanger());
        assertTrue(PackingGroup.III.isLowDanger());

        assertEquals("2.1", HazardLabel.LABEL_2_1_FLAMMABLE_GAS.getCode());
        assertTrue(HazardLabel.LABEL_2_1_FLAMMABLE_GAS.isGasLabel());
        assertTrue(HazardLabel.LABEL_3_FLAMMABLE_LIQUID.isTankRelevantLabel());
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
}
