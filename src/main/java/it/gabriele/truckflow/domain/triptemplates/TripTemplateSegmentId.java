package it.gabriele.truckflow.domain.triptemplates;

import java.util.UUID;

public record TripTemplateSegmentId(UUID value) {

  public TripTemplateSegmentId {
    value = TripTemplateValidation.requireNonNull(value, "value");
  }

  public static TripTemplateSegmentId random() {
    return new TripTemplateSegmentId(UUID.randomUUID());
  }
}
