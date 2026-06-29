package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.port.in.CalculateMissionRealMarginUseCase;
import it.gabriele.truckflow.application.port.out.CostAllocationRepository;
import it.gabriele.truckflow.domain.financeops.CostAllocation;
import it.gabriele.truckflow.domain.financeops.FinanceOperationRules;
import it.gabriele.truckflow.domain.shared.Money;
import java.util.Objects;

/** Implementazione default di CalculateMissionRealMarginUseCase. */
public final class DefaultCalculateMissionRealMarginUseCase
    implements CalculateMissionRealMarginUseCase {

  private final CostAllocationRepository allocationRepository;

  public DefaultCalculateMissionRealMarginUseCase(CostAllocationRepository allocationRepository) {
    this.allocationRepository =
        Objects.requireNonNull(allocationRepository, "Il repository allocazioni è obbligatorio.");
  }

  @Override
  public Result handle(Command command) {
    Objects.requireNonNull(command, "Il comando margine reale è obbligatorio.");
    Money costs =
        allocationRepository.findAll().stream()
            .filter(
                allocation ->
                    command.missionCode().trim().equalsIgnoreCase(allocation.missionCode()))
            .filter(FinanceOperationRules::canUseForProfitability)
            .map(CostAllocation::amount)
            .reduce(Money.of("0", command.revenue().getCurrencyCode()), Money::add);
    Money margin = command.revenue().subtract(costs);
    return new Result(command.revenue(), costs, margin);
  }
}
