package it.gabriele.truckflow.domain.tire;

import java.util.Objects;

public final class WheelPosition {

  private final int axleNumber;
  private final WheelSide side;
  private final WheelSlot slot;

  private WheelPosition(int axleNumber, WheelSide side, WheelSlot slot) {
    if (axleNumber < 1) {
      throw new IllegalArgumentException("Il numero asse deve partire da 1.");
    }
    if (side == null) {
      throw new IllegalArgumentException("Il lato ruota è obbligatorio.");
    }
    if (slot == null) {
      throw new IllegalArgumentException("La posizione ruota è obbligatoria.");
    }
    this.axleNumber = axleNumber;
    this.side = side;
    this.slot = slot;
  }

  public static WheelPosition of(int axleNumber, WheelSide side, WheelSlot slot) {
    return new WheelPosition(axleNumber, side, slot);
  }

  public int getAxleNumber() {
    return axleNumber;
  }

  public WheelSide getSide() {
    return side;
  }

  public WheelSlot getSlot() {
    return slot;
  }

  public String formatLabel() {
    return "Asse " + axleNumber + " " + side + " " + slot;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof WheelPosition that)) return false;
    return axleNumber == that.axleNumber && side == that.side && slot == that.slot;
  }

  @Override
  public int hashCode() {
    return Objects.hash(axleNumber, side, slot);
  }
}
