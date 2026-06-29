package it.gabriele.truckflow.domain.routeoptimization;

/** Obiettivo di ottimizzazione percorso. */
public enum RouteOptimizationObjective {
  MINIMIZE_COST,
  MINIMIZE_DISTANCE,
  MINIMIZE_TIME,
  MAXIMIZE_ON_TIME_DELIVERY,
  MINIMIZE_EMPTY_KILOMETERS,
  MINIMIZE_CO2,
  BALANCE_DRIVER_HOURS,
  MAXIMIZE_VEHICLE_SATURATION
}
