package it.gabriele.truckflow.domain.economics;

/** Categoria di un bene acquistato dall'azienda e tracciato economicamente. */
public enum FleetAssetCategory {
  VAN,
  RIGID_TRUCK,
  TRACTOR_UNIT,
  DRAWBAR_TRAILER,
  CENTER_AXLE_TRAILER,
  SEMI_TRAILER,
  CHASSIS,
  BODY_EQUIPMENT,
  CURTAINSIDER_BODY,
  BOX_BODY,
  REFRIGERATED_BODY,
  TIPPER_BODY,
  TANK_BODY,
  REFRIGERATION_UNIT,
  LOADING_EQUIPMENT,
  TAIL_LIFT,
  CRANE,
  FORKLIFT,
  PALLET_JACK,
  TIRE_SET,
  SINGLE_TIRE,
  TELEMATICS_DEVICE,
  GPS_TRACKER,
  TACHOGRAPH,
  DASHCAM,
  WORKSHOP_EQUIPMENT,
  WAREHOUSE_EQUIPMENT,
  SOFTWARE_LICENSE,
  OTHER;

  public boolean isVehicleUnit() {
    return switch (this) {
      case VAN,
          RIGID_TRUCK,
          TRACTOR_UNIT,
          DRAWBAR_TRAILER,
          CENTER_AXLE_TRAILER,
          SEMI_TRAILER,
          CHASSIS ->
          true;
      default -> false;
    };
  }

  public boolean isEquipment() {
    return switch (this) {
      case BODY_EQUIPMENT,
          CURTAINSIDER_BODY,
          BOX_BODY,
          REFRIGERATED_BODY,
          TIPPER_BODY,
          TANK_BODY,
          REFRIGERATION_UNIT,
          LOADING_EQUIPMENT,
          TAIL_LIFT,
          CRANE,
          FORKLIFT,
          PALLET_JACK,
          TELEMATICS_DEVICE,
          GPS_TRACKER,
          TACHOGRAPH,
          DASHCAM,
          WORKSHOP_EQUIPMENT,
          WAREHOUSE_EQUIPMENT,
          SOFTWARE_LICENSE ->
          true;
      default -> false;
    };
  }

  public boolean isTireRelated() {
    return this == TIRE_SET || this == SINGLE_TIRE;
  }
}
