package it.gabriele.truckflow.domain.driver;

import it.gabriele.truckflow.domain.cargo.CargoLoad;
import it.gabriele.truckflow.domain.cargo.CargoLoadRules;
import it.gabriele.truckflow.domain.fleet.Vehicle;
import it.gabriele.truckflow.domain.fleet.VehicleCombination;
import it.gabriele.truckflow.domain.fleet.VehicleType;
import it.gabriele.truckflow.domain.shipment.Shipment;

/**
 * Contiene regole di dominio relative all'assegnazione degli autisti.
 */
public final class DriverRules {

    private DriverRules() {
    }

    public static boolean canBeAssigned(Driver driver) {
        validateDriver(driver);

        return driver.canBeAssigned();
    }

    public static boolean hasRequiredLicenseForVehicleCombination(
            Driver driver,
            VehicleCombination vehicleCombination
    ) {
        validateDriver(driver);
        validateVehicleCombination(vehicleCombination);

        if (vehicleCombination.hasTrailer()) {
            return driver.canDriveVehicleCombinationWithTrailer();
        }

        Vehicle poweredUnit = vehicleCombination.getPoweredUnit();

        if (poweredUnit.getType() == VehicleType.VAN) {
            return driver.canDriveLightVehicle();
        }

        if (poweredUnit.getType() == VehicleType.RIGID_TRUCK
                || poweredUnit.getType() == VehicleType.REFRIGERATED_TRUCK) {
            return driver.canDriveRigidTruck();
        }

        return false;
    }

    public static boolean hasRequiredProfessionalQualificationForGoodsTransport(Driver driver) {
        validateDriver(driver);

        return driver.hasGoodsCqc();
    }

    public static boolean hasRequiredAdrCertificatesForCargoLoad(
            Driver driver,
            CargoLoad cargoLoad
    ) {
        validateDriver(driver);
        validateCargoLoad(cargoLoad);

        if (!CargoLoadRules.containsHazardousMaterial(cargoLoad)) {
            return true;
        }

        if (!driver.hasAdrBasicCertificate()) {
            return false;
        }

        if (CargoLoadRules.requiresAdrTankTransport(cargoLoad)
                && !driver.hasAdrCertificate(DriverAdrCertificateType.ADR_TANK)) {
            return false;
        }

        if (CargoLoadRules.containsExplosives(cargoLoad)
                && !driver.hasAdrCertificate(DriverAdrCertificateType.ADR_CLASS_1_EXPLOSIVES)) {
            return false;
        }

        if (CargoLoadRules.containsRadioactiveMaterial(cargoLoad)
                && !driver.hasAdrCertificate(DriverAdrCertificateType.ADR_CLASS_7_RADIOACTIVE)) {
            return false;
        }

        return true;
    }

    public static boolean hasRequiredOperationalQualificationsForShipment(
            Driver driver,
            Shipment shipment
    ) {
        validateDriver(driver);
        validateShipment(shipment);

        if (shipment.requiresTemperatureControlledTransport()
                && !driver.hasOperationalQualification(DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT)) {
            return false;
        }

        if (shipment.isInternational()
                && !driver.hasOperationalQualification(DriverOperationalQualification.INTERNATIONAL_TRANSPORT)) {
            return false;
        }

        return true;
    }

    public static boolean canDriveVehicleCombination(
            Driver driver,
            VehicleCombination vehicleCombination
    ) {
        validateDriver(driver);
        validateVehicleCombination(vehicleCombination);

        return driver.canBeAssigned()
                && vehicleCombination.canBeAssigned()
                && hasRequiredLicenseForVehicleCombination(driver, vehicleCombination)
                && hasRequiredProfessionalQualificationForGoodsTransport(driver);
    }

    public static boolean canBeAssignedToShipment(
            Driver driver,
            VehicleCombination vehicleCombination,
            Shipment shipment
    ) {
        validateDriver(driver);
        validateVehicleCombination(vehicleCombination);
        validateShipment(shipment);

        return canDriveVehicleCombination(driver, vehicleCombination)
                && hasRequiredAdrCertificatesForCargoLoad(driver, shipment.getCargoLoad())
                && hasRequiredOperationalQualificationsForShipment(driver, shipment);
    }

    private static void validateDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("L'autista è obbligatorio.");
        }
    }

    private static void validateVehicleCombination(VehicleCombination vehicleCombination) {
        if (vehicleCombination == null) {
            throw new IllegalArgumentException("La combinazione veicolare è obbligatoria.");
        }
    }

    private static void validateCargoLoad(CargoLoad cargoLoad) {
        if (cargoLoad == null) {
            throw new IllegalArgumentException("Il carico è obbligatorio.");
        }
    }

    private static void validateShipment(Shipment shipment) {
        if (shipment == null) {
            throw new IllegalArgumentException("La spedizione è obbligatoria.");
        }
    }
}
