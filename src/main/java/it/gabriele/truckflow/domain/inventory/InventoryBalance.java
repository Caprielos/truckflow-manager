package it.gabriele.truckflow.domain.inventory;

import it.gabriele.truckflow.domain.shared.Money;

import java.util.List;
import java.util.Objects;

/**
 * Saldo di un articolo in una posizione di magazzino.
 */
public final class InventoryBalance {

    private final InventoryItem item;
    private final WarehouseLocation location;
    private final double availableQuantity;

    private InventoryBalance(InventoryItem item, WarehouseLocation location, double availableQuantity) {
        if (item == null) {
            throw new IllegalArgumentException("L'articolo saldo magazzino è obbligatorio.");
        }
        if (location == null) {
            throw new IllegalArgumentException("La posizione saldo magazzino è obbligatoria.");
        }
        if (availableQuantity < 0 || Double.isNaN(availableQuantity) || Double.isInfinite(availableQuantity)) {
            throw new IllegalArgumentException("La quantità disponibile deve essere valida e non negativa.");
        }
        this.item = item;
        this.location = location;
        this.availableQuantity = availableQuantity;
    }

    public static InventoryBalance of(InventoryItem item, WarehouseLocation location, double availableQuantity) {
        return new InventoryBalance(item, location, availableQuantity);
    }

    public static InventoryBalance fromMovements(InventoryItem item, WarehouseLocation location, List<InventoryStockMovement> movements) {
        if (movements == null) {
            throw new IllegalArgumentException("I movimenti magazzino sono obbligatori.");
        }
        if (movements.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("I movimenti magazzino non possono contenere null.");
        }
        double quantity = movements.stream()
                .filter(movement -> movement.getItemCode().equals(item.getItemCode()))
                .filter(movement -> movement.getLocation().equals(location))
                .mapToDouble(InventoryStockMovement::signedQuantity)
                .sum();
        if (quantity < 0) {
            throw new IllegalArgumentException("I movimenti producono una giacenza negativa.");
        }
        return of(item, location, quantity);
    }

    public InventoryItem getItem() { return item; }
    public WarehouseLocation getLocation() { return location; }
    public double getAvailableQuantity() { return availableQuantity; }

    public boolean isBelowMinimumStock() {
        return item.isBelowMinimumStock(availableQuantity);
    }

    public Money calculateStockValue() {
        return item.calculateStockValue(availableQuantity);
    }

    public boolean canReserve(double requestedQuantity) {
        if (requestedQuantity <= 0 || Double.isNaN(requestedQuantity) || Double.isInfinite(requestedQuantity)) {
            throw new IllegalArgumentException("La quantità richiesta deve essere maggiore di zero.");
        }
        return availableQuantity >= requestedQuantity;
    }
}
