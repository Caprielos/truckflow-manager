package it.gabriele.truckflow.domain.inventory;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Movimento reale di magazzino collegabile a manutenzione, missione, acquisto o trasferimento.
 */
public final class InventoryStockMovement {

    private static final int MAX_CODE_LENGTH = 50;

    private final String movementCode;
    private final String itemCode;
    private final WarehouseLocation location;
    private final StockMovementType type;
    private final double quantity;
    private final Money unitCost;
    private final LocalDateTime occurredAt;
    private final String referenceNumber;
    private final Notes notes;

    private InventoryStockMovement(
            String movementCode,
            String itemCode,
            WarehouseLocation location,
            StockMovementType type,
            double quantity,
            Money unitCost,
            LocalDateTime occurredAt,
            String referenceNumber,
            Notes notes
    ) {
        this.movementCode = validateCode(movementCode, "Il codice movimento magazzino è obbligatorio.");
        this.itemCode = validateCode(itemCode, "Il codice articolo movimento è obbligatorio.");
        if (location == null) {
            throw new IllegalArgumentException("La posizione movimento magazzino è obbligatoria.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Il tipo movimento magazzino è obbligatorio.");
        }
        if (quantity <= 0 || Double.isNaN(quantity) || Double.isInfinite(quantity)) {
            throw new IllegalArgumentException("La quantità movimento deve essere maggiore di zero.");
        }
        if (unitCost == null) {
            throw new IllegalArgumentException("Il costo unitario movimento è obbligatorio.");
        }
        if (occurredAt == null) {
            throw new IllegalArgumentException("La data/ora movimento magazzino è obbligatoria.");
        }
        this.referenceNumber = validateCode(referenceNumber, "Il riferimento movimento magazzino è obbligatorio.");
        if (notes == null) {
            throw new IllegalArgumentException("Le note movimento magazzino sono obbligatorie.");
        }
        this.location = location;
        this.type = type;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.occurredAt = occurredAt;
        this.notes = notes;
    }

    public static InventoryStockMovement of(
            String movementCode,
            String itemCode,
            WarehouseLocation location,
            StockMovementType type,
            double quantity,
            Money unitCost,
            LocalDateTime occurredAt,
            String referenceNumber,
            Notes notes
    ) {
        return new InventoryStockMovement(movementCode, itemCode, location, type, quantity, unitCost, occurredAt, referenceNumber, notes);
    }

    private static String validateCode(String code, String message) {
        if (code == null) {
            throw new IllegalArgumentException(message);
        }
        String normalized = code.trim().toUpperCase();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        if (normalized.length() > MAX_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
        }
        if (!normalized.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice può contenere solo lettere, numeri, trattini e underscore.");
        }
        return normalized;
    }

    public String getMovementCode() { return movementCode; }
    public String getItemCode() { return itemCode; }
    public WarehouseLocation getLocation() { return location; }
    public StockMovementType getType() { return type; }
    public double getQuantity() { return quantity; }
    public Money getUnitCost() { return unitCost; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
    public String getReferenceNumber() { return referenceNumber; }
    public Notes getNotes() { return notes; }

    public double signedQuantity() {
        return quantity * type.getSign();
    }

    public boolean isRelatedTo(String referenceNumber) {
        if (referenceNumber == null) {
            throw new IllegalArgumentException("Il riferimento da cercare è obbligatorio.");
        }
        return this.referenceNumber.equals(referenceNumber.trim().toUpperCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryStockMovement that)) return false;
        return Double.compare(quantity, that.quantity) == 0
                && movementCode.equals(that.movementCode)
                && itemCode.equals(that.itemCode)
                && location.equals(that.location)
                && type == that.type
                && unitCost.equals(that.unitCost)
                && occurredAt.equals(that.occurredAt)
                && referenceNumber.equals(that.referenceNumber)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movementCode, itemCode, location, type, quantity, unitCost, occurredAt, referenceNumber, notes);
    }
}
