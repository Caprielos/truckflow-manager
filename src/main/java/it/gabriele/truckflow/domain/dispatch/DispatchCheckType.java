package it.gabriele.truckflow.domain.dispatch;

/** Controlli reali fatti dall'ufficio traffico prima di assegnare una missione. */
public enum DispatchCheckType {
  DRIVER_AVAILABILITY,
  DRIVER_LICENSE,
  DRIVER_QUALIFICATION,
  DRIVER_TIME,
  VEHICLE_STATUS,
  TRAILER_STATUS,
  VEHICLE_CERTIFICATES,
  CARGO_COMPATIBILITY,
  DOCUMENTS,
  LOAD_SECURING,
  PARKING_READY,
  CUSTOMER_CONTRACT,
  INVENTORY_EQUIPMENT,
  COST_MARGIN,
  ROUTE_FEASIBILITY,
  FACILITY_ACCESS,
  OTHER
}
