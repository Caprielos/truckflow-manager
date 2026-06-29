package it.gabriele.truckflow.domain.deadline;

/** Tipo di risorsa o processo a cui appartiene una scadenza enterprise. */
public enum DeadlineOwnerType {
  DRIVER,
  VEHICLE,
  TRAILER,
  VEHICLE_COMBINATION,
  CUSTOMER_CONTRACT,
  SUPPLIER_CONTRACT,
  DOCUMENT,
  FACILITY,
  INVENTORY_ITEM,
  TRANSPORT_MISSION,
  OTHER
}
