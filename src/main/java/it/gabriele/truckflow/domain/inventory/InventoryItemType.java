package it.gabriele.truckflow.domain.inventory;

/** Tipi di materiale che un'azienda di trasporto può tenere a magazzino. */
public enum InventoryItemType {
  SPARE_PART,
  TIRE,
  RETREADED_TIRE,
  CONSUMABLE,
  ENGINE_OIL,
  ADBLUE,
  FILTER,
  BRAKE_COMPONENT,
  LIGHTING,
  LOAD_SECURING_EQUIPMENT,
  ADR_EQUIPMENT,
  PPE,
  TOOL,
  TELEMATICS_DEVICE,
  TACHOGRAPH_ROLL,
  PALLET,
  DOCUMENT_FORM,
  CLEANING_SUPPLY,
  OTHER;

  public boolean isSafetyCritical() {
    return this == TIRE
        || this == RETREADED_TIRE
        || this == BRAKE_COMPONENT
        || this == LOAD_SECURING_EQUIPMENT
        || this == ADR_EQUIPMENT
        || this == PPE;
  }
}
