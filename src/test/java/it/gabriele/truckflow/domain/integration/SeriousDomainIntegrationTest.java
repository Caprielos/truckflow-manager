package it.gabriele.truckflow.domain.integration;

import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.company.CompanyComplianceRules;
import it.gabriele.truckflow.domain.company.CompanyLicense;
import it.gabriele.truckflow.domain.company.CompanyLicenseType;
import it.gabriele.truckflow.domain.company.TransportCompany;
import it.gabriele.truckflow.domain.driver.*;
import it.gabriele.truckflow.domain.fleet.*;
import it.gabriele.truckflow.domain.fuel.FuelCardProvider;
import it.gabriele.truckflow.domain.fuel.FuelConsumptionRules;
import it.gabriele.truckflow.domain.fuel.FuelTransaction;
import it.gabriele.truckflow.domain.loadsecurity.LoadSecuringChecklist;
import it.gabriele.truckflow.domain.loadsecurity.LoadSecuringEquipment;
import it.gabriele.truckflow.domain.loadsecurity.LoadSecuringEquipmentType;
import it.gabriele.truckflow.domain.loadsecurity.LoadSecuringRules;
import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.Weight;
import it.gabriele.truckflow.domain.telematics.TelematicsRules;
import it.gabriele.truckflow.domain.tire.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SeriousDomainIntegrationTest {

    @Test
    void shouldCreateTechnicalVehicleAndCombinationSummary() {
        Vehicle truck = Vehicle.technicalVehicle(
                "truck-001",
                "AB123CD",
                "WDB9634031L765432",
                VehicleUnitType.RIGID_TRUCK,
                VehicleStatus.AVAILABLE,
                tireSpecification(),
                rigidTruckTechnicalSpecification(),
                Notes.empty()
        );
        Vehicle trailer = Vehicle.technicalVehicle(
                "trailer-001",
                "EF456GH",
                "WDB9634031L765433",
                VehicleUnitType.DRAWBAR_TRAILER,
                VehicleStatus.AVAILABLE,
                tireSpecification(),
                drawbarTrailerTechnicalSpecification(),
                Notes.empty()
        );

        VehicleCombination combination = VehicleCombination.withTrailer("combo-001", truck, trailer, Notes.empty());

        assertEquals(VehicleCombinationType.TRUCK_AND_TRAILER, combination.getCombinationType());
        assertEquals(5, combination.calculateTotalAxleCount());
        assertEquals(Weight.ofKilograms(44000), combination.calculateGrossCombinationWeight());
        assertTrue(combination.findNextCertificateDeadline(LocalDate.of(2026, 1, 1)).isPresent());
    }

    @Test
    void shouldUseDriverCertificatesWithRealExpiryDates() {
        Driver driver = Driver.availableWithCertificates(
                "drv-001",
                "Mario Rossi",
                Set.of(DriverLicenseCategory.CE),
                Set.of(DriverProfessionalQualification.CQC_GOODS),
                Set.of(DriverAdrCertificateType.ADR_BASIC, DriverAdrCertificateType.ADR_TANK),
                Set.of(DriverOperationalQualification.INTERNATIONAL_TRANSPORT),
                Notes.empty(),
                List.of(
                        DriverCertificate.of(DriverCertificateType.CQC_GOODS, LocalDate.of(2024, 1, 1), LocalDate.of(2029, 1, 1), "CQC-1", Notes.empty()),
                        DriverCertificate.of(DriverCertificateType.ADR_BASIC, LocalDate.of(2024, 1, 1), LocalDate.of(2029, 1, 1), "ADR-1", Notes.empty()),
                        DriverCertificate.of(DriverCertificateType.INTERNATIONAL_TRANSPORT, LocalDate.of(2024, 1, 1), LocalDate.of(2027, 1, 1), "INT-1", Notes.empty())
                )
        );

        assertTrue(driver.hasValidCertificate(DriverCertificateType.CQC_GOODS, LocalDate.of(2026, 1, 1)));
        assertFalse(driver.hasValidCertificate(DriverCertificateType.CQC_GOODS, LocalDate.of(2030, 1, 1)));
        assertTrue(driver.hasExpiringCertificateWithin(LocalDate.of(2026, 12, 10), 30));
    }

    @Test
    void shouldValidateCompanyWasteAndInternationalLicenses() {
        TransportCompany company = TransportCompany.of(
                "co-001",
                "TruckFlow Transport SRL",
                "IT12345678901",
                List.of(
                        CompanyLicense.of(CompanyLicenseType.ROAD_HAULAGE_REGISTER, null, Notes.empty()),
                        CompanyLicense.of(CompanyLicenseType.REN, null, Notes.empty()),
                        CompanyLicense.of(CompanyLicenseType.COMMUNITY_LICENSE, LocalDate.of(2030, 1, 1), Notes.empty()),
                        CompanyLicense.of(CompanyLicenseType.ENVIRONMENTAL_MANAGERS_REGISTER_CATEGORY_4, LocalDate.of(2028, 1, 1), Notes.empty())
                ),
                Notes.empty()
        );

        assertTrue(CompanyComplianceRules.canOperateInternationalTransport(company, LocalDate.of(2026, 1, 1)));
        assertTrue(CompanyComplianceRules.canTransportCargo(company, CargoCategory.WASTE_NON_DANGEROUS, false, LocalDate.of(2026, 1, 1)));
        assertTrue(CompanyComplianceRules.canTransportCargo(company, CargoCategory.PALLETIZED_DRY_GOODS, true, LocalDate.of(2026, 1, 1)));
    }

    @Test
    void shouldEvaluateLoadSecurityFuelTireAndTelematicsRules() {
        LoadSecuringChecklist checklist = LoadSecuringChecklist.of(List.of(
                LoadSecuringEquipment.of(LoadSecuringEquipmentType.RATCHET_STRAP, 6, 5000),
                LoadSecuringEquipment.of(LoadSecuringEquipmentType.EDGE_PROTECTOR, 8, 0),
                LoadSecuringEquipment.of(LoadSecuringEquipmentType.ANTI_SLIP_MAT, 6, 0)
        ));
        assertTrue(LoadSecuringRules.hasMinimumEquipmentForCargo(checklist, CargoCategory.PALLETIZED_DRY_GOODS, Weight.ofKilograms(12000)));

        FuelTransaction previous = FuelTransaction.of("truck-001", LocalDateTime.of(2026, 1, 1, 8, 0), 300, Money.of("1.70", "EUR"), 100000, FuelCardProvider.DKV);
        FuelTransaction current = FuelTransaction.of("truck-001", LocalDateTime.of(2026, 1, 2, 8, 0), 250, Money.of("1.72", "EUR"), 100600, FuelCardProvider.DKV);
        assertTrue(FuelConsumptionRules.isConsumptionAnomaly(previous, current, 3.0, 10));

        Tire tire = Tire.of("rfid-001", TireStatus.IN_USE, 3.8, 100000, 120000);
        assertTrue(TireRules.shouldScheduleReplacement(tire));
        TireInstallation installation = TireInstallation.active(tire, "trailer-001", WheelPosition.of(2, WheelSide.RIGHT, WheelSlot.OUTER), LocalDate.of(2026, 1, 1), 100000);
        assertEquals(20000, installation.calculateKilometersMounted(120000));

        assertTrue(TelematicsRules.isFuelDropAnomaly(80, 60, 15));
        assertTrue(TelematicsRules.isSpeeding(91, 80, 5));
    }

    private static TireSpecification tireSpecification() {
        return TireSpecification.of("Michelin", "X Multi", "385/65 R22.5", 160, "J");
    }

    private static VehicleTechnicalSpecification rigidTruckTechnicalSpecification() {
        return VehicleTechnicalSpecification.of(
                VehicleMassSpecification.of(Weight.ofKilograms(26000), Weight.ofKilograms(11000), Weight.ofKilograms(18000), null),
                VehicleDimensionSpecification.of(Dimension.ofMeters(8.0, 2.55, 3.7), Dimension.ofMeters(7.2, 2.48, 2.4), 1.1, 18),
                VehicleAxleSpecification.of(
                        List.of(
                                VehicleAxle.of(1, WheelConfiguration.SINGLE, false, AxleSteeringType.STEERING),
                                VehicleAxle.of(2, WheelConfiguration.TWIN, false, AxleSteeringType.FIXED),
                                VehicleAxle.of(3, WheelConfiguration.SINGLE, true, AxleSteeringType.SELF_STEERING)
                        ),
                        SuspensionType.PNEUMATIC,
                        BrakeType.DISC,
                        Set.of(BrakeSafetySystem.ABS, BrakeSafetySystem.EBS),
                        "BPW"
                ),
                VehicleCouplingSpecification.none(),
                VehicleBodyConfiguration.baseOnly(VehicleBodyBaseType.CURTAIN_SIDE),
                List.of(VehicleCertificate.of(VehicleCertificateType.ROADWORTHINESS_INSPECTION, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31), Notes.empty()))
        );
    }

    private static VehicleTechnicalSpecification drawbarTrailerTechnicalSpecification() {
        return VehicleTechnicalSpecification.of(
                VehicleMassSpecification.of(Weight.ofKilograms(18000), Weight.ofKilograms(5000), null, null),
                VehicleDimensionSpecification.of(Dimension.ofMeters(7.8, 2.55, 3.7), Dimension.ofMeters(7.2, 2.48, 2.4), 1.1, 18),
                VehicleAxleSpecification.of(
                        List.of(
                                VehicleAxle.of(1, WheelConfiguration.SINGLE, false, AxleSteeringType.FIXED),
                                VehicleAxle.of(2, WheelConfiguration.SINGLE, false, AxleSteeringType.SELF_STEERING)
                        ),
                        SuspensionType.PNEUMATIC,
                        BrakeType.DISC,
                        Set.of(BrakeSafetySystem.ABS, BrakeSafetySystem.EBS),
                        "SAF"
                ),
                VehicleCouplingSpecification.drawbar(1.8, "DIN 40"),
                VehicleBodyConfiguration.baseOnly(VehicleBodyBaseType.CURTAIN_SIDE),
                List.of(VehicleCertificate.of(VehicleCertificateType.ROADWORTHINESS_INSPECTION, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 11, 30), Notes.empty()))
        );
    }
}
