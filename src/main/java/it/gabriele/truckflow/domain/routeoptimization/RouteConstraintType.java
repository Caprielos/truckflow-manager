package it.gabriele.truckflow.domain.routeoptimization;

/** Vincolo usato dal motore di ottimizzazione percorso. */
public enum RouteConstraintType {
  MAX_HEIGHT,
  MAX_WEIGHT,
  MAX_WIDTH,
  MAX_LENGTH,
  ADR_TUNNEL_RESTRICTION,
  ADR_TRANSIT_BAN,
  LOW_EMISSION_ZONE,
  TOLL_COST,
  TRAFFIC_DELAY,
  DRIVER_HOURS,
  DELIVERY_TIME_WINDOW,
  CUSTOMER_PRIORITY,
  ROAD_CLOSURE,
  FERRY_OR_TRAIN,
  BORDER_WAITING_TIME,
  TEMPERATURE_RISK
}
