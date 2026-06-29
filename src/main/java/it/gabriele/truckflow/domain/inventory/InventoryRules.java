package it.gabriele.truckflow.domain.inventory;

/**
 * Regole di dominio per magazzino e ricambi.
 */
public final class InventoryRules {

    private InventoryRules() {
    }

    public static boolean canReserve(InventoryBalance balance, double quantity) {
        validateBalance(balance);
        return balance.canReserve(quantity);
    }

    public static boolean shouldReorder(InventoryBalance balance) {
        validateBalance(balance);
        return balance.isBelowMinimumStock();
    }

    public static boolean safetyCriticalItemShouldHaveStock(InventoryBalance balance) {
        validateBalance(balance);
        return balance.getItem().getType().isSafetyCritical()
                && balance.getAvailableQuantity() > 0;
    }

    private static void validateBalance(InventoryBalance balance) {
        if (balance == null) {
            throw new IllegalArgumentException("Il saldo magazzino è obbligatorio.");
        }
    }
}
