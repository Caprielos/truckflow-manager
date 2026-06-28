package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.fuel.FuelTransaction;
import it.gabriele.truckflow.domain.pricing.RouteCostEstimate;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Voce costo collegata a una missione: carburante, pedaggi, autista, gomme, manutenzione, assicurazione, ammortamento.
 */
public final class MissionCostLine {

    private static final int MAX_CODE_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private final String lineCode;
    private final MissionCostType type;
    private final String description;
    private final Money amount;
    private final Notes notes;

    private MissionCostLine(String lineCode, MissionCostType type, String description, Money amount, Notes notes) {
        this.lineCode = validateCode(lineCode, "Il codice costo missione è obbligatorio.");
        if (type == null) {
            throw new IllegalArgumentException("Il tipo costo missione è obbligatorio.");
        }
        this.description = validateDescription(description);
        if (amount == null) {
            throw new IllegalArgumentException("L'importo costo missione è obbligatorio.");
        }
        if (amount.getAmount().signum() == 0) {
            throw new IllegalArgumentException("L'importo costo missione deve essere maggiore di zero.");
        }
        if (notes == null) {
            throw new IllegalArgumentException("Le note costo missione sono obbligatorie.");
        }
        this.type = type;
        this.amount = amount;
        this.notes = notes;
    }

    public static MissionCostLine of(String lineCode, MissionCostType type, String description, Money amount, Notes notes) {
        return new MissionCostLine(lineCode, type, description, amount, notes);
    }

    public static MissionCostLine fuelFromTransaction(String lineCode, FuelTransaction transaction, Notes notes) {
        if (transaction == null) {
            throw new IllegalArgumentException("La transazione carburante è obbligatoria.");
        }
        BigDecimal amount = transaction.getPricePerLiter().getAmount().multiply(BigDecimal.valueOf(transaction.getLiters()));
        Money total = Money.of(amount, transaction.getPricePerLiter().getCurrency());
        return of(lineCode, MissionCostType.FUEL, "Rifornimento " + transaction.getCardProvider(), total, notes);
    }

    public static MissionCostLine fuelFromEstimate(String lineCode, RouteCostEstimate estimate, Notes notes) {
        validateEstimate(estimate);
        return of(lineCode, MissionCostType.FUEL, "Carburante stimato da " + estimate.getSource(),
                estimate.getEstimatedFuelCost(), notes);
    }

    public static MissionCostLine tollsFromEstimate(String lineCode, RouteCostEstimate estimate, Notes notes) {
        validateEstimate(estimate);
        return of(lineCode, MissionCostType.TOLL, "Pedaggi stimati da " + estimate.getSource(),
                estimate.getEstimatedTollCost(), notes);
    }

    public static MissionCostLine vehicleWearFromEstimate(String lineCode, RouteCostEstimate estimate, Notes notes) {
        validateEstimate(estimate);
        return of(lineCode, MissionCostType.VEHICLE_DEPRECIATION, "Usura mezzo stimata da " + estimate.getSource(),
                estimate.getEstimatedVehicleWearCost(), notes);
    }

    public static MissionCostLine depreciation(String lineCode, FleetAssetPurchase asset, int months, Notes notes) {
        if (asset == null) {
            throw new IllegalArgumentException("Il bene aziendale è obbligatorio.");
        }
        MissionCostType type = switch (asset.getCategory()) {
            case SEMI_TRAILER, DRAWBAR_TRAILER, CENTER_AXLE_TRAILER -> MissionCostType.TRAILER_DEPRECIATION;
            case BODY_EQUIPMENT, REFRIGERATION_UNIT, LOADING_EQUIPMENT -> MissionCostType.BODY_EQUIPMENT_DEPRECIATION;
            default -> MissionCostType.VEHICLE_DEPRECIATION;
        };
        return of(lineCode, type, "Ammortamento " + asset.getAssetCode(), asset.calculateDepreciationForMonths(months), notes);
    }

    private static void validateEstimate(RouteCostEstimate estimate) {
        if (estimate == null) {
            throw new IllegalArgumentException("La stima costo percorso è obbligatoria.");
        }
    }

    private static String validateCode(String code, String nullMessage) {
        if (code == null) {
            throw new IllegalArgumentException(nullMessage);
        }
        String normalized = code.trim().toUpperCase();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(nullMessage);
        }
        if (normalized.length() > MAX_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
        }
        if (!normalized.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice può contenere solo lettere, numeri, trattini e underscore.");
        }
        return normalized;
    }

    private static String validateDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("La descrizione costo missione è obbligatoria.");
        }
        String normalized = description.trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("La descrizione costo missione non può essere vuota.");
        }
        if (normalized.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("La descrizione costo missione non può superare "
                    + MAX_DESCRIPTION_LENGTH + " caratteri.");
        }
        return normalized;
    }

    public String getLineCode() {
        return lineCode;
    }

    public MissionCostType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Money getAmount() {
        return amount;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean isVariableOperationalCost() {
        return type.isVariableOperationalCost();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MissionCostLine that)) return false;
        return lineCode.equals(that.lineCode)
                && type == that.type
                && description.equals(that.description)
                && amount.equals(that.amount)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineCode, type, description, amount, notes);
    }
}
