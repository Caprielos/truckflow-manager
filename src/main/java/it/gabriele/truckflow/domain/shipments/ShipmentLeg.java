package it.gabriele.truckflow.domain.shipments;

import it.gabriele.truckflow.domain.locations.LocationId;
import java.math.BigDecimal;

public record ShipmentLeg(
    ShipmentLegId id,
    int sequenceNumber,
    ShipmentLegType type,
    LocationId originLocationId,
    LocationId destinationLocationId,
    BigDecimal estimatedDistanceKm,
    String notes) {

  public ShipmentLeg {
    id = id == null ? ShipmentLegId.random() : id;
    sequenceNumber = ShipmentValidation.requirePositive(sequenceNumber, "sequenceNumber");
    type = ShipmentValidation.requireNonNull(type, "type");
    originLocationId = ShipmentValidation.requireNonNull(originLocationId, "originLocationId");
    destinationLocationId =
        ShipmentValidation.requireNonNull(destinationLocationId, "destinationLocationId");
    estimatedDistanceKm =
        ShipmentValidation.nonNegativeOrNull(estimatedDistanceKm, "estimatedDistanceKm");
    notes = ShipmentValidation.normalize(notes);

    validateSameOriginAndDestination(type, originLocationId, destinationLocationId);
  }

  public boolean hasEstimatedDistance() {
    return estimatedDistanceKm != null;
  }

  public boolean connectsTo(ShipmentLeg nextLeg) {
    ShipmentValidation.requireNonNull(nextLeg, "nextLeg");
    return destinationLocationId.equals(nextLeg.originLocationId());
  }

  private static void validateSameOriginAndDestination(
      ShipmentLegType type, LocationId originLocationId, LocationId destinationLocationId) {
    if (!originLocationId.equals(destinationLocationId)) {
      return;
    }

    if (type != ShipmentLegType.TRANSFER && type != ShipmentLegType.SPECIAL) {
      throw new IllegalArgumentException(
          "Origin and destination can be the same only for transfer or special shipment legs.");
    }
  }
}
