package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.Weight;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RealisticFleetModelTest {

    @Test
    void shouldCalculatePayloadWeightClassAndEpalCapacity() {
        VehicleMassSpecification mass = VehicleMassSpecification.of(
                Weight.ofKilograms(44000),
                Weight.ofKilograms(15000),
                Weight.ofKilograms(30000),
                null
        );

        VehicleDimensionSpecification dimensions = VehicleDimensionSpecification.of(
                Dimension.ofMeters(13.6, 2.55, 4.0),
                Dimension.ofMeters(13.6, 2.48, 2.7),
                1.2,
                null
        );

        assertEquals(Weight.ofKilograms(29000), mass.calculateNetPayload());
        assertEquals(VehicleWeightClass.HEAVY_OVER_12T, mass.calculateWeightClass());
        assertEquals(33, dimensions.estimateEpalCapacity());
    }

    @Test
    void shouldModelCurtainSideMegaWithTailLift() {
        VehicleBodyConfiguration configuration = VehicleBodyConfiguration.of(
                VehicleBodyBaseType.CURTAIN_SIDE,
                List.of(VehicleLoadingEquipment.of(
                        VehicleLoadingEquipmentType.TAIL_LIFT,
                        VehicleEquipmentPosition.REAR,
                        Weight.ofKilograms(1500),
                        Notes.empty()
                )),
                Set.of(VehicleTechnicalFeature.MEGA_VOLUME, VehicleTechnicalFeature.LOW_DECK)
        );

        assertTrue(configuration.isCargoBody());
        assertTrue(configuration.hasEquipment(VehicleLoadingEquipmentType.TAIL_LIFT));
        assertTrue(configuration.hasFeature(VehicleTechnicalFeature.MEGA_VOLUME));
    }

    @Test
    void shouldCalculateVehicleCertificateDeadlineStatus() {
        VehicleCertificate certificate = VehicleCertificate.of(
                VehicleCertificateType.ATP,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 6, 30),
                Notes.empty()
        );

        assertTrue(certificate.isValidOn(LocalDate.of(2026, 6, 1)));
        assertEquals(DeadlineStatus.EXPIRING_SOON, certificate.calculateStatus(LocalDate.of(2026, 6, 10), 30));
        assertEquals(DeadlineStatus.EXPIRED, certificate.calculateStatus(LocalDate.of(2026, 7, 1), 30));
    }

    @Test
    void shouldCheckAutotrenoGrossWeightAndTowRatio() {
        VehicleMassSpecification truck = VehicleMassSpecification.of(
                Weight.ofKilograms(26000),
                Weight.ofKilograms(11000),
                Weight.ofKilograms(18000),
                null
        );
        VehicleMassSpecification trailer = VehicleMassSpecification.of(
                Weight.ofKilograms(18000),
                Weight.ofKilograms(5000),
                null,
                null
        );

        assertEquals(
                Weight.ofKilograms(44000),
                VehicleCombinationTechnicalRules.calculateGrossCombinationWeight(truck, trailer)
        );
        assertTrue(VehicleCombinationTechnicalRules.canTow(truck, trailer));
        assertTrue(VehicleCombinationTechnicalRules.isWithinGrossWeightLimit(
                truck,
                trailer,
                VehicleCombinationLegalLimitProfile.italianStandardAutotreno()
        ));
    }
}
