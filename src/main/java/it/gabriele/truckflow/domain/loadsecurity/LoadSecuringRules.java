package it.gabriele.truckflow.domain.loadsecurity;

import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.shared.Weight;

/**
 * Regole minime di fissaggio carico. Non sostituisce un calcolo ingegneristico completo, ma
 * impedisce missioni palesemente incomplete.
 */
public final class LoadSecuringRules {

  private LoadSecuringRules() {}

  public static boolean hasMinimumEquipmentForCargo(
      LoadSecuringChecklist checklist, CargoCategory cargoCategory, Weight cargoWeight) {
    validate(checklist, cargoCategory, cargoWeight);

    if (cargoCategory.isBulk()) {
      return checklist.hasAtLeast(LoadSecuringEquipmentType.CONTAINMENT_NET, 1);
    }

    if (cargoCategory.isPalletized()
        || cargoCategory == CargoCategory.GENERAL
        || cargoCategory == CargoCategory.FOOD) {
      return checklist.hasAtLeast(
              LoadSecuringEquipmentType.RATCHET_STRAP, estimateMinimumStraps(cargoWeight))
          && checklist.hasAtLeast(LoadSecuringEquipmentType.EDGE_PROTECTOR, 4);
    }

    if (cargoCategory.requiresTemperatureControl()) {
      return checklist.hasAtLeast(LoadSecuringEquipmentType.LOAD_BAR, 2)
          || checklist.hasAtLeast(
              LoadSecuringEquipmentType.RATCHET_STRAP, estimateMinimumStraps(cargoWeight));
    }

    if (cargoCategory.isOversized()
        || cargoCategory == CargoCategory.MACHINERY
        || cargoCategory == CargoCategory.VEHICLES) {
      return checklist.hasAtLeast(
              LoadSecuringEquipmentType.RATCHET_STRAP, estimateMinimumStraps(cargoWeight) + 2)
          && checklist.hasAtLeast(LoadSecuringEquipmentType.ANTI_SLIP_MAT, 4);
    }

    return checklist.hasAtLeast(
        LoadSecuringEquipmentType.RATCHET_STRAP, estimateMinimumStraps(cargoWeight));
  }

  public static int estimateMinimumStraps(Weight cargoWeight) {
    if (cargoWeight == null) {
      throw new IllegalArgumentException("Il peso carico è obbligatorio.");
    }
    if (cargoWeight.getKilograms() == 0) {
      return 0;
    }
    return Math.max(2, (int) Math.ceil(cargoWeight.getKilograms() / 5000.0) * 2);
  }

  private static void validate(
      LoadSecuringChecklist checklist, CargoCategory cargoCategory, Weight cargoWeight) {
    if (checklist == null) {
      throw new IllegalArgumentException("La checklist fissaggio carico è obbligatoria.");
    }
    if (cargoCategory == null) {
      throw new IllegalArgumentException("La categoria merce è obbligatoria.");
    }
    if (cargoWeight == null) {
      throw new IllegalArgumentException("Il peso carico è obbligatorio.");
    }
  }
}
