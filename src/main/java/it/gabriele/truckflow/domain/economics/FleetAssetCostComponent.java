package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.util.Objects;

/**
 * Singola componente di costo di un acquisto flotta: mezzo, rimorchio, allestimento, gomme, telematica, pratiche.
 */
public final class FleetAssetCostComponent {

    private static final int MAX_CODE_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private final String componentCode;
    private final FleetAssetCostComponentType type;
    private final String description;
    private final VatBreakdown price;
    private final String assignedVehicleFleetNumber;
    private final Notes notes;

    private FleetAssetCostComponent(
            String componentCode,
            FleetAssetCostComponentType type,
            String description,
            VatBreakdown price,
            String assignedVehicleFleetNumber,
            Notes notes
    ) {
        this.componentCode = validateCode(componentCode, "Il codice componente acquisto è obbligatorio.");
        if (type == null) {
            throw new IllegalArgumentException("Il tipo componente acquisto è obbligatorio.");
        }
        this.description = validateDescription(description);
        if (price == null) {
            throw new IllegalArgumentException("Il prezzo componente acquisto è obbligatorio.");
        }
        this.assignedVehicleFleetNumber = normalizeOptionalCode(assignedVehicleFleetNumber);
        if (notes == null) {
            throw new IllegalArgumentException("Le note componente acquisto sono obbligatorie.");
        }
        this.type = type;
        this.price = price;
        this.notes = notes;
    }

    public static FleetAssetCostComponent of(
            String componentCode,
            FleetAssetCostComponentType type,
            String description,
            VatBreakdown price,
            String assignedVehicleFleetNumber,
            Notes notes
    ) {
        return new FleetAssetCostComponent(componentCode, type, description, price, assignedVehicleFleetNumber, notes);
    }

    public static FleetAssetCostComponent taxableNet(
            String componentCode,
            FleetAssetCostComponentType type,
            String description,
            Money netAmount,
            VatRate vatRate,
            String assignedVehicleFleetNumber,
            Notes notes
    ) {
        return of(componentCode, type, description,
                VatBreakdown.taxableFromNet(netAmount, vatRate), assignedVehicleFleetNumber, notes);
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

    private static String normalizeOptionalCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        return validateCode(code, "Il codice veicolo assegnato non può essere vuoto.");
    }

    private static String validateDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("La descrizione componente acquisto è obbligatoria.");
        }
        String normalized = description.trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("La descrizione componente acquisto non può essere vuota.");
        }
        if (normalized.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("La descrizione componente acquisto non può superare "
                    + MAX_DESCRIPTION_LENGTH + " caratteri.");
        }
        return normalized;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public FleetAssetCostComponentType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public VatBreakdown getPrice() {
        return price;
    }

    public String getAssignedVehicleFleetNumber() {
        return assignedVehicleFleetNumber;
    }

    public boolean isAssignedToVehicle() {
        return assignedVehicleFleetNumber != null;
    }

    public Notes getNotes() {
        return notes;
    }

    public Money getNetAmount() {
        return price.getNetAmount();
    }

    public Money getVatAmount() {
        return price.getVatAmount();
    }

    public Money getGrossAmount() {
        return price.getGrossAmount();
    }

    public Money calculateAccountingCost() {
        return price.calculateAccountingCost();
    }

    public Money calculateRecoverableVatAmount() {
        return price.calculateRecoverableVatAmount();
    }

    public boolean isVehicleUnitCost() {
        return type.isVehicleUnit();
    }

    public boolean isBodyOrEquipmentCost() {
        return type.isBodyOrEquipment();
    }

    public boolean isTireCost() {
        return type.isTireRelated();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FleetAssetCostComponent that)) return false;
        return componentCode.equals(that.componentCode)
                && type == that.type
                && description.equals(that.description)
                && price.equals(that.price)
                && Objects.equals(assignedVehicleFleetNumber, that.assignedVehicleFleetNumber)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentCode, type, description, price, assignedVehicleFleetNumber, notes);
    }
}
