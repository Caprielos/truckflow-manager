package it.gabriele.truckflow.domain.roadtransport;

import java.util.List;

/** Distribuzione pesi sugli assi per controllare sovraccarichi e carico assiale. */
public record AxleLoadPlan(List<AxleLoad> axleLoads) {

  public AxleLoadPlan {
    if (axleLoads == null || axleLoads.isEmpty()) {
      throw new IllegalArgumentException("Il piano carichi asse è obbligatorio.");
    }
    axleLoads = List.copyOf(axleLoads);
  }

  public double totalPlannedKilograms() {
    return axleLoads.stream().mapToDouble(AxleLoad::plannedKilograms).sum();
  }

  public boolean hasLegalOverload() {
    return axleLoads.stream().anyMatch(axleLoad -> !axleLoad.isWithinLegalLimit());
  }

  public boolean hasTechnicalOverload() {
    return axleLoads.stream().anyMatch(axleLoad -> !axleLoad.isWithinTechnicalLimit());
  }

  public List<AxleLoad> overloadedAxles() {
    return axleLoads.stream().filter(axleLoad -> !axleLoad.isWithinLegalLimit()).toList();
  }
}
