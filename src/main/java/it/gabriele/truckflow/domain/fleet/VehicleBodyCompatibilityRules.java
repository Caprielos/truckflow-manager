package it.gabriele.truckflow.domain.fleet;

import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.cargo.CargoItem;
import it.gabriele.truckflow.domain.cargo.CargoLoad;
import it.gabriele.truckflow.domain.cargo.DangerousGoodsProfile;

/**
 * Contiene regole per verificare se l'allestimento del mezzo è compatibile con il carico.
 */
public final class VehicleBodyCompatibilityRules {

    private VehicleBodyCompatibilityRules() {
    }

    public static boolean isBodyCompatibleWithCargoLoad(
            VehicleCombination vehicleCombination,
            CargoLoad cargoLoad
    ) {
        validateVehicleCombination(vehicleCombination);
        validateCargoLoad(cargoLoad);

        return supportsCargoCategories(vehicleCombination, cargoLoad)
                && supportsTemperatureRequirements(vehicleCombination, cargoLoad)
                && supportsAdrTankRequirements(vehicleCombination, cargoLoad);
    }

    public static boolean supportsCargoCategories(
            VehicleCombination vehicleCombination,
            CargoLoad cargoLoad
    ) {
        validateVehicleCombination(vehicleCombination);
        validateCargoLoad(cargoLoad);

        VehicleBodyType bodyType = vehicleCombination.getCargoUnit().getBodyType();

        return cargoLoad.getItems().stream()
                .allMatch(item -> isBodyCompatibleWithCargoItem(bodyType, item));
    }

    public static boolean supportsTemperatureRequirements(
            VehicleCombination vehicleCombination,
            CargoLoad cargoLoad
    ) {
        validateVehicleCombination(vehicleCombination);
        validateCargoLoad(cargoLoad);

        if (!cargoLoad.requiresTemperatureControl()) {
            return true;
        }

        return vehicleCombination.supportsTemperatureControl();
    }

    public static boolean supportsAdrTankRequirements(
            VehicleCombination vehicleCombination,
            CargoLoad cargoLoad
    ) {
        validateVehicleCombination(vehicleCombination);
        validateCargoLoad(cargoLoad);

        if (!cargoLoad.requiresAdrTankTransport()) {
            return true;
        }

        Vehicle cargoUnit = vehicleCombination.getCargoUnit();

        if (containsGasDangerousGoods(cargoLoad) || cargoLoad.hasCategory(CargoCategory.GAS)) {
            return cargoUnit.hasGasTankBody();
        }

        if (containsFuelDangerousGoods(cargoLoad) || cargoLoad.hasCategory(CargoCategory.FUEL)) {
            return cargoUnit.hasFuelTankBody();
        }

        return cargoUnit.hasTankBody();
    }

    private static boolean isBodyCompatibleWithCargoItem(
            VehicleBodyType bodyType,
            CargoItem item
    ) {
        CargoCategory category = item.getCategory();

        return switch (category) {
            case GENERAL, PALLETIZED_DRY_GOODS -> isGeneralCargoBody(bodyType);
            case FOOD -> isFoodCargoBody(bodyType);
            case REFRIGERATED_FOOD, PHARMACEUTICAL, TEMPERATURE_CONTROLLED_GOODS -> isTemperatureControlledBody(bodyType);
            case FRAGILE, ELECTRONICS, HIGH_VALUE_GOODS -> isProtectedCargoBody(bodyType);
            case HAZARDOUS_MATERIAL, DANGEROUS_GOODS -> isHazardousCargoBody(bodyType);
            case OVERSIZED, MACHINERY -> isOversizedCargoBody(bodyType);
            case VEHICLES -> bodyType == VehicleBodyType.CAR_TRANSPORTER
                    || bodyType == VehicleBodyType.FLATBED
                    || bodyType == VehicleBodyType.LOW_LOADER;
            case LIQUID -> bodyType == VehicleBodyType.TANK_LIQUID
                    || bodyType == VehicleBodyType.FOOD_GRADE_TANK;
            case FOOD_GRADE_LIQUID -> bodyType == VehicleBodyType.FOOD_GRADE_TANK
                    || bodyType == VehicleBodyType.TANK_LIQUID;
            case FUEL -> bodyType == VehicleBodyType.TANK_FUEL;
            case GAS -> bodyType == VehicleBodyType.TANK_GAS;
            case CONSTRUCTION_MATERIAL, BULK_INERT_GOODS, CONCRETE -> bodyType == VehicleBodyType.FLATBED
                    || bodyType == VehicleBodyType.FIXED_OPEN_BOX
                    || bodyType == VehicleBodyType.TIPPER
                    || bodyType == VehicleBodyType.REAR_TIPPER
                    || bodyType == VehicleBodyType.THREE_WAY_TIPPER
                    || bodyType == VehicleBodyType.CURTAIN_SIDE;
            case BULK_DRY, AGRICULTURAL_BULK, HAY_BALES -> bodyType == VehicleBodyType.SILO
                    || bodyType == VehicleBodyType.WALKING_FLOOR
                    || bodyType == VehicleBodyType.TIPPER
                    || bodyType == VehicleBodyType.REAR_TIPPER
                    || bodyType == VehicleBodyType.THREE_WAY_TIPPER
                    || bodyType == VehicleBodyType.FLATBED;
            case WASTE_NON_DANGEROUS, WASTE_DANGEROUS -> bodyType == VehicleBodyType.WALKING_FLOOR
                    || bodyType == VehicleBodyType.TIPPER
                    || bodyType == VehicleBodyType.REAR_TIPPER
                    || bodyType == VehicleBodyType.THREE_WAY_TIPPER
                    || bodyType == VehicleBodyType.HOOKLIFT_CHASSIS;
            case LIVESTOCK -> bodyType == VehicleBodyType.LIVESTOCK;
            case CONTAINERIZED_GOODS -> bodyType == VehicleBodyType.CONTAINER_CHASSIS;
            case COILS -> bodyType == VehicleBodyType.COIL_CARRIER;
        };
    }

    private static boolean isGeneralCargoBody(VehicleBodyType bodyType) {
        return bodyType == VehicleBodyType.VAN_BODY
                || bodyType == VehicleBodyType.BOX
                || bodyType == VehicleBodyType.DRY_BOX
                || bodyType == VehicleBodyType.CURTAIN_SIDE
                || bodyType == VehicleBodyType.ISOTHERMAL_BOX
                || bodyType == VehicleBodyType.REFRIGERATED_BOX;
    }

    private static boolean isFoodCargoBody(VehicleBodyType bodyType) {
        return bodyType == VehicleBodyType.BOX
                || bodyType == VehicleBodyType.DRY_BOX
                || bodyType == VehicleBodyType.ISOTHERMAL_BOX
                || bodyType == VehicleBodyType.REFRIGERATED_BOX;
    }

    private static boolean isTemperatureControlledBody(VehicleBodyType bodyType) {
        return bodyType == VehicleBodyType.REFRIGERATED_BOX
                || bodyType == VehicleBodyType.ISOTHERMAL_BOX;
    }

    private static boolean isProtectedCargoBody(VehicleBodyType bodyType) {
        return bodyType == VehicleBodyType.VAN_BODY
                || bodyType == VehicleBodyType.BOX
                || bodyType == VehicleBodyType.DRY_BOX
                || bodyType == VehicleBodyType.REFRIGERATED_BOX;
    }

    private static boolean isHazardousCargoBody(VehicleBodyType bodyType) {
        return bodyType == VehicleBodyType.BOX
                || bodyType == VehicleBodyType.DRY_BOX
                || bodyType == VehicleBodyType.CURTAIN_SIDE
                || bodyType == VehicleBodyType.TANK_LIQUID
                || bodyType == VehicleBodyType.TANK_FUEL
                || bodyType == VehicleBodyType.TANK_GAS
                || bodyType == VehicleBodyType.SILO;
    }

    private static boolean isOversizedCargoBody(VehicleBodyType bodyType) {
        return bodyType == VehicleBodyType.FLATBED
                || bodyType == VehicleBodyType.FLATBED_WITH_RAMPS
                || bodyType == VehicleBodyType.LOW_LOADER
                || bodyType == VehicleBodyType.EXTENDABLE_FLATBED;
    }

    private static boolean containsGasDangerousGoods(CargoLoad cargoLoad) {
        return cargoLoad.getDangerousGoodsProfiles().stream()
                .anyMatch(DangerousGoodsProfile::isGas);
    }

    private static boolean containsFuelDangerousGoods(CargoLoad cargoLoad) {
        return cargoLoad.getDangerousGoodsProfiles().stream()
                .anyMatch(profile -> profile.isUnNumber("UN 1202")
                        || profile.isUnNumber("UN 1203"));
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
}
