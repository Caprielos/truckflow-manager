package it.gabriele.truckflow.application.usecase.warehouse;

import it.gabriele.truckflow.application.port.in.MoveStockPositionUseCase;
import it.gabriele.truckflow.application.port.out.WarehouseStockPositionRepository;
import it.gabriele.truckflow.domain.warehouse.StockPosition;
import java.util.Objects;

/** Implementazione default di MoveStockPositionUseCase. */
public final class DefaultMoveStockPositionUseCase implements MoveStockPositionUseCase {

  private final WarehouseStockPositionRepository repository;

  public DefaultMoveStockPositionUseCase(WarehouseStockPositionRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public StockPosition handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    StockPosition aggregate =
        Objects.requireNonNull(command.stock(), "La posizione stock è obbligatoria.");
    repository.save(aggregate);
    return aggregate;
  }
}
