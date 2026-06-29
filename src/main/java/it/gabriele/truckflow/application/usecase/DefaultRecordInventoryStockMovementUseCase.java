package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.RecordInventoryStockMovementUseCase;
import it.gabriele.truckflow.application.port.out.InventoryBalanceRepository;
import it.gabriele.truckflow.application.port.out.InventoryItemRepository;
import it.gabriele.truckflow.application.port.out.InventoryStockMovementRepository;
import it.gabriele.truckflow.application.port.out.WarehouseLocationRepository;
import it.gabriele.truckflow.domain.inventory.InventoryBalance;
import it.gabriele.truckflow.domain.inventory.InventoryItem;
import it.gabriele.truckflow.domain.inventory.InventoryStockMovement;
import it.gabriele.truckflow.domain.inventory.WarehouseLocation;
import java.util.List;
import java.util.Objects;

/** Caso d'uso: registrare un movimento di magazzino e aggiornare il saldo logico. */
public final class DefaultRecordInventoryStockMovementUseCase
    implements RecordInventoryStockMovementUseCase {

  private final InventoryItemRepository itemRepository;
  private final WarehouseLocationRepository warehouseLocationRepository;
  private final InventoryStockMovementRepository movementRepository;
  private final InventoryBalanceRepository balanceRepository;

  public DefaultRecordInventoryStockMovementUseCase(
      InventoryItemRepository itemRepository,
      WarehouseLocationRepository warehouseLocationRepository,
      InventoryStockMovementRepository movementRepository,
      InventoryBalanceRepository balanceRepository) {
    this.itemRepository =
        Objects.requireNonNull(itemRepository, "Il repository articoli è obbligatorio.");
    this.warehouseLocationRepository =
        Objects.requireNonNull(
            warehouseLocationRepository, "Il repository posizioni magazzino è obbligatorio.");
    this.movementRepository =
        Objects.requireNonNull(
            movementRepository, "Il repository movimenti magazzino è obbligatorio.");
    this.balanceRepository =
        Objects.requireNonNull(balanceRepository, "Il repository saldi magazzino è obbligatorio.");
  }

  @Override
  public InventoryBalance handle(Command command) {
    Objects.requireNonNull(command, "Il comando movimento magazzino è obbligatorio.");
    InventoryItem item = itemRepository.getRequired(command.itemCode(), "Articolo magazzino");
    WarehouseLocation location =
        warehouseLocationRepository.getRequired(command.locationId(), "Posizione magazzino");
    movementRepository.save(command.movement());
    List<InventoryStockMovement> movements = movementRepository.findAll();
    InventoryBalance balance = InventoryBalance.fromMovements(item, location, movements);
    balanceRepository.save(balance);
    return balance;
  }
}
