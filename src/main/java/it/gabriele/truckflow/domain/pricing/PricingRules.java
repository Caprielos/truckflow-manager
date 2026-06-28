package it.gabriele.truckflow.domain.pricing;

import it.gabriele.truckflow.domain.cargo.CargoLoad;
import it.gabriele.truckflow.domain.cargo.CargoLoadRules;
import it.gabriele.truckflow.domain.shipment.Shipment;

/**
 * Regole di dominio per il pricing.
 */
public final class PricingRules {

    private PricingRules() {
    }

    public static boolean requiresAdrSurcharge(CargoLoad cargoLoad) {
        validateCargoLoad(cargoLoad);

        return CargoLoadRules.requiresAdrTransport(cargoLoad);
    }

    public static boolean requiresTemperatureControlSurcharge(CargoLoad cargoLoad) {
        validateCargoLoad(cargoLoad);

        return CargoLoadRules.requiresTemperatureControlledTransport(cargoLoad);
    }

    public static boolean requiresInternationalSurcharge(Shipment shipment) {
        validateShipment(shipment);

        return shipment.isInternational();
    }

    public static boolean hasDiscounts(PriceBreakdown priceBreakdown) {
        validatePriceBreakdown(priceBreakdown);

        return priceBreakdown.hasDiscounts();
    }

    public static boolean hasSurcharges(PriceBreakdown priceBreakdown) {
        validatePriceBreakdown(priceBreakdown);

        return priceBreakdown.hasSurcharges();
    }

    public static boolean hasBaseFreightLine(PriceBreakdown priceBreakdown) {
        validatePriceBreakdown(priceBreakdown);

        return priceBreakdown.hasLineType(PricingLineType.BASE_FREIGHT);
    }

    public static boolean hasFuelSurchargeLine(PriceBreakdown priceBreakdown) {
        validatePriceBreakdown(priceBreakdown);

        return priceBreakdown.hasLineType(PricingLineType.FUEL_SURCHARGE);
    }

    public static boolean hasTollChargeLine(PriceBreakdown priceBreakdown) {
        validatePriceBreakdown(priceBreakdown);

        return priceBreakdown.hasLineType(PricingLineType.TOLL_CHARGE);
    }

    public static boolean hasVehicleWearChargeLine(PriceBreakdown priceBreakdown) {
        validatePriceBreakdown(priceBreakdown);

        return priceBreakdown.hasLineType(PricingLineType.VEHICLE_WEAR_CHARGE);
    }

    public static boolean hasAdrSurchargeLine(PriceBreakdown priceBreakdown) {
        validatePriceBreakdown(priceBreakdown);

        return priceBreakdown.hasLineType(PricingLineType.ADR_SURCHARGE);
    }

    public static boolean hasTemperatureControlSurchargeLine(PriceBreakdown priceBreakdown) {
        validatePriceBreakdown(priceBreakdown);

        return priceBreakdown.hasLineType(PricingLineType.TEMPERATURE_CONTROL_SURCHARGE);
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

    private static void validatePriceBreakdown(PriceBreakdown priceBreakdown) {
        if (priceBreakdown == null) {
            throw new IllegalArgumentException("Il dettaglio prezzo è obbligatorio.");
        }
    }
}
