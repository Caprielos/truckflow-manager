package it.gabriele.truckflow.domain.loadsecurity;

import java.util.List;
import java.util.Objects;

public final class LoadSecuringChecklist {

  private final List<LoadSecuringEquipment> equipment;

  private LoadSecuringChecklist(List<LoadSecuringEquipment> equipment) {
    if (equipment == null) {
      throw new IllegalArgumentException("La lista dispositivi è obbligatoria.");
    }
    if (equipment.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("La lista dispositivi non può contenere valori nulli.");
    }
    this.equipment = List.copyOf(equipment);
  }

  public static LoadSecuringChecklist of(List<LoadSecuringEquipment> equipment) {
    return new LoadSecuringChecklist(equipment);
  }

  public List<LoadSecuringEquipment> getEquipment() {
    return equipment;
  }

  public int countByType(LoadSecuringEquipmentType type) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo dispositivo è obbligatorio.");
    }
    return equipment.stream()
        .filter(item -> item.getType() == type)
        .mapToInt(LoadSecuringEquipment::getQuantity)
        .sum();
  }

  public boolean hasAtLeast(LoadSecuringEquipmentType type, int minimumQuantity) {
    if (minimumQuantity < 0) {
      throw new IllegalArgumentException("La quantità minima non può essere negativa.");
    }
    return countByType(type) >= minimumQuantity;
  }
}
