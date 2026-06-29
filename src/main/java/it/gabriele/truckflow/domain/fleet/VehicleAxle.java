package it.gabriele.truckflow.domain.fleet;

import java.util.Objects;

public final class VehicleAxle {

  private final int axleNumber;
  private final WheelConfiguration wheelConfiguration;
  private final boolean liftable;
  private final AxleSteeringType steeringType;

  private VehicleAxle(
      int axleNumber,
      WheelConfiguration wheelConfiguration,
      boolean liftable,
      AxleSteeringType steeringType) {
    if (axleNumber < 1) {
      throw new IllegalArgumentException("Il numero asse deve partire da 1.");
    }
    if (wheelConfiguration == null) {
      throw new IllegalArgumentException("La configurazione ruote è obbligatoria.");
    }
    if (steeringType == null) {
      throw new IllegalArgumentException("Il tipo sterzatura asse è obbligatorio.");
    }
    this.axleNumber = axleNumber;
    this.wheelConfiguration = wheelConfiguration;
    this.liftable = liftable;
    this.steeringType = steeringType;
  }

  public static VehicleAxle of(
      int axleNumber,
      WheelConfiguration wheelConfiguration,
      boolean liftable,
      AxleSteeringType steeringType) {
    return new VehicleAxle(axleNumber, wheelConfiguration, liftable, steeringType);
  }

  public int getAxleNumber() {
    return axleNumber;
  }

  public WheelConfiguration getWheelConfiguration() {
    return wheelConfiguration;
  }

  public boolean isLiftable() {
    return liftable;
  }

  public AxleSteeringType getSteeringType() {
    return steeringType;
  }

  public boolean isSteering() {
    return steeringType == AxleSteeringType.STEERING
        || steeringType == AxleSteeringType.SELF_STEERING;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof VehicleAxle that)) return false;
    return axleNumber == that.axleNumber
        && liftable == that.liftable
        && wheelConfiguration == that.wheelConfiguration
        && steeringType == that.steeringType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(axleNumber, wheelConfiguration, liftable, steeringType);
  }
}
