package it.gabriele.truckflow.domain.warehouse;

/** Tipologia fisica o operativa di area magazzino. */
public enum WarehouseAreaType {
  RECEIVING,
  SHIPPING,
  STORAGE,
  PICKING,
  PACKING,
  CROSS_DOCKING,
  TEMPERATURE_CONTROLLED,
  ADR_SEGREGATED,
  WASTE_SEGREGATED,
  QUARANTINE,
  RETURNS,
  SPARE_PARTS,
  YARD,
  LOADING_DOCK
}
