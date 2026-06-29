package it.gabriele.truckflow.domain.deadlinepolicy;

import it.gabriele.truckflow.domain.deadline.DeadlineOwnerType;

/** Elemento fisico, documentale o operativo controllabile dallo scadenziario. */
public enum ManagedDeadlineElementType {
  VEHICLE_REGISTRATION_DOCUMENT(DeadlineOwnerType.VEHICLE),
  VEHICLE_INSPECTION(DeadlineOwnerType.VEHICLE),
  VEHICLE_INSURANCE(DeadlineOwnerType.VEHICLE),
  VEHICLE_TACHOGRAPH_CALIBRATION(DeadlineOwnerType.VEHICLE),
  VEHICLE_ATP_CERTIFICATE(DeadlineOwnerType.VEHICLE),
  VEHICLE_ADR_CERTIFICATE(DeadlineOwnerType.VEHICLE),
  VEHICLE_ENGINE_OIL(DeadlineOwnerType.VEHICLE),
  VEHICLE_AIR_FILTER(DeadlineOwnerType.VEHICLE),
  VEHICLE_OIL_FILTER(DeadlineOwnerType.VEHICLE),
  VEHICLE_FUEL_FILTER(DeadlineOwnerType.VEHICLE),
  VEHICLE_BRAKE_PADS(DeadlineOwnerType.VEHICLE),
  VEHICLE_BRAKE_DISCS(DeadlineOwnerType.VEHICLE),
  VEHICLE_COOLANT(DeadlineOwnerType.VEHICLE),
  VEHICLE_ADBLUE_SYSTEM(DeadlineOwnerType.VEHICLE),
  VEHICLE_BELTS(DeadlineOwnerType.VEHICLE),
  VEHICLE_BATTERY(DeadlineOwnerType.VEHICLE),
  VEHICLE_SUSPENSION(DeadlineOwnerType.VEHICLE),
  VEHICLE_LIGHTS(DeadlineOwnerType.VEHICLE),
  VEHICLE_ENGINE_DIAGNOSTIC(DeadlineOwnerType.VEHICLE),
  VEHICLE_ELECTRICAL_SYSTEM(DeadlineOwnerType.VEHICLE),
  TRAILER_BRAKING_SYSTEM(DeadlineOwnerType.TRAILER),
  TRAILER_ELECTRICAL_SYSTEM(DeadlineOwnerType.TRAILER),
  TRAILER_BODY_FLOOR(DeadlineOwnerType.TRAILER),
  TRAILER_DOORS_LOCKS(DeadlineOwnerType.TRAILER),
  TRAILER_REFRIGERATION_UNIT(DeadlineOwnerType.TRAILER),
  TRAILER_LANDING_GEAR(DeadlineOwnerType.TRAILER),
  TRAILER_FIFTH_WHEEL_COUPLING(DeadlineOwnerType.TRAILER),
  TRAILER_TAIL_LIFT(DeadlineOwnerType.TRAILER),
  DRIVER_LICENSE(DeadlineOwnerType.DRIVER),
  DRIVER_CQC(DeadlineOwnerType.DRIVER),
  DRIVER_ADR_CERTIFICATE(DeadlineOwnerType.DRIVER),
  DRIVER_MEDICAL_CHECK(DeadlineOwnerType.DRIVER),
  DRIVER_TACHOGRAPH_CARD(DeadlineOwnerType.DRIVER),
  DRIVER_MANDATORY_TRAINING(DeadlineOwnerType.DRIVER),
  CARGO_PALLET(DeadlineOwnerType.OTHER),
  CARGO_CONTAINER(DeadlineOwnerType.OTHER),
  CARGO_PACKAGING(DeadlineOwnerType.OTHER),
  CARGO_SEAL(DeadlineOwnerType.OTHER),
  CARGO_LABEL(DeadlineOwnerType.OTHER),
  WAREHOUSE_LOCATION(DeadlineOwnerType.FACILITY),
  WAREHOUSE_RACK(DeadlineOwnerType.FACILITY),
  WAREHOUSE_COLD_CELL(DeadlineOwnerType.FACILITY),
  WAREHOUSE_EQUIPMENT(DeadlineOwnerType.FACILITY),
  WAREHOUSE_SAFETY_SYSTEM(DeadlineOwnerType.FACILITY),
  TRIP_POINT_OF_INTEREST(DeadlineOwnerType.TRANSPORT_MISSION),
  TRIP_ROAD_CONSTRAINT(DeadlineOwnerType.TRANSPORT_MISSION),
  TRIP_WEATHER_CONSTRAINT(DeadlineOwnerType.TRANSPORT_MISSION),
  TRIP_TOLL_PROFILE(DeadlineOwnerType.TRANSPORT_MISSION),
  SECURITY_SEAL(DeadlineOwnerType.OTHER),
  SECURITY_LOCK(DeadlineOwnerType.OTHER),
  SECURITY_ALARM(DeadlineOwnerType.OTHER),
  SECURITY_CAMERA(DeadlineOwnerType.OTHER),
  TELEMATICS_CANBUS_SENSOR(DeadlineOwnerType.VEHICLE),
  TELEMATICS_DTC_MONITORING(DeadlineOwnerType.VEHICLE),
  TELEMATICS_TEMPERATURE_SENSOR(DeadlineOwnerType.VEHICLE),
  TELEMATICS_TPMS_SENSOR(DeadlineOwnerType.VEHICLE),
  TELEMATICS_FUEL_SENSOR(DeadlineOwnerType.VEHICLE),
  TELEMATICS_DOOR_SENSOR(DeadlineOwnerType.VEHICLE),
  OTHER(DeadlineOwnerType.OTHER);

  private final DeadlineOwnerType defaultOwnerType;

  ManagedDeadlineElementType(DeadlineOwnerType defaultOwnerType) {
    this.defaultOwnerType = defaultOwnerType;
  }

  public DeadlineOwnerType getDefaultOwnerType() {
    return defaultOwnerType;
  }
}
