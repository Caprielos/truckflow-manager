package it.gabriele.truckflow.domain.triptemplates;

import it.gabriele.truckflow.domain.locations.LocationId;

public record TripTemplateSegment(
    TripTemplateSegmentId id,
    int sequenceNumber,
    TripTemplateSegmentType type,
    LocationId originLocationId,
    LocationId destinationLocationId,
    Distance distance,
    String notes) {

  public TripTemplateSegment {
    id = id == null ? TripTemplateSegmentId.random() : id;
    sequenceNumber = TripTemplateValidation.requirePositive(sequenceNumber, "sequenceNumber");
    type = TripTemplateValidation.requireNonNull(type, "type");
    originLocationId = TripTemplateValidation.requireNonNull(originLocationId, "originLocationId");
    destinationLocationId =
        TripTemplateValidation.requireNonNull(destinationLocationId, "destinationLocationId");
    notes = TripTemplateValidation.normalize(notes);

    validateSameOriginAndDestination(type, originLocationId, destinationLocationId);
  }

  public boolean hasDistance() {
    return distance != null;
  }

  public boolean connectsTo(TripTemplateSegment nextSegment) {
    TripTemplateValidation.requireNonNull(nextSegment, "nextSegment");
    return destinationLocationId.equals(nextSegment.originLocationId());
  }

  private static void validateSameOriginAndDestination(
      TripTemplateSegmentType type, LocationId originLocationId, LocationId destinationLocationId) {
    if (!originLocationId.equals(destinationLocationId)) {
      return;
    }

    if (type != TripTemplateSegmentType.INTERNAL_TRANSFER
        && type != TripTemplateSegmentType.YARD_MOVEMENT
        && type != TripTemplateSegmentType.SPECIAL) {
      throw new IllegalArgumentException(
          "Origin and destination can be the same only for internal, yard or special segments.");
    }
  }
}
